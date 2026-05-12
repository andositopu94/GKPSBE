package com.GKPS.DTO;

import com.GKPS.Model.JadwalIbadah;
import com.GKPS.Model.PetugasIbadah;

public class jadwalIbadahWithPetugas {
    private JadwalIbadah jadwalIbadah;
    private PetugasIbadah petugasIbadah;

    public jadwalIbadahWithPetugas() {
    }

    public jadwalIbadahWithPetugas(JadwalIbadah jadwalIbadah, PetugasIbadah petugasIbadah) {
        this.jadwalIbadah = jadwalIbadah;
        this.petugasIbadah = petugasIbadah;
    }

    public JadwalIbadah getJadwalIbadah() {
        return jadwalIbadah;
    }

    public void setJadwalIbadah(JadwalIbadah jadwalIbadah) {
        this.jadwalIbadah = jadwalIbadah;
    }

    public PetugasIbadah getPetugasIbadah() {
        return petugasIbadah;
    }

    public void setPetugasIbadah(PetugasIbadah petugasIbadah) {
        this.petugasIbadah = petugasIbadah;
    }
}
