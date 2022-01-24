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

package org.tbbtalent.server.service.db.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.tbbtalent.server.model.db.QuestionTask;
import org.tbbtalent.server.model.db.TaskImpl;
import org.tbbtalent.server.model.db.UploadTaskImpl;
import org.tbbtalent.server.repository.db.TaskRepository;
import org.tbbtalent.server.request.CreateTaskRequest;
import org.tbbtalent.server.request.task.CreateQuestionTaskRequest;
import org.tbbtalent.server.request.task.CreateUploadTaskRequest;
import org.tbbtalent.server.service.db.TaskService;


// TODO: 15/1/22 Services should implement interfaces which define the operations of the service.
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskImpl createTask(CreateTaskRequest request) {
        return null;
    }

    public QuestionTask createQuestionTask(CreateQuestionTaskRequest request) {
        return null;
    }

    public UploadTaskImpl createUploadTask(CreateUploadTaskRequest request) {
        return null;
    }

    @Override
    public List<TaskImpl> listTasks() {
        return taskRepository.findAll();
    }
}
