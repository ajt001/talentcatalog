package org.tbbtalent.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tbbtalent.server.model.Role;
import org.tbbtalent.server.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
    @Query("select distinct u from User u "
            + " where lower(u.username) = lower(:username) "
            + " and u.role = :role "
            + " and u.status != 'deleted' ")
    User findByUsernameAndRole(@Param("username") String username,
                               @Param("role") Role role);
    
    @Query("select distinct u from User u "
            + " where lower(u.username) = lower(:username) "
            + " and u.status != 'deleted'")
    User findByUsernameIgnoreCase(@Param("username") String username);

    /* Used for candidate authentication */
    @Query("select distinct u from User u "
            + " where (lower(u.email) = lower(:email) )"
            + " and u.status != 'deleted'")
    User findByEmailIgnoreCase(@Param("email") String email);

    @Query("select u from User u where u.resetToken = :token and u.status != 'deleted'")
    User findByResetToken(@Param("token") String token);

    @Query(" select distinct u from User u "
            + " left join fetch u.sharedSearches "
            + " where u.id = :id ")
    User findByIdLoadSharedSearches(@Param("id") Long id);


}
