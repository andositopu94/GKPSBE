package com.GKPS.Service;

import com.GKPS.Model.Renungan;
import com.GKPS.Repository.RenunganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RenunganService {

    @Autowired
    private RenunganRepository renunganRepository;

    public List<Renungan> getAllRenungan() {
        return renunganRepository.findAll();
    }

    public Optional<Renungan> getRenunganById(String id) {
        return renunganRepository.findById(id);
    }

    public List<Renungan> getRenunganByDateAfter(LocalDate date){
        return renunganRepository.findByTanggalAfter(date);
    }

    public Renungan createRenungan(Renungan renungan) {
        return renunganRepository.save(renungan);
    }

    public Renungan updateRenungan(String id, Renungan renungan) {
        renungan.setId(id);
        return renunganRepository.save(renungan);
    }

    public void deleteRenungan(String id) {
        renunganRepository.deleteById(id);
    }
}
