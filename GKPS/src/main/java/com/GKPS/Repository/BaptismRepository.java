package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Baptism;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaptismRepository extends MongoRepository<Baptism, String> {
    /**
     * Find baptism by person ID
     */
    Optional<Baptism> findByPersonId(String personId);

    /**
     * Find baptisms by approval status
     */
    List<Baptism> findByApprovalStatus(String approvalStatus);

    /**
     * Find baptisms by date
     */
    List<Baptism> findByBaptismDate(LocalDate baptismDate);

    /**
     * Find baptisms by year
     */
    @Query("{ 'baptismDate': { $gte: ?0, $lt: ?1 } }")
    List<Baptism> findByYear(LocalDate startYear, LocalDate endYear);

    /**
     * Find baptisms by person ID and approval status
     */
    List<Baptism> findByPersonIdAndApprovalStatus(String personId, String approvalStatus);
}
