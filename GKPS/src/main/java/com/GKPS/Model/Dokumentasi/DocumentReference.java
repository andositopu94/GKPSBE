package com.GKPS.Model.Dokumentasi;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "document_references")
public class DocumentReference {
    @Id
    private String id;
    private String documentId; //Reference ke Dok
    private String referenceType; //Jenis referensi (misalnya: "Baptism", "Sidi", "Marriage", dll.)
    private String referenceId; //ID dari dokumen yang direferensikan (misalnya: ID Baptism, ID Sidi, dll.)
    private String personId; //ID orang yang terkait dengan referensi dokumen
    private String submittedBy; //ID orang yang mengajukan referensi dokumen
    private String status; //Status referensi dokumen (misalnya: "PENDING", "APPROVED", "REJECTED")
    private String reviewedBy; //ID orang yang meninjau referensi dokumen

    @CreatedDate
    private LocalDateTime submitDate; //Tanggal dan waktu ketika referensi dokumen diajukan
    private LocalDateTime reviewDate; //Tanggal dan waktu ketika referensi dokumen ditinjau
    private String rejectReason; //Alasan penolakan jika referensi dokumen ditolak
    private List<String> documentIds; //Daftar ID dokumen yang terkait dengan referensi dokumen
    private Boolean isActive=true; //Status aktif referensi dokumen
    private String notes; //Catatan tambahan tentang referensi dokumen

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
