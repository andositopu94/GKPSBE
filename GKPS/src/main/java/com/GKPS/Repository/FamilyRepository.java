package com.GKPS.Repository;

import com.GKPS.Model.Organisasi.Family;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FamilyRepository extends MongoRepository<Family,String> {
    List<Family> findBySektor(String sektor);
    List<Family> findByNamaKepalaKeluarga(String namaKepalaKeluarga);
}
