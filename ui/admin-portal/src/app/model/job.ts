/*
 * Copyright = c) 2022 Talent Beyond Boundarie.
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
import {HasId, PagedSearchRequest} from "./base";
import {SavedList} from "./saved-list";
import {User} from "./user";
import {Partner} from "./partner";
import {SavedSearch} from "./saved-search";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {getExternalHref} from "../util/url";
import {Country} from "./country";

export interface JobIds extends HasId {
  sfId: string;
}

export interface Job extends JobIds {
  /**
   * We need to get fields:
   * - Website
   * - Employer Description
   * - Employer Hiring commitment
   * We need to calculate:
   * - Have they hired from us before
   * Should these fields related to the employer sit in own table, an employer table?
   */
  // todo get field from SF Account to display in JOI
  website: string;
  // todo get field from SF Account to display in JOI
  employerDescription: string;
  // todo get field from SF Job Opp to display in JOI
  employerHiringCommitment: number;
  // todo calculate this using data from SF
  employerPreviousHire: boolean;
  accepting: boolean;
  contactEmail: string;
  contactUser: User;
  country: string;
  createdBy: User;
  createdDate: Date;
  employer: string;
  exclusionList: SavedList;
  jobSummary: string;
  name: string;
  publishedBy: User;
  publishedDate: Date;
  recruiterPartner: Partner;
  stage: JobOpportunityStage;
  starringUsers: User[];
  submissionDueDate: Date;
  submissionList: SavedList;
  suggestedList: SavedList;
  suggestedSearches: SavedSearch[];
  updatedBy: User;
  updatedDate: Date;
}

export interface JobIntakeData {
  minSalaryAgreement?: boolean;
  minSalaryRequired?: string;
  immigrationPathways?: VisaPathway[];
  benefits?: string;
  description?: string;
  education?: string;
  experience?: string;
  skills?: string;
  title?: string;
}

export interface VisaPathway {
  name: string;
  description: string;
  country: Country;
}

export function getJobExternalHref(router: Router, location: Location, job: Job): string {
  return getExternalHref(router, location, ['job', job.id]);
}

export type JobDocType = "jd" | "joi";

/**
 * Note that the string values of this enum MUST match the actual stage names for job
 * opportunities on Salesforce.
 * <p/>
 * See https://docs.google.com/document/d/1B6DmpYaONV_yNmyAqL76cu0TUQcpNgKtOmKELCkpRoc/edit#heading=h.qx7je1tuwoqv
 */
export enum JobOpportunityStage {
  prospect = "Prospect",
  briefing = "Briefing",
  pitching = "Pitching",
  identifyingRoles = "Identifying roles",
  candidateSearch = "Candidate search",
  visaEligibility = "Visa eligibility",
  cvPreparation = "CV preparation",
  cvReview = "CV review",
  recruitmentProcess = "Recruitment process",
  jobOffer = "Job offer",
  visaPreparation = "Visa preparation",
  postHireEngagement = "Post hire engagement",
  hiringCompleted = "Hiring completed",
  ineligibleEmployer = "Ineligible employer",
  ineligibleOccupation = "Ineligible occupation",
  ineligibleRegion = "Ineligible region",
  noInterest = "No interest",
  noJobOffer = "No job offer",
  noPrPathway = "No PR pathway",
  noSuitableCandidates = "No suitable candidates",
  noVisa = "No visa",
  tooExpensive = "Too expensive",
  tooHighWage = "Too high wage",
  tooLong = "Too long"
}

export class SearchJobRequest extends PagedSearchRequest {
  accepting?: boolean;
  keyword?: string;
  ownedByMe?: boolean;
  ownedByMyPartner?: boolean;
  published?: boolean;
  sfOppClosed?: boolean;
  stages?: string[];
  starred?: boolean;
}

export interface UpdateJobRequest {
  sfJoblink?: string;
  submissionDueDate?: Date;
}

