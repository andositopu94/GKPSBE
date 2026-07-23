package com.GKPS.Model.Keuangan;

import com.GKPS.Model.Enum.TransactionCategory;
import com.GKPS.Model.Enum.TransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "transactions")
public class Transaction extends BaseEntity{
    @Id
    @Indexed
    private String id;

    private TransactionType type;

    private TransactionCategory kategori; //donasi, operasional, dll
    private BigDecimal amount;

    private String accountId; //uangMasuk dan uangKeluar
    private String fromAccountId; //hanya untuk transfer
    private String toAccountId; //hanya untuk transfer

    private String deskripsi;

    private String referenceNo;
    private Approval approvalStatus;
    private List<Attachment> attachments;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public TransactionCategory getKategori() {
        return kategori;
    }

    public void setKategori(TransactionCategory kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
    public Approval getApprovalStatus() {
        return approvalStatus;
    }
    public void setApprovalStatus(Approval approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
