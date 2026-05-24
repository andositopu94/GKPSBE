package com.GKPS.Controller;

import com.GKPS.Model.JadwalKonseling;
import com.GKPS.Service.JadwalKonselingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jadwal-konseling")
public class JadwalKonselingController {
        private final JadwalKonselingService jadwalKonselingService;

        public JadwalKonselingController(JadwalKonselingService jadwalKonselingService) {
            this.jadwalKonselingService = jadwalKonselingService;
        }

        @GetMapping
        public ResponseEntity<List<JadwalKonseling>> getAll(@RequestParam(required = false) Boolean tersedia,
                                                        @RequestParam(required = false) Boolean upcoming) {
        if (Boolean.TRUE.equals(tersedia)) {
            return ResponseEntity.ok(jadwalKonselingService.getTersedia());
        }
        if (Boolean.TRUE.equals(upcoming)) {
            return ResponseEntity.ok(jadwalKonselingService.getUpComing());
        }
        return ResponseEntity.ok(jadwalKonselingService.getAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<JadwalKonseling> getById(@PathVariable String id) {
            return jadwalKonselingService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<JadwalKonseling> create(@RequestBody JadwalKonseling jadwalKonseling) {
            return ResponseEntity.ok(jadwalKonselingService.save(jadwalKonseling));
        }

        @PutMapping("/{id}")
        public ResponseEntity<JadwalKonseling> update(@PathVariable String id, @RequestBody JadwalKonseling jadwalKonseling) {
            return jadwalKonselingService.update(id, jadwalKonseling)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return jadwalKonselingService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
