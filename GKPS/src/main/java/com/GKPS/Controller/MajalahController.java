package com.GKPS.Controller;

import com.GKPS.Model.MajalahRohani;
import com.GKPS.Service.MajalahService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/majalah", "/api/public/majalah", "/api/admin/majalah"})
public class MajalahController {
    private final MajalahService majalahService;

    @GetMapping
    public ResponseEntity<List<MajalahRohani>> getAll(@RequestParam(defaultValue = "false") boolean approvedOnly) {
        if (approvedOnly) {
            return ResponseEntity.ok(majalahService.getApprovedMajalahRohani());
        }
        return ResponseEntity.ok(majalahService.getAllMajalah());
    }

    @GetMapping("/approved")
    public ResponseEntity<List<MajalahRohani>> getApprovedMajalah() {
        return ResponseEntity.ok(majalahService.getApprovedMajalahRohani());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajalahRohani> getById(@PathVariable String id) {
        return majalahService.getMajalahRohaniById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<MajalahRohani> create(@RequestBody MajalahRohani majalahRohani) {
        return ResponseEntity.ok(majalahService.createMajalahRohani(majalahRohani));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<MajalahRohani> update(@PathVariable String id, @RequestBody MajalahRohani majalahRohani) {
        if (!majalahService.getMajalahRohaniById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(majalahService.updateMajalahRohani(id, majalahRohani));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!majalahService.getMajalahRohaniById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        majalahService.deleteMajalahRohani(id);
        return ResponseEntity.noContent().build();
    }
}
