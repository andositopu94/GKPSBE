package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Baptism;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaptismRepository extends MongoRepository<Baptism, String> {
    List<Baptism> findByPersonId(String personId);
    List<Baptism> findByApprovalStatus(String approvalStatus);
    List<Baptism> findByIdAndApprovalStatus(String id, String approvalStatus);
    List<Baptism> findByApproveBy(String approveBy);

}
