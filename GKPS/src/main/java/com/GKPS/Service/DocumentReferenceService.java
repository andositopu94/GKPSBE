package com.GKPS.Service;

import com.GKPS.Model.Dokumentasi.DocumentReference;
import com.GKPS.Repository.DocumentReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentReferenceService {
    private final DocumentReferenceRepository documentReferenceRepository;

    // Submit a document reference for approval
    public DocumentReference submitForApproval(String personId, String referenceType, String referenceID, String subbmitedBy, List<String> documentIds) {
        DocumentReference documentReference = new DocumentReference();
        documentReference.setPersonId(personId);
        documentReference.setReferenceType(referenceType);
        documentReference.setReferenceId(referenceID);
        documentReference.setSubbmitedBy(subbmitedBy);
        documentReference.setStatus("Pending");
        documentReference.setSubbmitDate(LocalDateTime.now());
        documentReference.setActive(true);
        return documentReferenceRepository.save(documentReference);
    }

    // Approve a document reference
    public DocumentReference approveDocumentReference(String id, String reviewedBy, String notes    ) {
        DocumentReference documentReference = documentReferenceRepository.findByIdAndIsActive(id)
                .orElseThrow(() -> new RuntimeException("Document reference not found or inactive"));

        documentReference.setStatus("Approved");
        documentReference.setReviewedBy(reviewedBy);
        documentReference.setReviewDate(LocalDateTime.now());
        documentReference.setNotes(notes);
        return documentReferenceRepository.save(documentReference);
    }

    // Reject a document reference
    public DocumentReference rejectDocumentReference(String id, String reviewedBy, String rejectReason, String notes) {
        DocumentReference documentReference = documentReferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document reference not found or inactive"));

        documentReference.setStatus("Rejected");
        documentReference.setReviewedBy(reviewedBy);
        documentReference.setReviewDate(LocalDateTime.now());
        documentReference.setRejectReason(rejectReason);
        documentReference.setNotes(notes);
        return documentReferenceRepository.save(documentReference);

    }

    // Get all document references for a specific person
    public List<DocumentReference> findByPersonId(String personId) {
        return documentReferenceRepository.findByPersonId(personId);
    }

    // Get all document references for a specific reference type and reference ID
    public List<DocumentReference> findByReferenceTypeAndReferenceID(String referenceType, String referenceID) {
        return documentReferenceRepository.findByReferenceTypeAndReferenceID(referenceType, referenceID);
    }

    // Get all document references submitted by a specific user
    public List<DocumentReference> findBySubmittedBy(String subbmitedBy) {
        return documentReferenceRepository.findBySubmittedBy(subbmitedBy);
    }

    // Get all document references by reviewed by a specific user
    public List<DocumentReference> findByReviewedBy(String reviewedBy) {
        return documentReferenceRepository.findByReviewedBy(reviewedBy);
    }

    // Get all document references by status Pending
    public List<DocumentReference> findPendingDocuments() {
        return documentReferenceRepository.findByStatus("Pending");
    }

    public List<DocumentReference> findApprovedDocuments() {
        return documentReferenceRepository.findByStatus("Approved");
    }

    public List<DocumentReference> findRejectedDocuments() {
        return documentReferenceRepository.findByStatus("Rejected");
    }

    //hapus dokumen referensi
    public void deleteDocumentReference(String id) {
        DocumentReference documentReference = documentReferenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document reference not found or inactive"+ id ));
        documentReference.setActive(false);
        documentReferenceRepository.save(documentReference);
    }
}
