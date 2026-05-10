package com.GKPS.Model;

import com.GKPS.Model.Enum.RoleType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "jadwalKonseling")
public class JadwalKonseling {
    @Id
    private String id;

    private String namaKonselor;
    private RoleType roleKonselor;
    private LocalDateTime tanggalWaktu;
    private String lokasi;
    private String tipeKonseling; // Individu, pra-nikah, Online, keluarga
    private String deskripsi;
    private String catatanKonselor;
    private Boolean tersedia;
    private Integer kuotaTerisi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaKonselor() {
        return namaKonselor;
    }

    public void setNamaKonselor(String namaKonselor) {
        this.namaKonselor = namaKonselor;
    }

    public RoleType getRoleKonselor() {
        return roleKonselor;
    }

    public void setRoleKonselor(RoleType roleKonselor) {
        this.roleKonselor = roleKonselor;
    }

    public LocalDateTime getTanggalWaktu() {
        return tanggalWaktu;
    }

    public void setTanggalWaktu(LocalDateTime tanggalWaktu) {
        this.tanggalWaktu = tanggalWaktu;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getCatatanKonselor() {
        return catatanKonselor;
    }

    public void setCatatanKonselor(String catatanKonselor) {
        this.catatanKonselor = catatanKonselor;
    }

    public Boolean getTersedia() {
        return tersedia;
    }

    public void setTersedia(Boolean tersedia) {
        this.tersedia = tersedia;
    }

    public Integer getKuotaTerisi() {
        return kuotaTerisi;
    }

    public void setKuotaTerisi(Integer kuotaTerisi) {
        this.kuotaTerisi = kuotaTerisi;
    }
}
