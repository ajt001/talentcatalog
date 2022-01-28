import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Candidate, Status, TaskAssignment} from "../../../../model/candidate";
import {CandidateService} from "../../../../services/candidate.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AssignTasksCandidateComponent} from "../../../tasks/assign-tasks-candidate/assign-tasks-candidate.component";
import {EditTaskAssignmentComponent} from "./edit/edit-task-assignment.component";
import {ConfirmationComponent} from "../../../util/confirm/confirmation.component";
import {TaskAssignmentService} from "../../../../services/task-assignment.service";

@Component({
  selector: 'app-view-candidate-tasks',
  templateUrl: './view-candidate-tasks.component.html',
  styleUrls: ['./view-candidate-tasks.component.scss']
})
export class ViewCandidateTasksComponent implements OnInit, OnChanges {

  @Input() candidate: Candidate;
  @Input() editable: boolean;
  loading;
  error;
  saving;
  ongoingTasks: TaskAssignment[];
  completedTasks: TaskAssignment[];
  inactiveTasks: TaskAssignment[];
  today: Date;

  constructor(private candidateService: CandidateService,
              private taskAssignmentService: TaskAssignmentService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.today = new Date();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes && changes.candidate && changes.candidate.previousValue !== changes.candidate.currentValue) {
      this.loading = true;
      this.getCandidate();
    }
  }

  getCandidate() {
    this.candidateService.get(this.candidate.id).subscribe(
      candidate => {
        this.candidate = candidate;
        if (this.candidate.taskAssignments) {
          this.ongoingTasks = this.candidate.taskAssignments.filter(t =>
            t.completedDate == null && t.status === Status.active);
          this.completedTasks = this.candidate.taskAssignments.filter(t =>
            t.completedDate != null);
          this.inactiveTasks = this.candidate.taskAssignments.filter(t =>
            t.status === Status.inactive);
        } else {
          this.ongoingTasks = [];
          this.completedTasks = [];
        }
        this.loading = false;
      },
      error => {
        this.error = error;
        this.loading = false;
      });
  }

  isOverdue(ta: TaskAssignment) {
    return (new Date(ta.dueDate) < this.today) && !ta.task.optional;
  }

  assignTask() {
    const assignTaskCandidateModal = this.modalService.open(AssignTasksCandidateComponent, {
      centered: true,
      backdrop: 'static'
    });

    assignTaskCandidateModal.componentInstance.candidateId = this.candidate.id;

    assignTaskCandidateModal.result
      .then((taskAssignment: TaskAssignment) => this.getCandidate())
      .catch(() => { /* Isn't possible */ });

  }

  editTaskAssignment(ta: TaskAssignment) {
    const editTaskAssignmentModal = this.modalService.open(EditTaskAssignmentComponent, {
      centered: true,
      backdrop: 'static'
    });

    editTaskAssignmentModal.componentInstance.taskAssignment = ta;

    editTaskAssignmentModal.result
      .then((taskAssignment) => this.getCandidate())
      .catch(() => { /* Isn't possible */ });

  }

  deleteTaskAssignment(ta: TaskAssignment) {
    const deleteTaskAssignmentModal = this.modalService.open(ConfirmationComponent, {
      centered: true,
      backdrop: 'static'
    });

    deleteTaskAssignmentModal.componentInstance.message = "Are you sure you want to remove the candidate "
      + this.candidate.user.firstName + " " + this.candidate.user.lastName + " from the task " + ta.task.name + "?"

    deleteTaskAssignmentModal.result
      .then((result) => {
        if (result === true) {
          this.taskAssignmentService.removeTaskAssignment(ta.id).subscribe(
            () => {
              this.getCandidate();
              this.saving = false;
            },
            error => {
              this.error = error;
              this.saving = false;
            }
          );
        }
      })
      .catch(() => { /* Isn't possible */
      });
  }

  viewResponse(ta: TaskAssignment) {
    // todo link to the uploaded file, or in future it might be the answer to a question.
  }
}