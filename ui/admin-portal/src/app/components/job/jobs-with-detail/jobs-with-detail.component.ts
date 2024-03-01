import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../../../model/job";
import {MainSidePanelBase} from "../../util/split/MainSidePanelBase";
import {Router} from "@angular/router";
import {isStarredByMe, SearchOppsBy} from "../../../model/base";
import {JobService} from "../../../services/job.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {BehaviorSubject} from "rxjs";

/**
 * Displays the jobs returned by the given type of search, together with extra details
 * related to the selected job.
 * <p/>
 * The actual display of the jobs is delegated to the JobsComponent.
 */
@Component({
  selector: 'app-jobs-with-detail',
  templateUrl: './jobs-with-detail.component.html',
  styleUrls: ['./jobs-with-detail.component.scss']
})
export class JobsWithDetailComponent extends MainSidePanelBase implements OnInit {
  selectedJob: Job;
  error: any;
  loading: boolean;

  @Input() searchBy: SearchOppsBy;

  /**
   * This is passed in from a higher level component which tracks whether the overall read status
   * of all the chats that it manages.
   * That component is the cases tab in the Jobs home component - which displays an asterisk
   * if some chats are unread.
   * <p/>
   * This component can call next on this subject if it knows that some of the chats it manages
   * are unread. The fact that it is a BehaviorSubject means that you can query the current status
   * of the higher level component.
   */
  @Input() chatsRead$: BehaviorSubject<boolean>;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private jobService: JobService
  ) {
    super(6);
  }

  ngOnInit(): void {

  }

  onJobSelected(job: Job) {
    this.selectedJob = job;
  }

  doToggleStarred() {
    this.loading = true;
    this.error = null
    this.jobService.updateStarred(this.selectedJob.id, !this.isStarred()).subscribe(
      (job: Job) => {this.selectedJob = job; this.loading = false},
      (error) => {this.error = error; this.loading = false}
    )
  }

  isStarred(): boolean {
    return isStarredByMe(this.selectedJob?.starringUsers, this.authenticationService);
  }

  onJobUpdated(opp: Job) {
    //Currently we don't process this event
  }
}
