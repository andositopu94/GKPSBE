package com.GKPS.Model.Dokumentasi;

import com.GKPS.Model.Enum.ApprovalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "baptism")
public class Baptism {
    @Id
    private String id;
    private String personId; // ID of the person who got baptized
    private LocalDate baptismDate;
    private String pendeta; // Name of the pastor who performed the baptism
    private String tempat; // Place of baptism
    private List<String> documentReferenceIds; // List of document IDs related to baptism
    private ApprovalStatus approvalStatus; // Status of the baptism approval (e.g., PENDING, APPROVED, REJECTED)
    private String approveBy; // ID of the person who approved the baptism
    private LocalDate approveDate; // Date when the baptism was approved
    private String rejectReason; // Reason for rejection if the baptism was rejected
    private String notes; // Additional notes or comments about the baptism

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

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public void setBaptismDate(LocalDate baptismDate) {
        this.baptismDate = baptismDate;
    }

    public String getPendeta() {
        return pendeta;
    }

    public void setPendeta(String pendeta) {
        this.pendeta = pendeta;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
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
