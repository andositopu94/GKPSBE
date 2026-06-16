package com.GKPS.Repository;

import com.GKPS.Model.Organisasi.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByName(String name);
    List<Person> findAllByOrderByNameAsc();
    Optional<Person> findByNik(String nik);
    List<Person> findBySektor(String sektor);
    List<Person> findByActiveTrue();
}
