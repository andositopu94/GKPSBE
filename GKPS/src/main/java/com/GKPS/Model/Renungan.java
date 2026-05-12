package com.GKPS.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "renungan")
public class Renungan {
    @Id
    private String id;
    private String judul;
    private LocalDate tanggal;
    private String isi;
    private String penulis;
    private String link;

    public Renungan(String judul, LocalDate tanggal, String isi, String penulis, String link) {
        this.judul = judul;
        this.tanggal = tanggal;
        this.isi = isi;
        this.penulis = penulis;
        this.link = link;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
