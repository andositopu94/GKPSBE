package com.GKPS.Repository;

import com.GKPS.Model.InfoGereja;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoGerejaRepository extends MongoRepository<InfoGereja, String> {

}
