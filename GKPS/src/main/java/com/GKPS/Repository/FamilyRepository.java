package com.GKPS.Repository;

import com.GKPS.Model.Organisasi.Family;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends MongoRepository<Family,String> {
    List<Family> findBySektor(String sektor);
    List<Family> findByKepalaKeluargaId(String kepalaKeluargaId);
    Optional<Family> findByNomorKeluarga(String nomorKeluarga);
    List<Family> findByActiveTrue();
}
