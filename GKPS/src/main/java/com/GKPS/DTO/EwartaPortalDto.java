package com.GKPS.DTO;

import com.GKPS.Model.InfoGereja;
import com.GKPS.Model.Ewarta;
import com.GKPS.Model.JadwalIbadah;
import com.GKPS.Model.JadwalKonseling;

import java.util.List;

public class EwartaPortalDto {
    private InfoGereja infoGereja;
    private List<Ewarta> pengumumanList;
    private List<JadwalIbadah> jadwalIbadahList;
    private List<JadwalKonseling> jadwalKonselingList;
    private List<jadwalIbadahWithPetugas> jadwalIbadahWithPetugasList;

public EwartaPortalDto() {
    }

    public EwartaPortalDto(InfoGereja infoGereja, List<Ewarta> pengumumanList, List<JadwalKonseling> jadwalKonselingList,List<JadwalIbadah> jadwalIbadahList, List<jadwalIbadahWithPetugas> jadwalIbadahWithPetugasList) {
        this.infoGereja = infoGereja;
        this.pengumumanList = pengumumanList;
        this.jadwalKonselingList = jadwalKonselingList;
        this.jadwalIbadahList = jadwalIbadahList;
        this.jadwalIbadahWithPetugasList = jadwalIbadahWithPetugasList;
    }

    public InfoGereja getInfoGereja() {
        return infoGereja;
    }

    public void setInfoGereja(InfoGereja infoGereja) {
        this.infoGereja = infoGereja;
    }

    public List<Ewarta> getPengumumanList() {
        return pengumumanList;
    }

    public void setPengumumanList(List<Ewarta> pengumumanList) {
        this.pengumumanList = pengumumanList;
    }

    public List<JadwalKonseling> getJadwalKonselingList() {
        return jadwalKonselingList;
    }

    public void setJadwalKonselingList(List<JadwalKonseling> jadwalKonselingList) {
        this.jadwalKonselingList = jadwalKonselingList;
    }

    public List<JadwalIbadah> getJadwalIbadahList() {
        return jadwalIbadahList;
    }

    public void setJadwalIbadahList(List<JadwalIbadah> jadwalIbadahList) {
        this.jadwalIbadahList = jadwalIbadahList;
    }

    public List<jadwalIbadahWithPetugas> getJadwalIbadahWithPetugasList() {
        return jadwalIbadahWithPetugasList;
    }

    public void setJadwalIbadahWithPetugasList(List<jadwalIbadahWithPetugas> jadwalIbadahWithPetugasList) {
        this.jadwalIbadahWithPetugasList = jadwalIbadahWithPetugasList;
    }
}
