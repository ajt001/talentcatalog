/*
 * Copyright (c) 2023 Talent Beyond Boundaries.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

import {
  Directive,
  ElementRef,
  EventEmitter,
  Inject,
  Input,
  LOCALE_ID,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {CandidateOpportunity, SearchOpportunityRequest} from "../../../model/candidate-opportunity";
import {SearchResults} from "../../../model/search-results";
import {FormBuilder, FormGroup} from "@angular/forms";
import {EnumOption} from "../../../util/enum";
import {AuthService} from "../../../services/auth.service";
import {LocalStorageService} from "angular-2-local-storage";
import {SalesforceService} from "../../../services/salesforce.service";
import {indexOfHasId, SearchOppsBy} from "../../../model/base";
import {getOpportunityStageName, Opportunity} from "../../../model/opportunity";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {formatDate} from "@angular/common";
import {OpportunityService} from "./OpportunityService";
import {User} from "../../../model/user";

@Directive()
export abstract class FilteredOppsComponentBase<T extends Opportunity> implements OnInit, OnChanges {
  @Input() searchBy: SearchOppsBy;
  @Output() oppSelection = new EventEmitter();

  opps: T[];

  currentOpp: T;

  /*
   * These are default values which will normally be overridden in subclasses
   */
  myOppsOnlyLabel = "My opps only";
  myOppsOnlyTip = "Only show opps that I am the contact for";
  showClosedOppsLabel = "Show closed opps";
  showClosedOppsTip = "Show opps that have been closed";
  showInactiveOppsLabel = "Show inactive opps";
  showInactiveOppsTip = "Show opps that are no longer active - for example if they have already relocated";

  loading: boolean;
  error;
  pageNumber: number;
  pageSize: number;

  results: SearchResults<T>;

  //Get reference to the search input filter element (see #searchFilter in html) so we can reset focus
  @ViewChild("searchFilter")
  searchFilter: ElementRef;

  searchForm: FormGroup;

  //Default sort opps in descending order of nextDueDate
  sortField = 'nextStepDueDate';
  sortDirection = 'DESC';

  stages: EnumOption[] = [];


  private filterKeySuffix: string = 'Filter';
  private myOppsOnlySuffix: string = 'MyOppsOnly';
  private savedStateKeyPrefix: string = 'BrowseKey';
  private showClosedOppsSuffix: string = 'ShowClosedOpps';
  private showInactiveOppsSuffix: string = 'ShowInactiveOpps';
  private sortDirectionSuffix: string = 'SortDir';
  private sortFieldSuffix: string = 'Sort';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private localStorageService: LocalStorageService,
    protected oppService: OpportunityService<T>,
    private salesforceService: SalesforceService,
    @Inject(LOCALE_ID) private locale: string,
    private stateKeysRoot: string
  ) {}

  ngOnInit(): void {

    //Pick up previous sort
    const previousSortDirection: string = this.localStorageService.get(this.savedStateKey() + this.sortDirectionSuffix);
    if (previousSortDirection) {
      this.sortDirection = previousSortDirection;
    }
    const previousSortField: string = this.localStorageService.get(this.savedStateKey() + this.sortFieldSuffix);
    if (previousSortField) {
      this.sortField = previousSortField;
    }

    this.stages = this.loadStages();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.searchBy) {
      this.initSearchBy()
    }
  }

  protected abstract loadStages(): EnumOption[];

  private initSearchBy() {
    this.pageNumber = 1;
    this.pageSize = 30;

    //Pick up any previous keyword filter
    const filter = this.localStorageService.get(this.savedStateKey() + this.filterKeySuffix);

    //Pick up previous options
    const previousMyOppsOnly: string = this.localStorageService.get(this.savedStateKey() + this.myOppsOnlySuffix);
    const previousShowClosedOpps: string = this.localStorageService.get(this.savedStateKey() + this.showClosedOppsSuffix);
    const previousShowInactiveOpps: string = this.localStorageService.get(this.savedStateKey() + this.showInactiveOppsSuffix);

    this.searchForm = this.fb.group({
      keyword: [filter],
      myOppsOnly: [previousMyOppsOnly ? previousMyOppsOnly : false],
      showClosedOpps: [previousShowClosedOpps ? previousShowClosedOpps : false],
      showInactiveOpps: [previousShowInactiveOpps ? previousShowInactiveOpps : false],
      selectedStages: [[]]
    });

    this.subscribeToFilterChanges();

    this.search();
  }

  private get keyword(): string {
    return this.searchForm ? this.searchForm.value.keyword : "";
  }

  protected get showClosedOpps(): boolean {
    return this.searchForm ? this.searchForm.value.showClosedOpps : false;
  }

  protected get showInactiveOpps(): boolean {
    return this.searchForm ? this.searchForm.value.showInactiveOpps : false;
  }

  protected get myOppsOnly(): boolean {
    return this.searchForm ? this.searchForm.value.myOppsOnly : false;
  }

  get SearchOppsBy() {
    return SearchOppsBy;
  }

  get selectedStages(): string[] {
    return this.searchForm ? this.searchForm.value.selectedStages : "";
  }

  private savedStateKey(): string {
    //This key is constructed from the combination of inputs which are associated with each tab
    // in home.component.html
    //This key is used to store the last state associated with each tab.

    //The standard key is "BrowseKey" + stateKeysRoot  (eg "Jobs") +
    // the search by (corresponding to the specific displayed tab)
    let key = this.savedStateKeyPrefix
      + this.stateKeysRoot
      + SearchOppsBy[this.searchBy];

    return key
  }

  protected abstract createSearchRequest(): SearchOpportunityRequest;

  search() {
    //Remember keyword filter
    this.localStorageService.set(this.savedStateKey() + this.filterKeySuffix, this.keyword);

    //Remember sort
    this.localStorageService.set(this.savedStateKey()+this.sortFieldSuffix, this.sortField);
    this.localStorageService.set(this.savedStateKey()+this.sortDirectionSuffix, this.sortDirection);

    //Remember options
    this.localStorageService.set(this.savedStateKey()+this.myOppsOnlySuffix, this.myOppsOnly);
    this.localStorageService.set(this.savedStateKey()+this.showClosedOppsSuffix, this.showClosedOpps);
    this.localStorageService.set(this.savedStateKey()+this.showInactiveOppsSuffix, this.showInactiveOpps);

    let req = this.createSearchRequest();
    req.keyword = this.keyword;
    req.pageNumber = this.pageNumber - 1;
    req.pageSize = this.pageSize;

    req.sortFields = [this.sortField];
    req.sortDirection = this.sortDirection;

    req.stages = this.selectedStages;

    switch (this.searchBy) {
      case SearchOppsBy.live:

        //Don't want to see closed jobs
        req.sfOppClosed = false;
        break;

      case SearchOppsBy.mine:
        if (this.myOppsOnly) {
          req.ownedByMe = true;
        } else {
          req.ownedByMyPartner = true;
        }

        //Default - filters out closed opps and only includes active stages
        req.sfOppClosed = false;
        req.activeStages = true;

        if (this.showInactiveOpps) {
          //Turn off the active stages filter
          req.activeStages = false;
        }

        if (this.showClosedOpps) {
          req.sfOppClosed = true;
        }
        break;
    }

    this.error = null;
    this.loading = true;

    this.oppService.searchPaged(req).subscribe(
      results => this.processSearchResults(results),
      error => this.processSearchError(error)
    )

  }

  protected processSearchError(error: any) {
    this.error = error;
    this.loading = false;
  }

  protected processSearchResults(results: SearchResults<T>) {
    this.results = results;

    this.opps = results.content;

    if (this.opps.length > 0) {
      //Select previously selected item if still present in results
      const id: number = this.localStorageService.get(this.savedStateKey());
      if (id) {
        let currentIndex = indexOfHasId(id, this.opps);
        if (currentIndex >= 0) {
          this.selectCurrent(this.opps[currentIndex]);
        } else {
          this.selectCurrent(this.opps[0]);
        }
      } else {
        //Select the first search if no previous
        this.selectCurrent(this.results.content[0]);
      }
    }

    //Following the search filter loses focus, so focus back on it again
    setTimeout(()=>{this.searchFilter.nativeElement.focus()},0);

    this.loading = false;
  }

  canAccessSalesforce(): boolean {
    return this.authService.canAccessSalesforce();
  }

  get getCandidateOpportunityStageName() {
    return getOpportunityStageName;
  }

  getOppSfLink(sfId: string): string {
    return this.salesforceService.sfOppToLink(sfId);
  }

  private subscribeToFilterChanges() {
    this.searchForm.valueChanges
    .pipe(
      debounceTime(400),
      distinctUntilChanged()
    )
    .subscribe(() => {
      this.search();
    });
  }

  /**
   * Call when column title is clicked. If the column is the currently selected column,
   * the sort toggles between ASC and DESC.
   * If the column is not the currently selected column, the sort is set to the given
   * default.
   * @param column Name of column which was clicked
   * @param directionDefault Default sort direction for the clicked column
   */
  toggleSort(column: string, directionDefault = 'ASC') {
    if (this.sortField === column) {
      this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
    } else {
      this.sortField = column;
      this.sortDirection = directionDefault;
    }

    if (this.searchBy) {
      this.search();
    }
  }

  selectCurrent(opp: T) {
    this.currentOpp = opp;

    const id: number = opp.id;
    this.localStorageService.set(this.savedStateKey(), id);

    this.oppSelection.emit(opp);

  }

  fullUserName(user: User) {
    return user ? user.firstName + " " + user.lastName : "";
  }

  getNextStepHoverString(opp: CandidateOpportunity) {
    const date = opp == null ? null : opp.updatedDate;
    const dateStr = date == null ? "???" : formatDate(date, "yyyy-MM-dd", this.locale);
    return dateStr + ': ' + (opp.nextStep ? opp.nextStep : '');
  }

}