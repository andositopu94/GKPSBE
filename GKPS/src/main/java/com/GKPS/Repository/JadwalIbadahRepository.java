package com.GKPS.Repository;

import com.GKPS.Model.jadwalIbadah;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JadwalIbadahRepository extends MongoRepository<jadwalIbadah, String> {
    List<jadwalIbadah> findAll();
}
