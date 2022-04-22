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

package org.tbbtalent.server.model.db;

import org.tbbtalent.server.model.db.task.TaskType;
import org.tbbtalent.server.util.dto.DtoBuilder;
import org.tbbtalent.server.util.dto.DtoPropertyFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * DTOs for Tasks and TaskAssignments
 *
 * @author John Cameron
 */
public class TaskDtoHelper {

    /**
     * Filters out properties in the DtoBuilder based on the taskType.
     */
    static private class TaskDtoPropertyFilter implements DtoPropertyFilter {

        //These properties should only be extracted for taskType = Question
        private final Set<String> questionOnlyProperties =
            new HashSet<>(Arrays.asList("answer", "candidateAnswerField", "allowedAnswers"));

        //These properties should only be extracted for taskType = Upload
        private final Set<String> uploadOnlyProperties =
            new HashSet<>(Arrays.asList("uploadType", "uploadSubfolderName", "uploadableFileTypes"));

        public boolean ignoreProperty(Object o, String property) {
            Object taskType = getProperty(o, "taskType");

            //Ignore taskType dependent properties if taskType does not match
            boolean ignore =
                questionOnlyProperties.contains(property) && !TaskType.Question.equals(taskType) ||
                uploadOnlyProperties.contains(property) && !TaskType.Upload.equals(taskType);

            return ignore;
        }
    };

    public static DtoBuilder getTaskAssignmentDto() {
        return new DtoBuilder(new TaskDtoPropertyFilter())
            .add("id")
            .add("abandonedDate")
            .add("candidateNotes")
            .add("completedDate")
            .add("dueDate")
            .add("status")
            .add("task", getTaskDto())
            .add("answer")
            ;
    }

    public static DtoBuilder getTaskDto() {
        return new DtoBuilder(new TaskDtoPropertyFilter())
            .add("id")
            .add("name")
            .add("daysToComplete")
            .add("description")
            .add("displayName")
            .add("optional")
            .add("helpLink")
            .add("taskType")
            .add("uploadSubfolderName")
            .add("uploadableFileTypes")
            .add("candidateAnswerField")
            .add("allowedAnswers", getAllowedQuestionTaskAnswerDto())
            .add("createdBy", getUserDto())
            .add("createdDate")
            ;
    }

    private static DtoBuilder getAllowedQuestionTaskAnswerDto() {
        return new DtoBuilder()
            .add("name")
            .add("displayName")
            ;
    }

    public static DtoBuilder getUserDto() {
        return new DtoBuilder()
            .add("id")
            .add("firstName")
            .add("lastName")
            ;
    }


}
