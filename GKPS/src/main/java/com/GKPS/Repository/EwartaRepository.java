package com.GKPS.Repository;

import com.GKPS.Model.Enum.EwartaCategory;
import com.GKPS.Model.Ewarta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EwartaRepository extends MongoRepository<Ewarta, String>{
    List<Ewarta>findByIsPublishedTrue();
    List<Ewarta>findByCategory(EwartaCategory category);
    List<Ewarta>findByIsPublishedTrueAndExpiredDateAfter(LocalDateTime expiredDate);
}
