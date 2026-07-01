package com.GKPS.Controller;

import com.GKPS.Config.ResourceNotFoundException;
import com.GKPS.DTO.Response.ApiResponse;
import com.GKPS.Model.Dokumentasi.Baptism;
import com.GKPS.Repository.BaptismRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/dokumentasi/baptism")
public class BaptismController {
    private final BaptismRepository baptismRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Baptism>> create (@RequestBody Baptism baptism, Authentication authentication) {
        log.info("Creating baptism document for: {}", baptism.getPersonId());

        if (baptism.getPersonId() == null || baptism.getBaptismDate() == null) {
            throw new IllegalArgumentException("PersonId and baptismDate must be provided");
        }
        Baptism savedBaptism = baptismRepository.save(baptism);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Baptism document created successfully", savedBaptism));
    }

    //get all baptism record
    @GetMapping
    public ResponseEntity<ApiResponse<List<Baptism>>> getAll() {
        log.info("Fetching all baptism documents");
        List<Baptism> baptisms = baptismRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Fetched all baptism documents", baptisms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Baptism>> getById(@PathVariable String id) {
        log.info("Fetching baptism record with ID: {}", id);
        Baptism baptism = baptismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baptism", "id", id));
        return ResponseEntity.ok(ApiResponse.success("Baptism record retrieved successfully", baptism));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<Baptism>> getByPersonId(@PathVariable String personId) {
        log.info("Fetching baptism record for person: {}", personId);

        Baptism baptism = baptismRepository.findByPersonId(personId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Baptism", "personId", personId));
        return ResponseEntity.ok(ApiResponse.success("Baptism record retrieved successfully", baptism));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Baptism>>> getByStatus(@PathVariable String status) {
        log.info("Fetching baptisms with status: {}", status);
        List<Baptism> baptisms = baptismRepository.findByApprovalStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Baptisms retrieved by status", baptisms));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Baptism>> update(
            @PathVariable String id,
            @RequestBody Baptism baptism,
            Authentication authentication) {
        log.info("Updating baptism record with ID: {}", id);

        Baptism existing = baptismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baptism", "id", id));

        baptism.setId(id);
        Baptism updated = baptismRepository.save(baptism);
        return ResponseEntity.ok(ApiResponse.success("Baptism record updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable String id,
            Authentication authentication) {
        log.info("Deleting baptism record with ID: {}", id);

        Baptism baptism = baptismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baptism", "id", id));

        baptismRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Baptism record deleted successfully", null));
    }
}
