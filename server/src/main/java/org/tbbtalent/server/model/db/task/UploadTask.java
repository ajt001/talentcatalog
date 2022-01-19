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

package org.tbbtalent.server.model.db.task;

import org.springframework.lang.NonNull;

/**
 * Task where candidate is required to upload a file.
 *
 * @author John Cameron
 */
public interface UploadTask extends Task {

    /**
     * Specifies information including where the upload will be made to, how it is named,
     * and which file types are allowed.
     * @return Information for managing an upload
     */
    @NonNull
    UploadInfo getUploadInfo();
}
