import {Component, OnInit} from '@angular/core';
import {IntakeComponentBase} from "../../../util/intake/IntakeComponentBase";
import {UntypedFormBuilder} from "@angular/forms";
import {CandidateService} from "../../../../services/candidate.service";
import {EnumOption, enumOptions} from "../../../../util/enum";
import {YesNo} from "../../../../model/candidate";

@Component({
  selector: 'app-health-issues',
  templateUrl: './health-issues.component.html',
  styleUrls: ['./health-issues.component.scss']
})
export class HealthIssuesComponent extends IntakeComponentBase implements OnInit {

  public healthIssuesOptions: EnumOption[] = enumOptions(YesNo);

  constructor(fb: UntypedFormBuilder, candidateService: CandidateService) {
    super(fb, candidateService);
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      healthIssues: [{value: this.candidateIntakeData?.healthIssues, disabled: !this.editable}],
      healthIssuesNotes: [{value: this.candidateIntakeData?.healthIssuesNotes, disabled: !this.editable}],
    });
  }

  get hasNotes(): boolean {
    let found: boolean = false;
    if (this.form.value.healthIssues) {
      if (this.form.value.healthIssues === 'Yes') {
        found = true
      }
      if (this.form.value.healthIssues === 'No') {
        found = true
      }
    }
    return found;
  }

}
