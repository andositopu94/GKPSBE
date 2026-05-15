package com.GKPS.Repository;

import com.GKPS.Model.JadwalKonseling;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JadwalKonselingRepository extends MongoRepository<JadwalKonseling, String> {
    List<JadwalKonseling> findByTanggalAfter(LocalDateTime tanggalWaktu);
    List<JadwalKonseling> findByTersediaTrue();

    List<JadwalKonseling> findByTanggalWaktuAfter(LocalDateTime tanggalWaktu);
}
