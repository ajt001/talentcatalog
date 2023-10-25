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

package org.tctalent.server.request.candidate.language;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class CreateCandidateLanguageRequest {
    //todo make subclass? But then need two different methods in service taking in two different requests
    /**
     * Only required from Admin portal.
     * No candidateId needed when coming from candidate portal (as candidate is the logged in candidate).
     */
    @Nullable
    private Long candidateId;

    private Long languageId;
    private Long writtenLevelId;
    private Long spokenLevelId;

}