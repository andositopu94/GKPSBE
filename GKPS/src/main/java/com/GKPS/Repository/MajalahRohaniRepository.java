package com.GKPS.Repository;

import com.GKPS.Model.MajalahRohani;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MajalahRohaniRepository extends MongoRepository<MajalahRohani, String> {
    List<MajalahRohani> findByIsApprovedTrue();
    List<MajalahRohani> findByTanggalAfter(LocalDate date);
    List<MajalahRohani> findByNamaPenulisContaining(String namaPenulis);
}
