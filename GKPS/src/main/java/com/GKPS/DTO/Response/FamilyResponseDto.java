package com.GKPS.DTO.Response;

import lombok.Data;

@Data
public class FamilyResponseDto {
    private String id;
    private String nomorKeluarga;
    private String kepalaKeluargaId;
    private String namaKepalaKeluarga;
    private String sektor;
    private Integer jumlahAnggota;
}
