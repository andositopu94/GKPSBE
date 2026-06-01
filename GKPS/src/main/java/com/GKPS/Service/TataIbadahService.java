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

    public Optional<TataIbadah> updateTataIbadah(String id, TataIbadah tataIbadah) {
    if (!tataIbadahRepository.existsById(id)) {
        return Optional.empty();
    }
        tataIbadah.setId(id);
        return Optional.of(tataIbadahRepository.save(tataIbadah));
    }

    public boolean deleteTataIbadah(String id) {
        if (!tataIbadahRepository.existsById(id)) {
            return false;
        }
        tataIbadahRepository.deleteById(id);
        return true;
    }
}
