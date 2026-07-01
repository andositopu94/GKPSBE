package com.GKPS.Controller;

import com.GKPS.Exception.InvalidInputException;
import com.GKPS.Exception.ResourceNotFoundException;
import com.GKPS.Model.Dokumentasi.Sidi;
import com.GKPS.Repository.SidiRepository;
import com.GKPS.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dokumentasi/sidi")
@RequiredArgsConstructor
@Slf4j
public class SidiController {
    private final SidiRepository sidiRepository;

    /**
     * Create new sidi record
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Sidi>> create(
            @RequestBody Sidi sidi,
            Authentication authentication) {
        log.info("Creating sidi record for person: {}", sidi.getPersonId());

        if (sidi.getPersonId() == null || sidi.getSidiDate() == null) {
            throw new InvalidInputException("Person ID dan Sidi Date harus diisi");
        }

        Sidi saved = sidiRepository.save(sidi);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sidi record created successfully", saved));
    }

    /**
     * Get all sidi records
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Sidi>>> getAll() {
        log.info("Fetching all sidi records");
        List<Sidi> sidis = sidiRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Sidi records retrieved successfully", sidis));
    }

    /**
     * Get sidi by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sidi>> getById(@PathVariable String id) {
        log.info("Fetching sidi record with ID: {}", id);
        Sidi sidi = sidiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi", "id", id));
        return ResponseEntity.ok(ApiResponse.success("Sidi record retrieved successfully", sidi));
    }

    /**
     * Get sidi by person ID
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<Sidi>> getByPersonId(@PathVariable String personId) {
        log.info("Fetching sidi record for person: {}", personId);
        Sidi sidi = sidiRepository.findByPersonId(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi untuk person", "personId", personId));
        return ResponseEntity.ok(ApiResponse.success("Sidi record retrieved successfully", sidi));
    }

    /**
     * Get sidi by approval status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Sidi>>> getByStatus(@PathVariable String status) {
        log.info("Fetching sidi with status: {}", status);
        List<Sidi> sidis = sidiRepository.findByApprovalStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Sidi records retrieved by status", sidis));
    }

    /**
     * Update sidi record
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Sidi>> update(
            @PathVariable String id,
            @RequestBody Sidi sidi,
            Authentication authentication) {
        log.info("Updating sidi record with ID: {}", id);

        Sidi existing = sidiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi", "id", id));

        sidi.setId(id);
        Sidi updated = sidiRepository.save(sidi);
        return ResponseEntity.ok(ApiResponse.success("Sidi record updated successfully", updated));
    }

    /**
     * Delete sidi record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable String id,
            Authentication authentication) {
        log.info("Deleting sidi record with ID: {}", id);

        Sidi sidi = sidiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi", "id", id));

        sidiRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Sidi record deleted successfully", null));
    }
}
