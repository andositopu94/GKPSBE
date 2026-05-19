package com.GKPS.Controller;

import com.GKPS.Model.InfoGereja;
import com.GKPS.Service.InfoGerejaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/info-gereja")
public class InfoGerejaController {
    @Autowired
    private final InfoGerejaService infoGerejaService;

    public InfoGerejaController(InfoGerejaService infoGerejaService) {
        this.infoGerejaService = infoGerejaService;
    }

    @GetMapping
    public ResponseEntity<List<InfoGereja>> getAll(){
        return ResponseEntity.ok(infoGerejaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfoGereja> getById(@PathVariable String id){
        return infoGerejaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
     }

    @PostMapping
    public ResponseEntity<InfoGereja> create(@RequestBody InfoGereja infoGereja){
        return ResponseEntity.ok(infoGerejaService.save(infoGereja));
     }

    @PutMapping("/{id}")
    public ResponseEntity<InfoGereja> update(@PathVariable String id, @RequestBody InfoGereja infoGereja){
        return infoGerejaService.update(id, infoGereja)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
     }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return infoGerejaService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
