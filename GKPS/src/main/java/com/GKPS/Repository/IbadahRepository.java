package com.GKPS.Repository;

import com.GKPS.Model.TataIbadah;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IbadahRepository extends MongoRepository<TataIbadah, String>{

}
