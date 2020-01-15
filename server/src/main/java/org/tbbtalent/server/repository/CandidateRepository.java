package org.tbbtalent.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tbbtalent.server.model.Candidate;
import org.tbbtalent.server.model.DataRow;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

//    /* Used for candidate authentication */
//    @Query(" select distinct c from Candidate c "
//            + " where lower(c.email) = lower(:username) "
//            + " or lower(c.phone) = lower(:username) "
//            + " or lower(c.whatsapp) = lower(:username) ")
//    Candidate findByAnyUserIdentityIgnoreCase(@Param("username") String username);

    /* Used for JWT token parsing */
    Candidate findByCandidateNumber(String number);

    @Query(" select distinct c from Candidate c "
            + " where lower(c.candidateNumber) like lower(:candidateNumber) ")
    Page<Candidate> searchCandidateNumber(@Param("candidateNumber") String candidateNumber, Pageable pageable);

    /* Used for candidate registration to check for existing accounts with different username options */
//    Candidate findByEmailIgnoreCase(String email);
    @Query("select distinct c from Candidate c "
            + " where (lower(c.phone) = lower(?1) )"
            + " and c.status != 'deleted'")
    Candidate findByPhoneIgnoreCase(String phone);

    @Query("select distinct c from Candidate c "
            + " where (lower(c.whatsapp) = lower(:whatsapp) )"
            + " and c.status != 'deleted'")
    Candidate findByWhatsappIgnoreCase(String whatsapp);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateOccupations p "
            + " where c.id = :id ")
    Candidate findByIdLoadCandidateOccupations(@Param("id") Long id);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateEducations e "
            + " left join e.educationMajor m "
            + " where c.id = :id ")
    Candidate findByIdLoadEducations(@Param("id") Long id);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateJobExperiences e "
            + " left join e.candidateOccupation o "
            + " left join e.country co "
            + " where c.id = :id ")
    Candidate findByIdLoadJobExperiences(@Param("id") Long id);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateCertifications cert "
            + " where c.id = :id ")
    Candidate findByIdLoadCertifications(@Param("id") Long id);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateLanguages lang "
            + " where c.id = :id ")
    Candidate findByIdLoadCandidateLanguages(@Param("id") Long id);

    @Query(" select c from Candidate c "
            + " where c.user.id = :id ")
    Candidate findByUserId(@Param("id") Long userId);

    @Query(" select c from Candidate c "
            + " join c.user u "
            + " where c.id = :id ")
    Optional<Candidate> findByIdLoadUser(@Param("id") Long id);

    @Query(" select c from Candidate c "
            + " where c.nationality.id = :nationalityId ")
    List<Candidate> findByNationalityId(@Param("nationalityId") Long nationalityId);

    @Query(" select c from Candidate c "
            + " where c.country.id = :countryId ")
    List<Candidate> findByCountryId(@Param("countryId") Long countryId);

    @Query(" select distinct c from Candidate c "
            + " left join c.candidateOccupations occ "
            + " left join c.candidateJobExperiences exp "
            + " left join exp.candidateOccupation expOcc "
            + " left join exp.country co "
            + " left join c.candidateEducations edu "
            + " left join edu.educationMajor maj "
            + " left join c.candidateCertifications cert "
            + " left join c.candidateLanguages lang "
            + " where c.user.id = :id ")
    Candidate findByUserIdLoadProfile(@Param("id") Long userId);

    @Query("select new org.tbbtalent.server.model.DataRow(c.nationality.name, count(c))"
            + " from Candidate c "
            +" group by c.nationality.name order by count(c) desc")
    List<DataRow> countByNationalityOrderByCount();

}
