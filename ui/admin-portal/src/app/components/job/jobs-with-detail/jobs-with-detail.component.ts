import {Component, Input, OnInit} from '@angular/core';
import {Job} from "../../../model/job";
import {MainSidePanelBase} from "../../util/split/MainSidePanelBase";
import {Router} from "@angular/router";
import {SearchJobsBy} from "../../../model/base";


@Component({
  selector: 'app-jobs-with-detail',
  templateUrl: './jobs-with-detail.component.html',
  styleUrls: ['./jobs-with-detail.component.scss']
})
export class JobsWithDetailComponent extends MainSidePanelBase implements OnInit {
  selectedJob: Job;

  @Input() searchBy: SearchJobsBy;

  constructor(
    private router: Router,
  ) {
    super(6);
  }

  ngOnInit(): void {

  }

  onJobSelected(job: Job) {
    this.selectedJob = job;
  }

  doOpenJob() {
    this.router.navigate(['job', this.selectedJob.id]);
  }

  doToggleStarred() {
    //todo
  }

  isStarred() {
    //todo
  }
}
