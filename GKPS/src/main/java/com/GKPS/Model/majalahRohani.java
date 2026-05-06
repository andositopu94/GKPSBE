package com.GKPS.Model;

import jakarta.annotation.Generated;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class majalahRohani {
    @Id
    private String id;
    private String judul;
    private LocalDate tanggal;
    private String link;
    private String gambar;
    private String isi;
    private String penulis;
    private String kategori;

    public majalahRohani(String id, String judul, LocalDate tanggal, String link, String gambar, String isi, String penulis, String kategori) {
        this.id = id;
        this.judul = judul;
        this.tanggal = tanggal;
        this.link = link;
        this.gambar = gambar;
        this.isi = isi;
        this.penulis = penulis;
        this.kategori = kategori;
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

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
