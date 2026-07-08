package com.GKPS.Controller;

import com.GKPS.Model.TataIbadah;
import com.GKPS.Service.TataIbadahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tataibadah")
public class TataIbadahController {
    @Autowired
    private TataIbadahService tataIbadahService;

    @GetMapping
    public ResponseEntity<List<TataIbadah>> getAll(){
        return ResponseEntity.ok(tataIbadahService.getAllTataIbadah());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TataIbadah> getById(@PathVariable String id){
        return tataIbadahService.getTataIbadahById(id)
//                .map(ResponseEntity::ok)
                .map(foundTataIbadah -> ResponseEntity.ok(foundTataIbadah))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PENDETA', 'ADMIN', 'MAJELIS', 'SINTUA', 'SEKRETARIS')")
    public ResponseEntity<TataIbadah> create(@RequestBody TataIbadah tataIbadah){
        return ResponseEntity.ok(tataIbadahService.createTataIbadah(tataIbadah));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PENDETA', 'ADMIN', 'MAJELIS', 'SINTUA', 'SEKRETARIS')")
    public ResponseEntity<TataIbadah> update(@PathVariable String id, @RequestBody TataIbadah tataIbadah){
        return tataIbadahService.updateTataIbadah(id, tataIbadah)
                .map(updatedTataIbadah -> ResponseEntity.ok(updatedTataIbadah))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PENDETA', 'ADMIN', 'MAJELIS', 'SINTUA', 'SEKRETARIS')")
    public ResponseEntity<Void> delete(@PathVariable String id){
        return tataIbadahService.deleteTataIbadah(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
