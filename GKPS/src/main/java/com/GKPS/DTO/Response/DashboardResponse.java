package com.GKPS.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalJemaat;
    private Long totalKeluarga;
    private Long totalPengurus;
    private Map<String, Long> statistikPerSektor;
    private Map<String, Long> statistikPerOrganisasi;
    private Integer totalKegiatanBulanIni;

    private BigDecimal totalPemasukan;
    private BigDecimal totalPengeluaran;
    private BigDecimal saldoKas;
    private List<Map<String, Object>> pemasukanPerKategori;
    private List<Map<String, Object>> trendBulanan;

    private String lastUpdate;
}
