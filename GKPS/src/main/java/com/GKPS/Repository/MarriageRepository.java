package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Marriage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageRepository extends MongoRepository<Marriage, String> {
    /**
     * Find marriage by husband and wife IDs
     */
    Optional<Marriage> findByHusbandIdAndWifeId(String husbandId, String wifeId);

    /**
     * Find marriages by approval status
     */
    List<Marriage> findByApprovalStatus(String approvalStatus);

    /**
     * Find marriages by marriage date
     */
    List<Marriage> findByMarriageDate(String marriageDate);

    /**
     * Find marriages by husband ID
     */
    List<Marriage> findByHusbandId(String husbandId);

    /**
     * Find marriages by wife ID
     */
    List<Marriage> findByWifeId(String wifeId);

    /**
     * Find marriages by year (using regex on marriageDate)
     */
    @Query("{ 'marriageDate': { $regex: ?0 } }")
    List<Marriage> findByYear(String year);
}
