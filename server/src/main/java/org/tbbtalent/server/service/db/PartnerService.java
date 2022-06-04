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
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.tbbtalent.server.exception.EntityExistsException;
import org.tbbtalent.server.exception.InvalidRequestException;
import org.tbbtalent.server.exception.NoSuchObjectException;
import org.tbbtalent.server.model.db.PartnerImpl;
import org.tbbtalent.server.model.db.partner.Partner;
import org.tbbtalent.server.request.partner.SearchPartnerRequest;
import org.tbbtalent.server.request.partner.UpdatePartnerRequest;

/**
 * Service for managing {@link Partner}
 *
 * @author John Cameron
 */
public interface PartnerService {

    /**
     * Get the Partner with the given id.
     * @param partnerId ID of partner to get
     * @return partner
     * @throws NoSuchObjectException if there is no Partner with this id.
     */
    @NonNull
    Partner getPartner(long partnerId) throws NoSuchObjectException;

    /**
     * Get the default source partner (eg TBB).
     * @return Default source partner (as defined in database - defaultSourcePartner field)
     * @throws NoSuchObjectException if no default source partner is configured
     */
    @NonNull
    Partner getDefaultSourcePartner() throws NoSuchObjectException;

    /**
     * Get the partner associated with the given domain name.
     * @param hostDomain Domain name - eg tbbtalent.org
     * @return Partner associated with domain or null if no partner is found associated with that domain
     */
    @Nullable
    Partner getPartnerFromHost(String hostDomain);

    /**
     * Get all partners
     * @return List of partners
     */
    List<PartnerImpl> listPartners();

    /**
     * Get the partners as a paged search request
     * @param request - Paged Search Request
     * @return Page of partners
     */
    Page<PartnerImpl> searchPartners(SearchPartnerRequest request);

    /**
     * Create a partner.
     * @param request Request contains partner data
     * @return Created partner
     * @throws EntityExistsException if registration_url already exists
     * @throws InvalidRequestException if required attribute, name, is not present or partner type
     * is unrecognized.
     * @throws NoSuchObjectException if there is an unknown country id in the request
     */
    @NonNull
    PartnerImpl create(UpdatePartnerRequest request)
        throws EntityExistsException, InvalidRequestException, NoSuchObjectException;

    /**
     * Update the partner with the given ID.
     * @param id of partner to update
     * @param request Request contains updated data
     * @return Updated partner
     * @throws EntityExistsException if updated registration_url already exists.
     * @throws InvalidRequestException if partner type is unrecognized.
     * @throws NoSuchObjectException if there is not a partner with this id.
     */
    @NonNull
    PartnerImpl update(long id, UpdatePartnerRequest request) throws EntityExistsException, NoSuchObjectException;
}