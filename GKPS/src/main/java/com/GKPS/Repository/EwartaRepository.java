package com.GKPS.Repository;

import com.GKPS.Model.Enum.EwartaCategory;
import com.GKPS.Model.ewarta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EwartaRepository extends MongoRepository<ewarta, String>{
    List<ewarta>findByIsPublishedTrue();
    List<ewarta>findByKategori(EwartaCategory kategori);
}
