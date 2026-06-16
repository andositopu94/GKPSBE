package com.GKPS.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FamilyRequestDto {
    private String nomorKeluarga;
    private String kepalaKeluargaId;
    private String sektor;
    private List<String> anggotaKeluarga;
}
