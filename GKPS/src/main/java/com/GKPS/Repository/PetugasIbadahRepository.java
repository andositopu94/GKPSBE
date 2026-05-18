package com.GKPS.Repository;

import com.GKPS.Model.PetugasIbadah;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetugasIbadahRepository extends MongoRepository<PetugasIbadah, String> {
//    List<PetugasIbadah> findByTanggalAfter(String tanggal);
    PetugasIbadah findByJadwalIbadahId(String jadwalIbadahId);

}
