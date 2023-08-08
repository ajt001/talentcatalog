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

import {Directive, Input, OnInit} from "@angular/core";
import {Candidate, CandidateIntakeData, CandidateVisa} from "../../../model/candidate";

@Directive()
export abstract class VisaCheckComponentBase implements OnInit {
  /**
   * This is the existing candidate data (if any) which is used to
   * initialize the form data.
   */
  @Input() candidateIntakeData: CandidateIntakeData;

  /**
   * Visa Check Object for selected country.
   */
  @Input() visaCheckRecord: CandidateVisa;

  /**
   * Candidate the visa check/intake data relates to.
   */
  @Input() candidate: Candidate;

  ngOnInit() {
  }
}
