package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Marriage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageRepository extends MongoRepository<Marriage, String> {
    Optional<Marriage> findByHusbandIdAndWifeId(String hubsandId, String wifeId);
    List<Marriage> findByApprovalStatus(String approvalStatus);
//    List<Marriage> findByApproveBy(String approveBy);
    List<Marriage> findByHusbandId(String husbandId);
    List<Marriage> findByWifeId(String wifeId);
    @Query("{'marriageDate': { $regex: ?0 } }")
    List<Marriage> findByYear(String year);
}
