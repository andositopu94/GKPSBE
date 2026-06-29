package com.GKPS.DTO.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonRequestDTO {
    private String name;
    private String nik;
    private String jenisKelamin;
    private LocalDate tanggalLahir; // Format: "yyyy-MM-dd"
    private String alamat;
    private String sektor;
    private String noHp;
    private String statusPernikahan;
    private String email;
}
