package com.GKPS.Repository;

import com.GKPS.Model.Renungan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RenunganRepository extends MongoRepository<Renungan, String> {
    List<Renungan> findByTanggalAfter(LocalDate date);
    List<Renungan> findByPenulisContaining(String penulis);
}
