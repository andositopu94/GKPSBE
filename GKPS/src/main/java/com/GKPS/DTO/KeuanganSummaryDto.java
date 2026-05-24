package com.GKPS.DTO;

import java.time.LocalDate;
import java.util.List;

public class KeuanganSummaryDto {
    private LocalDate tanggal;
    private List<ItemKeuanganDto> items;
    private Double totalPemasukan;

    public KeuanganSummaryDto() {
    }

    public KeuanganSummaryDto(LocalDate tanggal, List<ItemKeuanganDto> items, Double totalPemasukan) {
        this.tanggal = tanggal;
        this.items = items;
        this.totalPemasukan = totalPemasukan;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public List<ItemKeuanganDto> getItems() {
        return items;
    }

    public void setItems(List<ItemKeuanganDto> items) {
        this.items = items;
    }

    public Double getTotalPemasukan() {
        return totalPemasukan;
    }

    public void setTotalPemasukan(Double totalPemasukan) {
        this.totalPemasukan = totalPemasukan;
    }
}
