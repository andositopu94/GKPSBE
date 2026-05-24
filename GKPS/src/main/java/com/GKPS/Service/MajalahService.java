package com.GKPS.Service;

import com.GKPS.Model.MajalahRohani;
import com.GKPS.Repository.MajalahRohaniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajalahService {
    @Autowired
    private MajalahRohaniRepository majalahRohaniRepository;

    public List<MajalahRohani> getAllMajalah() {
        return majalahRohaniRepository.findAll();
    }

    public List<MajalahRohani> getApprovedMajalahRohani() {
        return majalahRohaniRepository.findByIsApprovedTrue();
    }

    public Optional<MajalahRohani> getMajalahRohaniById(String id) {
        return majalahRohaniRepository.findById(id);
    }

    public MajalahRohani createMajalahRohani(MajalahRohani majalahRohani) {
        return majalahRohaniRepository.save(majalahRohani);
    }

    public MajalahRohani updateMajalahRohani(String id, MajalahRohani majalahRohani) {
        majalahRohani.setId(id);
        return majalahRohaniRepository.save(majalahRohani);
    }

    public void deleteMajalahRohani(String id) {
        majalahRohaniRepository.deleteById(id);
    }



}
