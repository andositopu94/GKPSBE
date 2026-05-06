package com.GKPS.Model;

import com.GKPS.Model.Enum.IbadahItemType;

public class IbadahItem {
    private Integer urutan;
    private IbadahItemType type;
    private String judul;   //pujian, firman, dll
    private String isi;     //isi pujian, firman, ayat, doa dll
    private String keterangan; //keterangan tambahan, misal nama penyanyi, nama pembaca firman dll

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public IbadahItemType getType() {
        return type;
    }

    public void setType(IbadahItemType type) {
        this.type = type;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
