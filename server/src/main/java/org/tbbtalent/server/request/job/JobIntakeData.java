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

package org.tbbtalent.server.request.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Has fields for all candidate intake fields.
 * <p/>
 * An instance of this class is received from the browser on each update.
 * Each update will come from a single intake component - comprising one or
 * a small number of fields. Just values for those fields will be populated
 * in the class. All other fields will be null.
 * <p/>
 * Null fields are ignored - non null fields update the database.
 *
 * @author John Cameron
 */
@Getter
@Setter
@ToString
public class JobIntakeData {

    private String description;
}