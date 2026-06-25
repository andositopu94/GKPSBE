package com.GKPS.Controller;

import com.GKPS.Model.Dokumentasi.Dokumen;
import com.GKPS.Service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Dokumen> uploadDocument(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("title") String title,
                                                  @RequestParam(value = "description", required = false) String description,
                                                  @RequestParam(value = "uploadedBy", required = false) String uploadedBy,
                                                  @RequestParam("documentType") String documentType,
                                                  @RequestParam(value = "documentCategory", required = false) String documentCategory) {
        Dokumen dokumen = documentService.uploadDokumen(file, title, description, uploadedBy, documentType, documentCategory);
        return ResponseEntity.ok(dokumen);
    }

    @GetMapping
    public List<Dokumen> findAllDokumen() {
        return documentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dokumen> findDokumenById(@PathVariable String id) {
        return documentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uploadedBy/{uploadedBy}")
    public List<Dokumen> findDokumenByUploadedBy(@PathVariable String uploadedBy) {
        return documentService.findByUploadedBy(uploadedBy);
    }

    @GetMapping("/category/{category}")
    public List<Dokumen> findDokumenByCategory(@PathVariable String category) {
        return documentService.findByDocumentCategory(category);
    }

    @GetMapping("/type/{type}")
    public List<Dokumen> findDokumenByType(@PathVariable String type) {
        return documentService.findByDocumentType(type);
     }

    @GetMapping("/uploadDate/{uploadDate}")
    public List<Dokumen> findDokumenByUploadDate(@PathVariable LocalDateTime uploadDate) {
            return documentService.findByUploadDate(uploadDate);
        }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadDokumen(@PathVariable String id) {
        return documentService.downloadFile(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dokumen> updateDokumen(@PathVariable String id,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "documentType", required = false) String documentType,
                                   @RequestParam(value = "documentCategory", required = false) String documentCategory) {

        Dokumen dokumen = documentService.updateDokumen(id, title, description, documentType, documentCategory);
        return ResponseEntity.ok(dokumen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDokumen(@PathVariable String id) {
        documentService.deleteDokumen(id);
        return ResponseEntity.ok().build();
    }
}
