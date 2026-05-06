package com.GKPS.Model.Keuangan;

import com.GKPS.Model.Enum.ApprovalStatus;

import java.time.LocalDateTime;

public class Approval {
    private ApprovalStatus status = ApprovalStatus.PENDING;

    private String approveBy;
    private LocalDateTime approveAt;

    private String deskripsi;

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public LocalDateTime getApproveAt() {
        return approveAt;
    }

    public void setApproveAt(LocalDateTime approveAt) {
        this.approveAt = approveAt;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
