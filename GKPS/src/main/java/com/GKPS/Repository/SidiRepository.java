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
    Optional<Sidi> findByPersonId(String personId);
    List<Sidi> findByPersonIdAndApprovalStatus(String personId, String approvalStatus);
    List<Sidi> findByApprovalStatus(String approvalStatus);

    List<Sidi> findBySidiDate(LocalDate sidiDate);

    @Query("{ 'sidiDate': { $gte: ?0, $lt: ?1 } }")
    List<Sidi> findByYear(LocalDate startYear, LocalDate endYear);

}
