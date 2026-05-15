package com.GKPS.Service;

import com.GKPS.DTO.EwartaPortalDto;
import com.GKPS.DTO.jadwalIbadahWithPetugas;
import com.GKPS.Model.*;
import com.GKPS.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EwartaService {

    @Autowired
    private InfoGerejaRepository infoGerejaRepository;

    @Autowired
    private EwartaRepository EwartaRepository;

    @Autowired
    private JadwalKonselingRepository JadwalKonselingRepository;

    @Autowired
    private JadwalIbadahRepository JadwalIbadahRepository;

    @Autowired
    private PetugasIbadahRepository PetugasIbadahRepository;

    public EwartaPortalDto getEwartaPortalDto() {
        EwartaPortalDto dto = new EwartaPortalDto();

        //ambil info gereje
        List<InfoGereja>infoGerejaList= infoGerejaRepository.findAll();
        InfoGereja infoGereja = infoGerejaList.isEmpty() ? null : infoGerejaList.get(0);
        dto.setInfoGereja(infoGereja);

        //ambil ewarta yang belum expired
        List<Ewarta> pengumumanList = EwartaRepository.findByIsPublishedTrueAndExpiredDateAfter(LocalDateTime.now());
        dto.setPengumumanList(pengumumanList);

        //ambil jadwal konseling yang tersedia
        List<JadwalKonseling> jadwalKonselingList = JadwalKonselingRepository.findByTanggalWaktuAfter(LocalDateTime.now());
        dto.setJadwalKonselingList(jadwalKonselingList);

        List<jadwalIbadahWithPetugas> jadwalIbadahWithPetugasList = new ArrayList<>();
        List<JadwalIbadah> allJadwalIbadah = JadwalIbadahRepository.findAll();

        for (JadwalIbadah jadwalIbadah : allJadwalIbadah) {
            PetugasIbadah petugas = PetugasIbadahRepository.findByJadwalIbadahId(jadwalIbadah.getId());
            jadwalIbadahWithPetugas jadwalIbadahWithPetugas = new jadwalIbadahWithPetugas(jadwalIbadah, petugas);
            jadwalIbadahWithPetugasList.add(jadwalIbadahWithPetugas);
        }
        dto.setJadwalIbadahWithPetugasList(jadwalIbadahWithPetugasList);

        return dto;
    }

}
