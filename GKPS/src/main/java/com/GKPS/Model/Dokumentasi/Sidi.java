package com.GKPS.Model.Dokumentasi;

import com.GKPS.Model.Enum.ApprovalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "sidi")
public class Sidi {
    @Id
    private String id;
    private String personId; // ID of the Person who received Sidi
    private LocalDate sidiDate; // Date of Sidi
    private String pendeta; // Name of the pastor who performed Sidi
    private List<String> documentReferenceIds; // List of document IDs related to Sidi
    private ApprovalStatus approvalStatus; // Status of the Sidi approval (e.g., PENDING, APPROVED, REJECTED)
    private String approveBy; // ID of the person who approved the Sidi
    private LocalDate approveDate; // Date when the Sidi was approved
    private String rejectReason; // Reason for rejection if the Sidi was rejected
    private String notes; // Additional notes or comments about the Sidi

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public LocalDate getSidiDate() {
        return sidiDate;
    }

    public void setSidiDate(LocalDate sidiDate) {
        this.sidiDate = sidiDate;
    }

    public String getPendeta() {
        return pendeta;
    }

    public void setPendeta(String pendeta) {
        this.pendeta = pendeta;
    }

    public List<String> getDocumentReferenceIds() {
        return documentReferenceIds;
    }

    public void setDocumentReferenceIds(List<String> documentReferenceIds) {
        this.documentReferenceIds = documentReferenceIds;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
