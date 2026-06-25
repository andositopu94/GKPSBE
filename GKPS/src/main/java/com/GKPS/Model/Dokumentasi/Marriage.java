package com.GKPS.Model.Dokumentasi;

import com.GKPS.Model.Enum.ApprovalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "marriage")
public class Marriage {
    @Id
    private String id;
    private String husbandId; // ID of the husband
    private String wifeId; // ID of the wife
    private String marriageDate; // Date of the marriage
    private String tempatAcaraNikah; // Place of the marriage
    private String pendeta; // Name of the pastor who performed the marriage
    private String lokasiGereja; // Location of the church where the marriage took place

    private List<String> documentReferenceIds; // List of document IDs related to the marriage
    private ApprovalStatus approvalStatus; // Status of the marriage approval (e.g., PENDING, APPROVED, REJECTED)
    private String approveBy; // ID of the person who approved the marriage
    private LocalDate approveDate; // Date when the marriage was approved
    private String rejectReason; // Reason for rejection if the marriage was rejected
    private String notes; // Additional notes or comments about the marriage

    private String saksi1;
    private String saksi2;
    private Boolean previousMarriageHusband; // Indicates if either party has been married before
    private Boolean previousMarriageWife;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(String husbandId) {
        this.husbandId = husbandId;
    }

    public String getWifeId() {
        return wifeId;
    }

    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getTempatAcaraNikah() {
        return tempatAcaraNikah;
    }

    public void setTempatAcaraNikah(String tempatAcaraNikah) {
        this.tempatAcaraNikah = tempatAcaraNikah;
    }

    public String getPendeta() {
        return pendeta;
    }

    public void setPendeta(String pendeta) {
        this.pendeta = pendeta;
    }

    public String getLokasiGereja() {
        return lokasiGereja;
    }

    public void setLokasiGereja(String lokasiGereja) {
        this.lokasiGereja = lokasiGereja;
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

    public String getSaksi1() {
        return saksi1;
    }

    public void setSaksi1(String saksi1) {
        this.saksi1 = saksi1;
    }

    public String getSaksi2() {
        return saksi2;
    }

    public void setSaksi2(String saksi2) {
        this.saksi2 = saksi2;
    }

    public Boolean getPreviousMarriageHusband() {
        return previousMarriageHusband;
    }

    public void setPreviousMarriageHusband(Boolean previousMarriageHusband) {
        this.previousMarriageHusband = previousMarriageHusband;
    }

    public Boolean getPreviousMarriageWife() {
        return previousMarriageWife;
    }

    public void setPreviousMarriageWife(Boolean previousMarriageWife) {
        this.previousMarriageWife = previousMarriageWife;
    }
}
