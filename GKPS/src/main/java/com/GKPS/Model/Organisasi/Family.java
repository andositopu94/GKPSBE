package com.GKPS.Model.Organisasi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "family")
public class Family {
    @Id
    private String id;
    private String nomorKeluarga;
    private String namaKepalaKeluarga;
    private String sektor;
    private List<String> anggotaKeluarga; // List of Person IDs

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomorKeluarga() {
        return nomorKeluarga;
    }

    public void setNomorKeluarga(String nomorKeluarga) {
        this.nomorKeluarga = nomorKeluarga;
    }

    public String getNamaKepalaKeluarga() {
        return namaKepalaKeluarga;
    }

    public void setNamaKepalaKeluarga(String namaKepalaKeluarga) {
        this.namaKepalaKeluarga = namaKepalaKeluarga;
    }

    public String getSektor() {
        return sektor;
    }

    public void setSektor(String sektor) {
        this.sektor = sektor;
    }

    public List<String> getAnggotaKeluarga() {
        return anggotaKeluarga;
    }

    public void setAnggotaKeluarga(List<String> anggotaKeluarga) {
        this.anggotaKeluarga = anggotaKeluarga;
    }
}
