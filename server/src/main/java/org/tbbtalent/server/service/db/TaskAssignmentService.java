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

import java.util.List;
import org.springframework.lang.Nullable;
import org.tbbtalent.server.model.db.Candidate;
import org.tbbtalent.server.model.db.SavedList;
import org.tbbtalent.server.model.db.Status;
import org.tbbtalent.server.model.db.task.Task;
import org.tbbtalent.server.model.db.task.TaskAssignment;

// TODO: Notes for Caroline: The methods and documentation are taken from your "TDD Operations Tasks"
// design document - with a bit more detail added.
// This documentation is also used to define the testing that needs to be carries out in
// TaskAssignmentServiceTest

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
     * @param task      - Task to be associated with the newly created TaskAssignment
     * @param candidate - Candidate associated with the newly created TaskAssignment
     * @return Newly created task assignment associated with candidate and task
     */
    TaskAssignment assignTaskToCandidate(Task task, Candidate candidate);

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
     * @param task - Task to be associated with the newly created TaskAssignment
     * @param list - List of candidates to whom the task should be assigned
     */
    void assignTaskToList(Task task, SavedList list);

    /**
     * Fetch task assignments for a given candidate by Status (eg active or inactive)
     *
     * @param candidate - Candidate whose task assignments we want
     * @param status    - Status (active or inactive) of task assignments. If null all task
     *                  assignments are fetched.
     * @return Task assignments associated with candidate
     */
    List<TaskAssignment> getCandidateTaskAssignments(Candidate candidate, @Nullable Status status);

}
