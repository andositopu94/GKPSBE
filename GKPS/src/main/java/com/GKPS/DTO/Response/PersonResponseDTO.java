package com.GKPS.DTO.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonResponseDTO {
    private String id;
    private String name;
    private String nik;
    private String jenisKelamin;
    private LocalDate tanggalLahir;
    private String sektor;
    private String noHp;
    private String alamat;
    private String statusPernikahan;
    private String email;
    private Boolean active;
}
