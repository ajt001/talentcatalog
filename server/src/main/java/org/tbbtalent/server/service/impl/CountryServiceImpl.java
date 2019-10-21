package org.tbbtalent.server.service.impl;

import io.jsonwebtoken.lang.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tbbtalent.server.exception.EntityExistsException;
import org.tbbtalent.server.exception.EntityReferencedException;
import org.tbbtalent.server.exception.NoSuchObjectException;
import org.tbbtalent.server.model.Candidate;
import org.tbbtalent.server.model.Country;
import org.tbbtalent.server.model.Status;
import org.tbbtalent.server.repository.CandidateRepository;
import org.tbbtalent.server.repository.CountryRepository;
import org.tbbtalent.server.repository.CountrySpecification;
import org.tbbtalent.server.request.country.CreateCountryRequest;
import org.tbbtalent.server.request.country.SearchCountryRequest;
import org.tbbtalent.server.request.country.UpdateCountryRequest;
import org.tbbtalent.server.service.CountryService;
import org.tbbtalent.server.service.TranslationService;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CandidateRepository candidateRepository;
    private final CountryRepository countryRepository;
    private final TranslationService translationService;

    @Autowired
    public CountryServiceImpl(CandidateRepository candidateRepository,
                              CountryRepository countryRepository,
                              TranslationService translationService) {
        this.candidateRepository = candidateRepository;
        this.countryRepository = countryRepository;
        this.translationService = translationService;
    }

    @Override
    public List<Country> list() {
        List<Country> countries = countryRepository.findByStatus(Status.active);
        return countries;
    }

    @Override
    public List<Country> listCountries() {
        List<Country> countries = countryRepository.findByStatus(Status.active);
        return translationService.translate(countries);
    }

    @Override
    public Page<Country> searchCountries(SearchCountryRequest request) {
        Page<Country> countries = countryRepository.findAll(
                CountrySpecification.buildSearchQuery(request), request.getPageRequest());
        log.info("Found " + countries.getTotalElements() + " countries in search");
        return countries;
    }

    @Override
    public Country getCountry(long id) {
        return this.countryRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException(Country.class, id));
    }

    @Override
    @Transactional
    public Country createCountry(CreateCountryRequest request) throws EntityExistsException {
        Country country = new Country(
                request.getName(), request.getStatus());
        checkDuplicates(null, request.getName());
        return this.countryRepository.save(country);
    }


    @Override
    @Transactional
    public Country updateCountry(long id, UpdateCountryRequest request) throws EntityExistsException {
        Country country = this.countryRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException(Country.class, id));
        checkDuplicates(id, request.getName());

        country.setName(request.getName());
        country.setStatus(request.getStatus());
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public boolean deleteCountry(long id) throws EntityReferencedException {
        Country country = countryRepository.findById(id).orElse(null);
        List<Candidate> candidates = candidateRepository.findByCountryId(id);
        if (!Collections.isEmpty(candidates)){
            throw new EntityReferencedException("country");
        }
        if (country != null) {
            country.setStatus(Status.deleted);
            countryRepository.save(country);
            return true;
        }
        return false;
    }

    private void checkDuplicates(Long id, String name) {
        Country existing = countryRepository.findByNameIgnoreCase(name);
        if (existing != null && !existing.getId().equals(id) || (existing != null && id == null)){
            throw new EntityExistsException("country");
        }
    }
}
