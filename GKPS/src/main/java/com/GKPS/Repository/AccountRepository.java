package com.GKPS.Repository;

import com.GKPS.Model.Keuangan.account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<account, String> {
    Optional<account>findByNama(String nama);
    List<account> findByIsDeletedFalse();
    boolean existsByNama(String nama);
}
