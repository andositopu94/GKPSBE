package com.GKPS.Controller;

import com.GKPS.Config.InvalidInputException;
import com.GKPS.Config.ResourceNotFoundException;
import com.GKPS.DTO.Response.ApiResponse;
import com.GKPS.Model.Dokumentasi.Marriage;
import com.GKPS.Repository.MarriageRepository;
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
@RequestMapping("/api/dokumentasi/marriage")
public class MarriageController {
    private final MarriageRepository marriageRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Marriage>> createMarriageDocument(@RequestBody Marriage marriage, Authentication authentication) {
        log.info("Creating marriage document for: {}", marriage.getHusbandId(), marriage.getWifeId());

        if (marriage.getHusbandId() == null || marriage.getWifeId() == null) {
            throw new InvalidInputException("HusbandId and wifeId Harus diisi");
        }

        Marriage savedMarriage = marriageRepository.save(marriage);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Marriage document created successfully", savedMarriage));
    }

    //get all marriage record
    @GetMapping
    public ResponseEntity<ApiResponse<List<Marriage>>> getAllMarriageDocuments() {
        log.info("Fetching all marriage documents");
        List<Marriage> marriages = marriageRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("Fetched all marriage documents", marriages));
    }

    //get marriage by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Marriage>> getMarriageDocumentById(@PathVariable String id) {
        log.info("Fetching marriage document with ID: {}", id);
        Marriage marriage = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage document not found with ID: " + id));
        return ResponseEntity.ok(ApiResponse.success("Fetched marriage document", marriage));
    }

    //get marriages by approval status
    @GetMapping("/status/{approvalStatus}")
    public ResponseEntity<ApiResponse<List<Marriage>>> getMarriageDocumentsByApprovalStatus(@PathVariable String approvalStatus) {
        log.info("Fetching marriage documents with approval status: {}", approvalStatus);
        List<Marriage> marriages = marriageRepository.findByApprovalStatus(approvalStatus);
        return ResponseEntity.ok(ApiResponse.success("Fetched marriage documents by approval status", marriages));
    }

    //update marriage record
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Marriage>> update(@PathVariable String id, @RequestBody Marriage marriage, Authentication authentication) {
        log.info("Updating marriage document with ID: {}", id);

        Marriage existingMarriage = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage document not found with ID: " + id));
        marriage.setId(id);
        Marriage updatedMarriage = marriageRepository.save(marriage);
        return ResponseEntity.ok(ApiResponse.success("Marriage document updated successfully", updatedMarriage));
    }

    //delete marriage record
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id, Authentication authentication) {
        log.info("Deleting marriage document with ID: {}", id);

        Marriage existingMarriage = marriageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marriage document not found with ID: " + id));
        marriageRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Marriage document deleted successfully", null));
    }

}
