package com.GKPS.DTO.Response;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardResponse {
    private Long totalJemaat;
    private Long totalPengurus;
    private Long totalKeluarga;
    private Map<String, Long> statistikPerSektor;
    private Map<String, Long> statistikPerOrganisasi;
    private Integer totalKegiatanBulanIni;
}
