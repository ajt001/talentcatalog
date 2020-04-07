package org.tbbtalent.server.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.tbbtalent.server.model.SavedSearch;
import org.tbbtalent.server.model.SavedSearchSubtype;
import org.tbbtalent.server.model.SavedSearchType;
import org.tbbtalent.server.model.Status;
import org.tbbtalent.server.model.User;
import org.tbbtalent.server.request.search.SearchSavedSearchRequest;

public class SavedSearchSpecification {

    public static Specification<SavedSearch> buildSearchQuery(
            final SearchSavedSearchRequest request, User loggedInUser) {

        //Returns an anonymous class implementing the Specification interface.
        //There is only one method that needs to be implemented on that 
        //interface because all the other methods are defaults or static.
        //That method is the toPredicate method - 
        // Predicate toPredicate(Root<SavedSearch> savedSearch, CriteriaQuery<?> query, CriteriaBuilder builder)
        //toPredicate creates the WHERE clause for the given CriteriaQuery
        return (savedSearch, query, builder) -> {
            
            //Conjunction means implicit AND between each term (sub Predicate)
            Predicate conjunction = builder.conjunction();
            query.distinct(true);

            /*
              WHERE
                 like keyword
                 and
                 equals saved search type
                 and 
                 search status = active
                 and
                 (
                    owned
                    or
                    shared
                    or
                    fixed
                 )
             */

            // ONLY SHOW ACTIVE SAVED SEARCHES
            conjunction.getExpressions().add(
                    builder.equal(savedSearch.get("status"), Status.active)
            );

            // Filter by keyword if present
            if (!StringUtils.isBlank(request.getKeyword())){
                String lowerCaseMatchTerm = request.getKeyword().toLowerCase();
                String likeMatchTerm = "%" + lowerCaseMatchTerm + "%";
                conjunction.getExpressions().add(
                     builder.like(builder.lower(savedSearch.get("name")), likeMatchTerm)
                );
            }
            
            // Filter by type if present
            SavedSearchType savedSearchType = request.getSavedSearchType();
            if (savedSearchType != null) {
                // Search for specified type
                SavedSearchSubtype savedSearchSubtype = request.getSavedSearchSubtype();
                String type = SavedSearch.makeStringSavedSearchType(
                        savedSearchType, savedSearchSubtype);
                conjunction.getExpressions().add(
                        builder.equal(savedSearch.get("type"), type)
                );
            }


            // AND (fixed OR shared OR owned) - if any are true
            Predicate ors = builder.disjunction();
            
            //If fixed is specified, only supply matching saved searches
            if (request.getFixed() != null && request.getFixed()) {
                ors.getExpressions().add(
                        builder.equal(savedSearch.get("fixed"), request.getFixed())
                );
            }

            //If shared is specified, only supply searches shared with the owner
            if (request.getShared() != null && request.getShared()) {
                if (loggedInUser != null) {
                    List<SavedSearch> sharedSearches = loggedInUser.getSharedSearches();
                    if (!sharedSearches.isEmpty()) {
                        List<Long> sharedIDs = new ArrayList<>();
                        for (SavedSearch sharedSearch : sharedSearches) {
                            sharedIDs.add(sharedSearch.getId());
                        }
                        ors.getExpressions().add(
                                savedSearch.get("id").in( sharedIDs )
                        );
                    }
                }
            }

            //If owned by this user (ie by logged in user)
            if (request.getOwned() != null && request.getOwned()) {
                if (loggedInUser != null) {
                    ors.getExpressions().add(
                         builder.equal(savedSearch.get("createdBy"), loggedInUser.getId())
                    );
                }
            }
            
            conjunction.getExpressions().add(ors);

            return conjunction;
        };
    }

}
