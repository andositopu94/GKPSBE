package com.GKPS.DTO;

public class ItemKeuanganDto {
    private String jenis;
    private Double amount;
    private String deskripsi;

    public ItemKeuanganDto() {
    }

    public ItemKeuanganDto(String jenis, Double amount, String deskripsi) {
        this.jenis = jenis;
        this.amount = amount;
        this.deskripsi = deskripsi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
