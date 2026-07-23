package com.GKPS.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class KeuanganSummaryDto {
    private LocalDate tanggal;
    private List<ItemKeuanganDto> items;
    private BigDecimal totalPemasukan;
    private BigDecimal totalPengeluaran;

    public KeuanganSummaryDto() {
    }

    public KeuanganSummaryDto(LocalDate tanggal, List<ItemKeuanganDto> items, BigDecimal totalPemasukan,  BigDecimal totalPengeluaran) {
        this.tanggal = tanggal;
        this.items = items;
        this.totalPemasukan = totalPemasukan;
        this.totalPengeluaran = totalPengeluaran;
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

    public BigDecimal getTotalPemasukan() {
        return totalPemasukan;
    }

    public void setTotalPemasukan(BigDecimal totalPemasukan) {
        this.totalPemasukan = totalPemasukan;
    }

    public BigDecimal getTotalPengeluaran() {
        return totalPengeluaran;
    }
    public void setTotalPengeluaran(BigDecimal totalPengeluaran) {
        this.totalPengeluaran = totalPengeluaran;
    }
}
