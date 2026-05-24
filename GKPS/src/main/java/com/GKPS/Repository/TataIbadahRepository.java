package com.GKPS.Repository;

import com.GKPS.Model.TataIbadah;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TataIbadahRepository extends MongoRepository<TataIbadah, String> {
    List<TataIbadah> findByTanggalAfter(LocalDateTime date);
    List<TataIbadah> findByNamaIbadahContaining(String namaIbadah);
}
