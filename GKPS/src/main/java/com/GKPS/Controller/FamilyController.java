package com.GKPS.Controller;

import com.GKPS.DTO.FamilyRequestDto;
import com.GKPS.DTO.FamilyResponseDto;
import com.GKPS.Model.Organisasi.Family;
import com.GKPS.Service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;

    @PostMapping
    public Family createFamily(@RequestBody FamilyRequestDto familyRequestDto) {
       return familyService.save(familyRequestDto);
    }

    @GetMapping
    public List<FamilyResponseDto> findAll() {
        return familyService.findAll();
    }

    @GetMapping("/{id}")
    public FamilyResponseDto findById(@PathVariable String id) {
        return familyService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        familyService.delete(id);
    }
}
