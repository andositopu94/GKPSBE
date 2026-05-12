package com.GKPS.Model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class MajalahRohani {
    @Id
    private String id;
    private String judul;
    private LocalDate tanggal;
    private String link;
    private String gambar;
    private String isi;
    private String namaPenulis;
    private boolean isApproved; // Menandakan apakah majalah rohani sudah disetujui atau belum

    public MajalahRohani(String id, String judul, LocalDate tanggal, String link, String gambar, String isi, String namaPenulis, boolean isApproved) {
        this.id = id;
        this.judul = judul;
        this.tanggal = tanggal;
        this.link = link;
        this.gambar = gambar;
        this.isi = isi;
        this.namaPenulis = namaPenulis;
        this.isApproved = isApproved;
    }

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

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getNamaPenulis() {
        return namaPenulis;
    }

    public void setNamaPenulis(String namaPenulis) {
        this.namaPenulis = namaPenulis;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
