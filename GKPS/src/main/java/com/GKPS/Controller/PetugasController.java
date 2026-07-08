package com.GKPS.Controller;

import com.GKPS.Model.PetugasIbadah;
import com.GKPS.Repository.PetugasIbadahRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/petugas-ibadah", "/api/ibadah/petugas", "/api/admin/ibadah/petugas"})
public class PetugasController {
    private final PetugasIbadahRepository petugasIbadahRepository;

    @GetMapping
    public ResponseEntity<List<PetugasIbadah>> getAll() {
        return ResponseEntity.ok(petugasIbadahRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetugasIbadah> getById(@PathVariable String id) {
        return petugasIbadahRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jadwal/{jadwalIbadahId}")
    public ResponseEntity<List<PetugasIbadah>> getByJadwalIbadahId(@PathVariable String jadwalIbadahId) {
        PetugasIbadah petugasIbadah = petugasIbadahRepository.findByJadwalIbadahId(jadwalIbadahId);
        return petugasIbadah == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(List.of(petugasIbadah));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<PetugasIbadah> create(@RequestBody PetugasIbadah petugasIbadah) {
        return ResponseEntity.ok(petugasIbadahRepository.save(petugasIbadah));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<PetugasIbadah> update(@PathVariable String id, @RequestBody PetugasIbadah petugasIbadah) {
        if (!petugasIbadahRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        petugasIbadah.setId(id);
        return ResponseEntity.ok(petugasIbadahRepository.save(petugasIbadah));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!petugasIbadahRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        petugasIbadahRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
