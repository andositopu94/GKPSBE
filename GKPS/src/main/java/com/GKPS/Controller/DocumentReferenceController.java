package com.GKPS.Controller;

import com.GKPS.Model.Dokumentasi.DocumentReference;
import com.GKPS.Service.DocumentReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document-references")
public class DocumentReferenceController {
    private final DocumentReferenceService documentReferenceService;

    @PostMapping("/submit")
    public ResponseEntity<DocumentReference> submitForApproval(@RequestParam String personId,
                                                                @RequestParam String referenceType,
                                                                @RequestParam String referenceID,
                                                                @RequestParam (required = false)String submittedBy,
                                                                @RequestParam List<String> documentIds) {
        DocumentReference documentReference = documentReferenceService.submitForApproval(personId, referenceType, referenceID, submittedBy, documentIds);
        return ResponseEntity.ok(documentReference);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<DocumentReference> approveDocumentReference(@PathVariable String id,
                                                                      @RequestParam String reviewedBy,
                                                                      @RequestParam(required = false) String notes) {
        DocumentReference documentReference = documentReferenceService.approveDocumentReference(id, reviewedBy, notes);
        return ResponseEntity.ok(documentReference);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<DocumentReference> rejectDocumentReference(@PathVariable String id,
                                                                     @RequestParam String reviewedBy,
                                                                     @RequestParam String rejectReason,
                                                                     @RequestParam(required = false) String notes) {
        DocumentReference documentReference = documentReferenceService.rejectDocumentReference(id, reviewedBy, rejectReason, notes);
        return ResponseEntity.ok(documentReference);
    }

    @GetMapping("/person/{personId}")
    public List<DocumentReference> findByPersonId(@PathVariable String personId) {
        return documentReferenceService.findByPersonId(personId);
    }

    @GetMapping("/reference")
    public List<DocumentReference> findByReferenceTypeAndReferenceId(@RequestParam String referenceType,
                                                                      @RequestParam String referenceID) {
        return documentReferenceService.findByReferenceTypeAndReferenceID(referenceType, referenceID);
    }

    @GetMapping("/pending")
    public List<DocumentReference> findPendingDocumentReferences() {
        return documentReferenceService.findPendingDocuments();
    }

    @GetMapping("/approved")
    public List<DocumentReference> findApprovedDocumentReferences() {
        return documentReferenceService.findApprovedDocuments();
    }

    @GetMapping("/rejected")
    public List<DocumentReference> findRejectedDocumentReferences() {
        return documentReferenceService.findRejectedDocuments();
    }

    //Dokumen yang di review oleh user tertentu
    @GetMapping("/reviewed-by/{reviewedBy}")
    public List<DocumentReference> findByReviewedBy(@PathVariable String reviewedBy) {
        return documentReferenceService.findByReviewedBy(reviewedBy);
    }

    //hapus dokumen reference
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentReference(@PathVariable String id) {
        documentReferenceService.deleteDocumentReference(id);
        return ResponseEntity.noContent().build();
    }
}
