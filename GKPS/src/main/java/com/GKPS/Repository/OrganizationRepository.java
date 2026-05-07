package com.GKPS.Repository;

import com.GKPS.Model.Organisasi.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization>findByIsActiveTrue();
}
