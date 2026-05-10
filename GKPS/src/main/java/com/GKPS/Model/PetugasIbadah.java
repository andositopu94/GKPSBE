package com.GKPS.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "petugasIbadah")
public class PetugasIbadah {
    @Id
    private String id;

    @DBRef
    private jadwalIbadah jadwalIbadah;
    private String pengkhotbah;
    private String pemimpinPujian;
    private String pemusik;
    private String penyanyi;
    private String pembacaAlkitab;
    private String pelayanVotum;
    private String pembawaDoa;
    private String penerimaKolekte;
    private String multimedia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public com.GKPS.Model.jadwalIbadah getJadwalIbadah() {
        return jadwalIbadah;
    }

    public void setJadwalIbadah(com.GKPS.Model.jadwalIbadah jadwalIbadah) {
        this.jadwalIbadah = jadwalIbadah;
    }

    public String getPengkhotbah() {
        return pengkhotbah;
    }

    public void setPengkhotbah(String pengkhotbah) {
        this.pengkhotbah = pengkhotbah;
    }

    public String getPemimpinPujian() {
        return pemimpinPujian;
    }

    public void setPemimpinPujian(String pemimpinPujian) {
        this.pemimpinPujian = pemimpinPujian;
    }

    public String getPemusik() {
        return pemusik;
    }

    public void setPemusik(String pemusik) {
        this.pemusik = pemusik;
    }

    public String getPenyanyi() {
        return penyanyi;
    }

    public void setPenyanyi(String penyanyi) {
        this.penyanyi = penyanyi;
    }

    public String getPembacaAlkitab() {
        return pembacaAlkitab;
    }

    public void setPembacaAlkitab(String pembacaAlkitab) {
        this.pembacaAlkitab = pembacaAlkitab;
    }

    public String getPelayanVotum() {
        return pelayanVotum;
    }

    public void setPelayanVotum(String pelayanVotum) {
        this.pelayanVotum = pelayanVotum;
    }

    public String getPembawaDoa() {
        return pembawaDoa;
    }

    public void setPembawaDoa(String pembawaDoa) {
        this.pembawaDoa = pembawaDoa;
    }

    public String getPenerimaKolekte() {
        return penerimaKolekte;
    }

    public void setPenerimaKolekte(String penerimaKolekte) {
        this.penerimaKolekte = penerimaKolekte;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }
}
