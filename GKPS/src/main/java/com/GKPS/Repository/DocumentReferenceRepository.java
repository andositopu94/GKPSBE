package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.DocumentReference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentReferenceRepository extends MongoRepository<DocumentReference, String> {
    List<DocumentReference> findByPersonId(String personId);
    List<DocumentReference> findByReferenceTypeAndReferenceID(String referenceType, String referenceID);
    List<DocumentReference> findBySubmittedBy(String subbmitedBy);
    List<DocumentReference> findByStatus(String status);
    List<DocumentReference> findByReviewedBy(String reviewedBy);
    List<DocumentReference> findByisActive();
    List<DocumentReference> findByReferenceType(String referenceType);
    Optional<DocumentReference> findByIdAndIsActive(String id);
}
