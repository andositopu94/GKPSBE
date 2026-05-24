package com.GKPS.Service;

import com.GKPS.Model.TataIbadah;
import com.GKPS.Repository.TataIbadahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TataIbadahService {
    @Autowired
    private TataIbadahRepository tataIbadahRepository;

    public List<TataIbadah> getAllTataIbadah() {
        return tataIbadahRepository.findAll();
    }

    public Optional<TataIbadah> getTataIbadahById(String id) {
        return tataIbadahRepository.findById(id);
    }

    public List<TataIbadah> getUpcomingTataIbadah() {
        return tataIbadahRepository.findByTanggalAfter(LocalDateTime.now());
    }

    public TataIbadah createTataIbadah(TataIbadah tataIbadah) {
        return tataIbadahRepository.save(tataIbadah);
    }

    public TataIbadah updateTataIbadah(String id, TataIbadah tataIbadah) {
//        Optional<TataIbadah> existingTataIbadah = tataIbadahRepository.findById(id);
//        if (existingTataIbadah.isPresent()) {
//            TataIbadah updatedTataIbadah = existingTataIbadah.get();
//            updatedTataIbadah.setNamaIbadah(tataIbadah.getNamaIbadah());
//            updatedTataIbadah.setTanggal(tataIbadah.getTanggal());
//            updatedTataIbadah.setDeskripsi(tataIbadah.getDeskripsi());
//            return tataIbadahRepository.save(updatedTataIbadah);
//        } else {
//            throw new RuntimeException("Tata Ibadah not found with id: " + id);
//        }
        tataIbadah.setId(id);
        return tataIbadahRepository.save(tataIbadah);
    }

    public void deleteTataIbadah(String id) {
        tataIbadahRepository.deleteById(id);
    }
}
