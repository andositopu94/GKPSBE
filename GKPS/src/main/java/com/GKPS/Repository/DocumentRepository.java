package com.GKPS.Repository;

import com.GKPS.Model.Dokumentasi.Dokumen;
import com.GKPS.Model.Enum.DocumentCategory;
import com.GKPS.Model.Enum.DocumentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<Dokumen, String> {
    List<Dokumen> findByUploadedBy(String uploadedBy);
    List<Dokumen> findByDocumentType(DocumentType documentType);
    List<Dokumen> findByDocumentCategory(DocumentCategory documentCategory);
    List<Dokumen> findByTitleContainingIgnoreCase(String fileName);
    Optional<Dokumen> findByIdAndIsActiveTrue(String id);
    List<Dokumen> findByIsActiveTrue();
    List<Dokumen> findByUploadDate(LocalDateTime uploadDate);
}
