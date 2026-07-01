package com.GKPS.Controller;

import com.GKPS.Exception.InvalidInputException;
import com.GKPS.Exception.ResourceNotFoundException;
import com.GKPS.Model.Dokumentasi.Baptism;
import com.GKPS.Repository.BaptismRepository;
import com.GKPS.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dokumentasi/baptism")
@RequiredArgsConstructor
@Slf4j
public class BaptismController {
    private final BaptismRepository baptismRepository;

    /**
     * Create new baptism record
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Baptism>> create(
            @RequestBody Baptism baptism,
            Authentication authentication) {
        log.info("Creating baptism record for person: {}", baptism.getPersonId());

        if (baptism.getPersonId() == null || baptism.getBaptismDate() == null) {
            throw new InvalidInputException("Person ID dan Baptism Date harus diisi");
        }

        Baptism saved = baptismRepository.save(baptism);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Baptism record created successfully", saved));
    }

    /**
     * Get all baptism records
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Baptism>>> getAll() {
        log.info("Fetching all baptism records");
        List<Baptism> baptisms = baptismRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Baptism records retrieved successfully", baptisms));
    }

    /**
     * Get baptism by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Baptism>> getById(@PathVariable String id) {
        log.info("Fetching baptism record with ID: {}", id);
        Baptism baptism = baptismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Baptism", "id", id));
        return ResponseEntity.ok(ApiResponse.success("Baptism record retrieved successfully", baptism));
    }

    /**
     * Get baptisms by person ID
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<Baptism>> getByPersonId(@PathVariable String personId) {
        log.info("Fetching baptism record for person: {}", personId);
        Baptism baptism = baptismRepository.findByPersonId(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Baptism untuk person", "personId", personId));
        return ResponseEntity.ok(ApiResponse.success("Baptism record retrieved successfully", baptism));
    }

    /**
     * Get baptisms by approval status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Baptism>>> getByStatus(@PathVariable String status) {
        log.info("Fetching baptisms with status: {}", status);
        List<Baptism> baptisms = baptismRepository.findByApprovalStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Baptisms retrieved by status", baptisms));
    }

    /**
     * Update baptism record
     */
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

    /**
     * Delete baptism record
     */
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
