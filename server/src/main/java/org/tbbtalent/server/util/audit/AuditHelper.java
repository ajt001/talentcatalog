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

package org.tbbtalent.server.util.audit;

import org.tbbtalent.server.model.db.Candidate;
import org.tbbtalent.server.model.db.User;

public class AuditHelper {
    /**
     * Depending on if the request came from the admin or candidate portal, sets audit fields with correct user. If
     * User and Candidate are the same, then it's candidate portal. If User and Candidate are different comes from admin.
     * @param candidate Candidate being altered
     * @param user Logged in User
     */
    public static void setAuditFieldsFromUser(Candidate candidate, User user) {
        if (candidate.getUser() == user ) {
            candidate.setAuditFields(candidate.getUser());
        } else {
            candidate.setAuditFields(user);
        }
    }
}
