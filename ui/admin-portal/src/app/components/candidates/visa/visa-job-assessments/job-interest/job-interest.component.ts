import {Component, Input, OnInit} from '@angular/core';
import {EnumOption, enumOptions} from '../../../../../util/enum';
import {CandidateVisaJobCheck, YesNo} from '../../../../../model/candidate';
import {FormBuilder} from '@angular/forms';
import {CandidateService} from '../../../../../services/candidate.service';
import {IntakeComponentBase} from '../../../../util/intake/IntakeComponentBase';

@Component({
  selector: 'app-job-interest',
  templateUrl: './job-interest.component.html',
  styleUrls: ['./job-interest.component.scss']
})
export class JobInterestComponent extends IntakeComponentBase implements OnInit {

  @Input() selectedIndex: number;
  @Input() selectedJobCheck: CandidateVisaJobCheck;
  public jobInterestOptions: EnumOption[] = enumOptions(YesNo);

  constructor(fb: FormBuilder, candidateService: CandidateService) {
    super(fb, candidateService);
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      visaJobId: [this.selectedJobCheck?.id],
      visaJobInterest: [this.selectedJobCheck?.interest],
    });
  }
}
