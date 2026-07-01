package com.GKPS.Controller;

import com.GKPS.Exception.InvalidInputException;
import com.GKPS.Exception.ResourceNotFoundException;
import com.GKPS.Model.Dokumentasi.Marriage;
import com.GKPS.Repository.MarriageRepository;
import com.GKPS.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dokumentasi/marriage")
@RequiredArgsConstructor
@Slf4j
public class MarriageController {
    private final MarriageRepository marriageRepository;

    /**
     * Create new marriage record
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Marriage>> create(
            @RequestBody Marriage marriage,
            Authentication authentication) {
        log.info("Creating marriage record for husband: {}, wife: {}", marriage.getHusbandId(), marriage.getWifeId());

        if (marriage.getHusbandId() == null || marriage.getWifeId() == null) {
            throw new InvalidInputException("Husband ID dan Wife ID harus diisi");
        }

        Marriage saved = marriageRepository.save(marriage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Marriage record created successfully", saved));
    }

    /**
     * Get all marriage records
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Marriage>>> getAll() {
        log.info("Fetching all marriage records");
        List<Marriage> marriages = marriageRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Marriage records retrieved successfully", marriages));
    }

    /**
     * Get marriage by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Marriage>> getById(@PathVariable String id) {
        log.info("Fetching marriage record with ID: {}", id);
        Marriage marriage = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage", "id", id));
        return ResponseEntity.ok(ApiResponse.success("Marriage record retrieved successfully", marriage));
    }

    /**
     * Get marriages by approval status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Marriage>>> getByStatus(@PathVariable String status) {
        log.info("Fetching marriages with status: {}", status);
        List<Marriage> marriages = marriageRepository.findByApprovalStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Marriages retrieved by status", marriages));
    }

    /**
     * Update marriage record
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Marriage>> update(
            @PathVariable String id,
            @RequestBody Marriage marriage,
            Authentication authentication) {
        log.info("Updating marriage record with ID: {}", id);

        Marriage existing = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage", "id", id));

        marriage.setId(id);
        Marriage updated = marriageRepository.save(marriage);
        return ResponseEntity.ok(ApiResponse.success("Marriage record updated successfully", updated));
    }

    /**
     * Delete marriage record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable String id,
            Authentication authentication) {
        log.info("Deleting marriage record with ID: {}", id);

        Marriage marriage = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage", "id", id));

        marriageRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Marriage record deleted successfully", null));
    }
}
