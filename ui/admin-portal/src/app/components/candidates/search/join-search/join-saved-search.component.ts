import {Component, OnInit} from '@angular/core';


import {SearchResults} from '../../../../model/search-results';

import {FormBuilder, FormGroup} from "@angular/forms";
import {catchError, debounceTime, distinctUntilChanged, map, switchMap, tap} from "rxjs/operators";
import {SavedSearch} from "../../../../model/saved-search";
import {SavedSearchService} from "../../../../services/saved-search.service";
import {Router} from "@angular/router";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Observable, of} from "rxjs";

@Component({
  selector: 'app-join-saved-search',
  templateUrl: './join-saved-search.component.html',
  styleUrls: ['./join-saved-search.component.scss']
})
export class JoinSavedSearchComponent implements OnInit {

  searchForm: FormGroup;
  loading: boolean;
  error: any;
  pageNumber: number;
  pageSize: number;
  results: SearchResults<SavedSearch>;
  searching = false;
  searchFailed = false;
  doSavedSearchSearch;

  constructor(private activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private router: Router,
              private savedSearchService: SavedSearchService) {
  }

  ngOnInit() {

    this.searchForm = this.fb.group({
      selectedSavedSearch: [null],
      saveSearchId: [''],
      searchType: ['']
    });
    //dropdown to add joined searches
    this.doSavedSearchSearch = (text$: Observable<string>) =>
      text$.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => {
          this.searching = true;
          this.error = null
        }),
        switchMap(term =>
          this.savedSearchService.search({phrase: term}).pipe(
            tap(() => this.searchFailed = false),
            map(result => result.content),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            }))
        ),
        tap(() => this.searching = false)
      );

  }

  renderSavedSearchRow(savedSearch: SavedSearch) {
    return savedSearch.name;
  }



  add(){
     let searchJoin = this.searchForm.value;
     searchJoin.savedSearchId = searchJoin.selectedSavedSearch.id;
     searchJoin.name = searchJoin.selectedSavedSearch.name;
     this.closeModal(searchJoin);
  }

  closeModal(searchJoin) {
    this.activeModal.close(searchJoin);
  }

  dismiss() {
    this.activeModal.dismiss(false);
  }

}
