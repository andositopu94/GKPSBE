package com.GKPS.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ewarta")
public class ewarta {
    private String judul;
    private LocalDateTime tanggal;
    private String isi;

    public ewarta(String judul, LocalDateTime tanggal, String isi) {
        this.judul = judul;
        this.tanggal = tanggal;
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
