package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Marriage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarriageRepository extends MongoRepository<Marriage, String> {
    List<Marriage> findByPersonId(String personId);
    List<Marriage> findByApprovalStatus(String approvalStatus);
    @Query("{ '_id':?0, 'approvalStatus' : ?1 }")
    Optional<Marriage> findByIdAndApprovalStatus(String id, String approvalStatus);
    List<Marriage> findByApproveBy(String approveBy);
    List<Marriage> findByHusbandId(String husbandId);
    List<Marriage> findByWifeId(String wifeId);
}
