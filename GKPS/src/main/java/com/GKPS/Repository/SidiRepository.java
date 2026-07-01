package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Sidi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SidiRepository extends MongoRepository<Sidi, String> {
    /**
     * Find sidi by person ID
     */
    Optional<Sidi> findByPersonId(String personId);

    /**
     * Find sidi records by approval status
     */
    List<Sidi> findByApprovalStatus(String approvalStatus);

    /**
     * Find sidi by sidi date
     */
    List<Sidi> findBySidiDate(LocalDate sidiDate);

    /**
     * Find sidi records by year
     */
    @Query("{ 'sidiDate': { $gte: ?0, $lt: ?1 } }")
    List<Sidi> findByYear(LocalDate startYear, LocalDate endYear);

    /**
     * Find sidi by person ID and approval status
     */
    List<Sidi> findByPersonIdAndApprovalStatus(String personId, String approvalStatus);
}
