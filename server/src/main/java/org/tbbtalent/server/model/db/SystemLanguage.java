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

package org.tbbtalent.server.model.db;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * These are the various languages that are supported by the candidate portal (front end).
 * <p/>
 * Adding a new language:
 * <ul>
 *     <li>
 *         Use tool like Phrase to generate translations. Export file as Nested JSON
 *     </li>
 *     <li>
 *         Add new entity (table entry) for the new language to this table.
 *     </li>
 *     <li>
 *         On the front end Angular code for candidate-portal update the isSelectedLanguageRtl
 *         method in the LanguageService - depending on whether or not the language is right to left.
 *     </li>
 *     <li>
 *         Add new translations for the various drop downs (eg Countries) in the back end Settings.
 *     </li>
 * </ul>
 */
@Entity
@Table(name = "system_language")
@SequenceGenerator(name = "seq_gen", sequenceName = "system_language_id_seq", allocationSize = 1)
public class SystemLanguage extends AbstractAuditableDomainObject<Long> {

    private String language;
    private String label;

    @Enumerated(EnumType.STRING)
    private Status status;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
