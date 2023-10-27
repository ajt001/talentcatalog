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

package org.tctalent.server.api.admin;

import java.util.List;
import org.tctalent.server.model.db.DataRow;

public class StatsApiTestUtil {

  static List<DataRow> getGenderStats() {
    return List.of(
        new DataRow("male", 15111L),
        new DataRow("undefined", 3772L),
        new DataRow("female", 2588L)
    );
  }

  static List<DataRow> getRegistrationStats() {
    return List.of(
        new DataRow("2016-06-04", 4L),
        new DataRow("2016-06-10", 1L),
        new DataRow("2016-06-14", 1L)
    );
  }

  static List<DataRow> getRegistrationByOccupationStats() {
    return List.of(
        new DataRow("undefined", 11414L),
        new DataRow("Unknown", 1652L),
        new DataRow("Teacher", 777L)
    );
  }

}
