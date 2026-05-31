package com.GKPS.Controller;

import com.GKPS.Model.TataIbadah;
import com.GKPS.Service.TataIbadahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TataIbadah> create(@RequestBody TataIbadah tataIbadah){
        return ResponseEntity.ok(tataIbadahService.createTataIbadah(tataIbadah));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TataIbadah> update(@PathVariable String id, @RequestBody TataIbadah tataIbadah){
        return tataIbadahService.updateTataIbadah(id, tataIbadah)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        return tataIbadahService.deleteTataIbadah(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
