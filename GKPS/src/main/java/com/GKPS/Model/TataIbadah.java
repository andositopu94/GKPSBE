package com.GKPS.Model;

import com.GKPS.Model.Keuangan.Attachment;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class TataIbadah {
    @Id
    private String id;
    private String namaIbadah; //minggu hari besar
    private LocalDateTime tanggal;

    private String tema;
    private String pengkhotbah; //pendeta, majelis dll
    private String pelayan; //majelis, sintua, syamas dll

    private List<IbadahItem> items;
    private List<Attachment>attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaIbadah() {
        return namaIbadah;
    }

    public void setNamaIbadah(String namaIbadah) {
        this.namaIbadah = namaIbadah;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getPengkhotbah() {
        return pengkhotbah;
    }

    public void setPengkhotbah(String pengkhotbah) {
        this.pengkhotbah = pengkhotbah;
    }

    public String getPelayan() {
        return pelayan;
    }

    public void setPelayan(String pelayan) {
        this.pelayan = pelayan;
    }

    public List<IbadahItem> getItems() {
        return items;
    }

    public void setItems(List<IbadahItem> items) {
        this.items = items;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
