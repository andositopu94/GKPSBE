package com.GKPS.Service;

import com.GKPS.Model.Dokumentasi.Dokumen;
import com.GKPS.Model.Enum.DocumentCategory;
import com.GKPS.Model.Enum.DocumentType;
import com.GKPS.Repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    //upload dokumen
    private static final String UPLOAD_DIRECTORY = System.getProperty("java.io.tmpdir") + "/uploads/";

    //upload dokumen baru
    public Dokumen uploadDokumen(MultipartFile file, String title, String description, String uploadedBy, String documentType, String documentCategory) {
        // Create the upload directory if it doesn't exist
        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName != null && originalFileName.contains(".") ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
            String newFileName = UUID.randomUUID().toString() + fileExtension;
            String filePath = UPLOAD_DIRECTORY + newFileName;
            file.transferTo(Paths.get(filePath));

            Dokumen dokumen = new Dokumen();
            dokumen.setTitle(title);
            dokumen.setDocumentType(DocumentType.valueOf(documentType));
            dokumen.setDescription(description);
            dokumen.setFilePath(filePath);
            dokumen.setFileName(newFileName);
            dokumen.setUploadedBy(uploadedBy);
            dokumen.setDocumentCategory(DocumentCategory.valueOf(documentCategory));
            dokumen.setFileSize(formatFileSize(file.getSize()));
            dokumen.setUploadDate(LocalDateTime.now());
            dokumen.setJenisFile(file.getContentType());
            dokumen.setActive(true);

            return documentRepository.save(dokumen);
        }catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(),e);
        }
    }

    public List<Dokumen> findAll() {
        return documentRepository.findAll();
    }
    public Optional<Dokumen> findById(String id) {
        return documentRepository.findByIdAndIsActiveTrue(id);
    }

    public List<Dokumen> findByUploadedBy(String uploadedBy) {
        return documentRepository.findByUploadedBy(uploadedBy);
    }

    public List<Dokumen> findByDocumentType(String documentType) {
        DocumentType type = DocumentType.valueOf(documentType);
        return documentRepository.findByDocumentType(type);
    }

    public List<Dokumen> findByDocumentCategory(String documentCategory) {
        DocumentCategory category = DocumentCategory.valueOf(documentCategory);
        return documentRepository.findByDocumentCategory(category);
    }

    public List<Dokumen> findByTitleContainingIgnoreCase(String fileName) {
        return documentRepository.findByTitleContainingIgnoreCase(fileName);
    }

    public List<Dokumen> findByUploadDate(LocalDateTime uploadDate) {
        return documentRepository.findByUploadDate(uploadDate);
    }

    //Download file
    public ResponseEntity<byte[]> downloadFile(String id) {
        Dokumen dokumen = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found with id: " + id));

        try {
            Path filePath = Paths.get(dokumen.getFilePath());
            byte[] fileContent = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dokumen.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(dokumen.getJenisFile())).body(fileContent);
        }catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }

    //update dokumen
    public Dokumen updateDokumen(String id, String title, String description, String documentType, String documentCategory) {
        Dokumen dokumen = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found with id: " + id));

        if (title != null) {
            dokumen.setTitle(title);
        }
        if (description != null) {
            dokumen.setDescription(description);
        }
        if (documentType != null) {
            dokumen.setDocumentType(DocumentType.valueOf(documentType));
        }
        if (documentCategory != null) {
            dokumen.setDocumentCategory(DocumentCategory.valueOf(documentCategory));
        }

        return documentRepository.save(dokumen);
    }

    //delete dokumen
    public void deleteDokumen(String id) {
        Dokumen dokumen = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found with id: " + id));
        dokumen.setActive(false);
        documentRepository.save(dokumen);
    }

    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", (double) size / 1024);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (double) size / (1024 * 1024));
        } else {
            return String.format("%.2f GB", (double) size / (1024 * 1024 * 1024));
        }
    }
}
