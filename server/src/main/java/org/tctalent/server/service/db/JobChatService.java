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

package org.tctalent.server.service.db;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.tctalent.server.exception.EntityExistsException;
import org.tctalent.server.exception.InvalidRequestException;
import org.tctalent.server.exception.NoSuchObjectException;
import org.tctalent.server.model.db.CandidateOpportunity;
import org.tctalent.server.model.db.JobChat;
import org.tctalent.server.model.db.JobChatType;
import org.tctalent.server.model.db.PartnerImpl;
import org.tctalent.server.model.db.SalesforceJobOpp;

public interface JobChatService {

    /**
     * Creates a new job chat.
     * <p/>
     * Parameters which are not needed for job type may be null.
     * @param type Type of Job Chat
     * @param job Job associated with chat
     * @param sourcePartner Source partner associated with chat
     * @param candidateOpp Candidate opportunity associated with chat
     * @return Created job chat
     * @throws EntityExistsException if there is already a job chat matching the given request.
     */
    @NonNull JobChat createJobChat(JobChatType type, @Nullable SalesforceJobOpp job,
        @Nullable PartnerImpl sourcePartner, @Nullable CandidateOpportunity candidateOpp)
            throws EntityExistsException;

    /**
     * Finds a job chat matching the given type and paramteres, creating one if needed.
     * <p/>
     * Parameters which are not needed for job type may be null.
     * @param type Type of Job Chat
     * @param job Job associated with chat
     * @param sourcePartner Source partner associated with chat
     * @param candidateOpp Candidate opportunity associated with chat
     * @return Found or created job chat
     * @throws InvalidRequestException if required objects for this chat type are missing (null).
     */
    @NonNull JobChat getOrCreateJobChat(JobChatType type, @Nullable SalesforceJobOpp job,
        @Nullable PartnerImpl sourcePartner, @Nullable CandidateOpportunity candidateOpp)
        throws InvalidRequestException;

    /**
     * Creates a job chat which is associated with the Job Creator, but not any particular source
     * partner or candidate.
     * @param type Should be {@link JobChatType#JobCreatorAllSourcePartners} or
     * {@link JobChatType#AllJobCandidates}
     * @param job Job associated with chat
     * @return created JobChat
     * @throws InvalidRequestException if type is not allowed
     */
    @NonNull JobChat createJobCreatorChat(
        @NonNull JobChatType type, @NonNull SalesforceJobOpp job) throws InvalidRequestException;

    /**
     * Creates a {@link JobChatType#JobCreatorSourcePartner} type JobChat.
     * <p/>
     * This is associated with a particular Source Partner and Job (no association with any
     * particular candidate)
     * @param job Job associated with chat
     * @param sourcePartner Source partner asssociated with chat
     * @return created JobChat
     */
    @NonNull JobChat createJobCreatorSourcePartnerChat(
        @NonNull SalesforceJobOpp job, @NonNull PartnerImpl sourcePartner);

    /**
     * Creates a JobChat associated with a Candidate Opportunity
     * @param type Should be {@link JobChatType#CandidateProspect} or
     * {@link JobChatType#CandidateRecruiting}
     * @param candidateOpp Candidate opportunity associated with job chat
     * @return created JobChat
     * @throws InvalidRequestException if type is not allowed
     */
    @NonNull JobChat createCandidateOppChat(
        @NonNull JobChatType type, @NonNull CandidateOpportunity candidateOpp)
        throws InvalidRequestException;

    /**
     * Get the JobChat with the given id.
     * @param id Id of job chat to get
     * @return JobChat
     * @throws NoSuchObjectException if there is no JobChat with this id.
     */
    @NonNull
    JobChat getJobChat(long id) throws NoSuchObjectException;

    /**
     * Return all JobChats.
     * @return JobChats
     */
    @NonNull
    List<JobChat> listJobChats();
}