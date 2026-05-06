package com.GKPS.Model.Keuangan;

import com.GKPS.Model.Enum.transactionCategory;
import com.GKPS.Model.Enum.transactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "transactions")
public class transaction extends BaseEntity{
    @Id
    private String id;

    private transactionType type;

    private transactionCategory kategori; //donasi, operasional, dll
    private Double amount;

    private String accountId; //uangMasuk dan uangKeluar
    private String fromAccountId; //hanya untuk transfer
    private String toAccountId; //hanya untuk transfer

    private String deskripsi;

    private String referenceNo;
    private Approval approvalStatus;
    private List<Attachment> attachments;

    public transaction() {
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

    public transactionType getType() {
        return type;
    }

    public void setType(transactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public transactionCategory getKategori() {
        return kategori;
    }

    public void setKategori(transactionCategory kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
