package com.GKPS.Service;

import com.GKPS.Model.JadwalKonseling;
import com.GKPS.Repository.JadwalKonselingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JadwalKonselingService {
    private final JadwalKonselingRepository jadwalKonselingRepository;

    public JadwalKonselingService(JadwalKonselingRepository jadwalKonselingRepository) {
        this.jadwalKonselingRepository = jadwalKonselingRepository;
     }

     public List<JadwalKonseling> getAll() {
         return jadwalKonselingRepository.findAll();
     }

     public List<JadwalKonseling> getTersedia() {
         return jadwalKonselingRepository.findByTersediaTrue();
     }

     public List<JadwalKonseling> getUpComing() {
        return jadwalKonselingRepository.findByTanggalWaktuAfter(LocalDateTime.now());
     }

     public Optional<JadwalKonseling> getById(String id) {
         return jadwalKonselingRepository.findById(id);
     }

     public JadwalKonseling save(JadwalKonseling jadwalKonseling) {
        return jadwalKonselingRepository.save(jadwalKonseling);
    }

    public Optional<JadwalKonseling> update(String id, JadwalKonseling payload) {
        return jadwalKonselingRepository.findById(id)
                .map(existing -> {
                    payload.setId(existing.getId());
                    return jadwalKonselingRepository.save(payload);
                });
    }

    public boolean delete(String id) {
        if (!jadwalKonselingRepository.existsById(id)) {
            return false;
        }
        jadwalKonselingRepository.deleteById(id);
        return true;
    }
}
