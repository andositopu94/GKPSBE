package com.GKPS.Repository;

import com.GKPS.Model.JadwalIbadah;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JadwalIbadahRepository extends MongoRepository<JadwalIbadah, String> {
    List<JadwalIbadah> findAll();
}
