package com.GKPS.Controller;

import com.GKPS.Config.ResourceNotFoundException;
import com.GKPS.DTO.Response.ApiResponse;
import com.GKPS.Model.Dokumentasi.Sidi;
import com.GKPS.Repository.SidiRepository;
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
@RequestMapping("/api/dokumentasi/sidi")
public class SidiController {
    private final SidiRepository sidiRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Sidi>> create(@RequestBody Sidi sidi, Authentication authentication) {
            log.info("Creating sidi document for: {}", sidi.getPersonId());

        if (sidi.getPersonId() == null || sidi.getSidiDate() == null) {
            throw new IllegalArgumentException("PersonId and sidiDate must be provided");
        }
        Sidi savedSidi = sidiRepository.save(sidi);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Sidi document created successfully", savedSidi));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Sidi>>> getAll() {
        log.info("Fetching all sidi records");
        List<Sidi> sidis = sidiRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Sidi records retrieved successfully", sidis));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sidi>> getById(@PathVariable String id) {
        log.info("Fetching sidi record with ID: {}", id);
        Sidi sidi = sidiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi", "id", id));
        return ResponseEntity.ok(ApiResponse.success("Sidi record retrieved successfully", sidi));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<Sidi>> getByPersonId(@PathVariable String personId) {
        log.info("Fetching sidi record for person: {}", personId);
        Sidi sidi = sidiRepository.findByPersonId(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Sidi untuk person", "personId", personId));
        return ResponseEntity.ok(ApiResponse.success("Sidi record retrieved successfully", sidi));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Sidi>>> getByStatus(@PathVariable String status) {
        log.info("Fetching sidi with status: {}", status);
        List<Sidi> sidis = sidiRepository.findByApprovalStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Sidi records retrieved by status", sidis));
    }

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

