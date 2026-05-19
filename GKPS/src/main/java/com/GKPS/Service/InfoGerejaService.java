package com.GKPS.Service;

import com.GKPS.Model.InfoGereja;
import com.GKPS.Repository.InfoGerejaRepository;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

public class InfoGerejaService {
    private final InfoGerejaRepository infoGerejaRepository;

    public InfoGerejaService(InfoGerejaRepository infoGerejaRepository) {
        this.infoGerejaRepository = infoGerejaRepository;
    }
    public List<InfoGereja> getAll(){
        return infoGerejaRepository.findAll();
    }

    public Optional<InfoGereja> getById(String id){
        return infoGerejaRepository.findById(id);
    }

    public InfoGereja save(InfoGereja infoGereja){
        return infoGerejaRepository.save(infoGereja);
    }

    public Optional<InfoGereja> update(String id, InfoGereja payload){
        return infoGerejaRepository.findById(id).map(existingInfoGereja -> {
            payload.setId(existingInfoGereja.getId());
//            existingInfoGereja.setNamaGereja(payload.getNamaGereja());
//            existingInfoGereja.setAlamat(payload.getAlamat());
//            existingInfoGereja.setKelurahan(payload.getKelurahan());
//            existingInfoGereja.setKecamatan(payload.getKecamatan());
//            existingInfoGereja.setKota(payload.getKota());
//            existingInfoGereja.setProvinsi(payload.getProvinsi());
//            existingInfoGereja.setKodePos(payload.getKodePos());
//            existingInfoGereja.setNoTelepon(payload.getNoTelepon());
//            existingInfoGereja.setEmail(payload.getEmail());
//            existingInfoGereja.setWebsite(payload.getWebsite());
//            existingInfoGereja.setNamaPendeta(payload.getNamaPendeta());
//            existingInfoGereja.setNoHpPendeta(payload.getNoHpPendeta());
//            existingInfoGereja.setJamOperasional(payload.getJamOperasional());
//            existingInfoGereja.setDeskripsi(payload.getDeskripsi());
//            existingInfoGereja.setLatitude(payload.getLatitude());
//            existingInfoGereja.setLongitude(payload.getLongitude());
//            existingInfoGereja.setLogoUrl(payload.getLogoUrl());
//            existingInfoGereja.setFotoGereja(payload.getFotoGereja());

//            return infoGerejaRepository.save(existingInfoGereja);
            return infoGerejaRepository.save(payload);
        });
    }

    public boolean delete(String id){
        if (!infoGerejaRepository.existsById(id)){
            return false;
        }
        infoGerejaRepository.deleteById(id);
        return true;
    }
}
