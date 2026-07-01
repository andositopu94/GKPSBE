package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Baptism;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BaptismRepository extends MongoRepository<Baptism, String> {
    List<Baptism> findByPersonId(String personId);
    List<Baptism> findByApprovalStatus(String approvalStatus);
    List<Baptism> findByPersonIdAndApprovalStatus(String personId, String approvalStatus);
    List<Baptism> findByApproveBy(String approveBy);
    List<Baptism> findByBaptismDate(LocalDate baptismDate);
    @Query("{ 'baptismDate': { $gte: ?0, $lt: ?1 } }")
    List<Baptism> findByYear(LocalDate startYear, LocalDate endYear);


}
