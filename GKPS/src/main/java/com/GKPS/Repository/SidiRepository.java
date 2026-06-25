package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Sidi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SidiRepository extends MongoRepository<Sidi, String> {
    List<Sidi> findByPersonId(String personId);
    Optional<Sidi> findByIdAndApprovalStatus(String id, String approvalStatus);
    List<Sidi> findByApprovalStatus(String approvalStatus);
    List<Sidi> findByApproveBy(String approveBy);
}
