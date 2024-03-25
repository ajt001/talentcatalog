/*
 * Copyright (c) 2024 Talent Beyond Boundaries.
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

import {CandidateOpportunityStage} from "./candidate-opportunity";
import {JobOpportunityStage} from "./job";
import {PagedFilteredSearchRequest} from "./base";
import {User} from "./user";

export interface HelpLink {
  id: number,
  label: string,
  link: string,
  countryId?: number,
  caseStage?: CandidateOpportunityStage,
  focus?: HelpFocus,
  jobStage?: JobOpportunityStage,
  nextStepInfo?: NextStepInfo,
  createdBy: User;
  createdDate: Date;
  updatedBy: User
  updatedDate: Date;
}

export enum HelpFocus {
  closeOpp,
  updateNextStep,
  updateStage
}

export interface NextStepInfo {
  nextStepDays: number,
  nextStepName: string,
  nextStepText: string
}

export class SearchHelpLinkRequest extends PagedFilteredSearchRequest {
  countryId?: number;
}

export class UpdateHelpLinkRequest {
  label: string;
  link: string;
  countryId?: number;
  caseStage?: CandidateOpportunityStage;
  focus?: HelpFocus;
  jobStage?: JobOpportunityStage;
  nextStepInfo?: NextStepInfo;
}
