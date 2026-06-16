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
