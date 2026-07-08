package com.GKPS.Controller;

import com.GKPS.Model.Renungan;
import com.GKPS.Service.RenunganService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/renungan", "/api/admin/renungan", "/api/public/renungan"})
public class RenunganController {
    private final RenunganService renunganService;

    @GetMapping
    public ResponseEntity<List<Renungan>> getAll(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate afterDate){

            if (afterDate != null) {
                return ResponseEntity.ok(renunganService.getRenunganByDateAfter(afterDate));
            }
            return ResponseEntity.ok(renunganService.getAllRenungan());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Renungan> getById(@PathVariable String id) {
        return renunganService.getRenunganById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Renungan> create(@RequestBody Renungan renungan) {
        return ResponseEntity.ok(renunganService.createRenungan(renungan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Renungan> update(@PathVariable String id, @RequestBody Renungan renungan) {
        if (!renunganService.getRenunganById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(renunganService.updateRenungan(id, renungan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!renunganService.getRenunganById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        renunganService.deleteRenungan(id);
        return ResponseEntity.noContent().build();
    }
}
