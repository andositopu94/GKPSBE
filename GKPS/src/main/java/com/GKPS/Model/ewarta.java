package com.GKPS.Model;

import com.GKPS.Model.Enum.EwartaCategory;
import com.GKPS.Model.Keuangan.Attachment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ewarta")
public class ewarta {
    @Id
    private String id;
    private String judul;
    private String isi;

    private EwartaCategory category;

    private LocalDateTime tanggalPublikasi;
    private LocalDateTime tanggalAcara;
    private LocalDateTime expireDate;

    private Boolean isPublished=false;

    private List<Attachment> attachments;
    private String createdBy;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public EwartaCategory getCategory() {
        return category;
    }

    public void setCategory(EwartaCategory category) {
        this.category = category;
    }

    public LocalDateTime getTanggalPublikasi() {
        return tanggalPublikasi;
    }

    public void setTanggalPublikasi(LocalDateTime tanggalPublikasi) {
        this.tanggalPublikasi = tanggalPublikasi;
    }

    public LocalDateTime getTanggalAcara() {
        return tanggalAcara;
    }

    public void setTanggalAcara(LocalDateTime tanggalAcara) {
        this.tanggalAcara = tanggalAcara;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
