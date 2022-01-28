/*
 * Copyright (c) 2022 Talent Beyond Boundaries.
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

package org.tbbtalent.server.service.db;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import org.tbbtalent.server.exception.NoSuchObjectException;
import org.tbbtalent.server.model.db.Candidate;
import org.tbbtalent.server.model.db.SavedList;
import org.tbbtalent.server.model.db.Status;
import org.tbbtalent.server.model.db.TaskAssignmentImpl;
import org.tbbtalent.server.model.db.TaskImpl;
import org.tbbtalent.server.model.db.User;
import org.tbbtalent.server.model.db.task.TaskAssignment;
import org.tbbtalent.server.request.task.UpdateTaskAssignmentRequest;

/**
 * Service for managing {@link TaskAssignment}s.
 *
 * @author John Cameron
 */
public interface TaskAssignmentService {

    /**
     * An active TaskAssignment object is added to the candidate's task assignments.
     * <p/>
     * A user may want to assign a task to a candidate. For example if a candidate has to complete a
     * task uniquely for a particular position they’ve accepted, a user might assign a newly created
     * unique task to this candidate.
     * <p/>
     * Note that if the task is composed of subtasks, each subtask is assigned to the candidate.
     *
     * @param user      - User who made assignment
     * @param task      - Task to be associated with the newly created TaskAssignment
     * @param candidate - Candidate associated with the newly created TaskAssignment
     * @param dueDate   - Custom due date (can be null, which case the days to complete will be used
     *                  to set)
     * @return Newly created task assignment associated with candidate and task
     */
    TaskAssignmentImpl assignTaskToCandidate(
        User user, TaskImpl task, Candidate candidate, @Nullable LocalDate dueDate);

    /**
     * A user may want to assign a task to a list of candidates. For example if there’s a list of
     * candidates shortlisted for a job opportunity, they might all be required to complete some
     * pre-offer tasks via a task list.
     * <p/>
     * A new active TaskAssignment object for each candidate in the list, associated with the given
     * task.
     * <p/>
     * Note that if the task is composed of subtasks, each subtask is assigned to the candidate.
     * <p/>
     * This is effectively multiple applications of {@link #assignTaskToCandidate} for each
     * candidate in the list. See above doc for that method.
     *
     * @param user    - User who made assignment
     * @param task    - Task to be associated with the newly created TaskAssignment
     * @param list    - List of candidates to whom the task should be assigned
     * @param dueDate - Custom due date (can be null, which case the days to complete will be used
     *                to set)
     */
    void assignTaskToList(User user, TaskImpl task, SavedList list, @Nullable LocalDate dueDate);

    /**
     * Get the TaskAssignment with the given id.
     *
     * @param taskAssignmentId ID of TaskAssignment to get
     * @return TaskAssigment
     * @throws NoSuchObjectException if there is no TaskAssignment with this id.
     */
    @NonNull
    TaskAssignmentImpl get(long taskAssignmentId) throws NoSuchObjectException;

    /**
     * Update the task assignment with the given id.
     *
     * @param taskAssignmentId ID of the TaskAssignment to update
     * @param request          Update request containing the due date
     * @return Updated Task Assignment
     * @throws NoSuchObjectException If no such task assignment exists with that id
     */
    @NonNull
    TaskAssignmentImpl update(long taskAssignmentId, UpdateTaskAssignmentRequest request)
        throws NoSuchObjectException;

    /**
     * Deactivate the task assignment record and set as inactive.
     *
     * @param loggedInUser     - the logged in admin user who deactivated the task assignment
     * @param taskAssignmentId - Task Assignment to be removed.
     * @return true/false depending on success
     * @throws NoSuchObjectException If no such task assignment exists with that id
     */
    boolean removeTaskAssignment(User loggedInUser, long taskAssignmentId)
        throws NoSuchObjectException;

    /**
     * Fetch task assignments for a given candidate by Status (eg active or inactive)
     *
     * @param candidate - Candidate whose task assignments we want
     * @param status    - Status (active or inactive) of task assignments. If null all task
     *                  assignments are fetched.
     * @return Task assignments associated with candidate
     */
    List<TaskAssignment> getCandidateTaskAssignments(Candidate candidate, @Nullable Status status);

    /**
     * Marks the given task assignment as completed.
     * @param ta Task assignment
     */
    void completeTaskAssignment(TaskAssignment ta);

    /**
     * Marks the given upload task assignment as completed if the given file is successfully
     * uploaded according to the UploadTask's upload attributes.
     * @param ta Task assignment
     * @throws IOException if the upload fails
     * @throws ClassCastException if the task assignment is not for an UploadTask
     */
    void completeUploadTaskAssignment(TaskAssignment ta, MultipartFile file)
        throws IOException, ClassCastException;
}