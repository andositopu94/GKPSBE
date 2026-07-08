package com.GKPS.Controller;

import com.GKPS.Model.JadwalIbadah;
import com.GKPS.Repository.JadwalIbadahRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping({"/api/jadwal-ibadah", "/api/ibadah/jadwal","/api/public/jadwal-ibadah", "/api/admin/ibadah/jadwal"})
public class JadwalIbadahController {
    private final JadwalIbadahRepository jadwalIbadahRepository;

    @GetMapping
    public ResponseEntity<List<JadwalIbadah>> getAll() {
        return ResponseEntity.ok(jadwalIbadahRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JadwalIbadah> getById(@PathVariable String id) {
        return jadwalIbadahRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<JadwalIbadah> create(@RequestBody JadwalIbadah jadwalIbadah) {
        log.info("Creating new JadwalIbadah: {}", jadwalIbadah.getNama());
        return ResponseEntity.ok(jadwalIbadahRepository.save(jadwalIbadah));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<JadwalIbadah> update(@PathVariable String id, @RequestBody JadwalIbadah jadwalIbadah) {
        if (!jadwalIbadahRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jadwalIbadah.setId(id);
        return ResponseEntity.ok(jadwalIbadahRepository.save(jadwalIbadah));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!jadwalIbadahRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jadwalIbadahRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
