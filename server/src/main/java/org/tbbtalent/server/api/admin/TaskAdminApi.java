/*
 * Copyright (c) 2021 Talent Beyond Boundaries.
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

package org.tbbtalent.server.api.admin;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tbbtalent.server.model.db.TaskDtoHelper;
import org.tbbtalent.server.model.db.TaskImpl;
import org.tbbtalent.server.request.PagedSearchRequest;
import org.tbbtalent.server.request.country.UpdateCountryRequest;
import org.tbbtalent.server.service.db.TaskService;

@RestController()
@RequestMapping("/api/admin/task")
public class TaskAdminApi implements
    //todo Need to add in proper task request objects here
        ITableApi<PagedSearchRequest, UpdateCountryRequest, UpdateCountryRequest> {

    private final TaskService taskService;

    @Autowired
    public TaskAdminApi(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public @NotNull List<Map<String, Object>> list() {
        List<TaskImpl> tasks = taskService.listTasks();
        return TaskDtoHelper.getTaskDto().buildList(tasks);
    }

    @Override
    public @NotNull Map<String, Object> searchPaged(
        @Valid PagedSearchRequest request) {
        Page<TaskImpl> tasks = this.taskService.searchTasks(request);
        return TaskDtoHelper.getTaskDto().buildPage(tasks);
    }

}