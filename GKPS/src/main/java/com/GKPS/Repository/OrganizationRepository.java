package com.GKPS.Repository;

import com.GKPS.Model.Organisasi.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByIsActiveTrue();
    List<Organization> findByEndDateAfter(LocalDate date);
}
