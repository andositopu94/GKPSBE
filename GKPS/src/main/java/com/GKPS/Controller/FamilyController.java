package com.GKPS.Controller;

import com.GKPS.DTO.Request.FamilyRequestDto;
import com.GKPS.DTO.Response.FamilyResponseDto;
import com.GKPS.DTO.Response.PageResponse;
import com.GKPS.Model.Organisasi.Family;
import com.GKPS.Service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;

    @PostMapping
    public Family createFamily(@RequestBody FamilyRequestDto familyRequestDto) {
       return familyService.save(familyRequestDto);
    }

    @GetMapping
    public ResponseEntity<PageResponse<FamilyResponseDto>> findAll(@PageableDefault(page = 0, size = 20, sort = "namaKepalaKeluarga", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<FamilyResponseDto> familyPage = familyService.findAll(pageable);
        return ResponseEntity.ok(PageResponse.fromPage(familyPage));
    }

    @GetMapping("/{id}")
    public FamilyResponseDto findById(@PathVariable String id) {
        return familyService.findById(id);
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        familyService.delete(id);
//    }
    @PatchMapping("/{id}/deactive")
    public void deactive(@PathVariable String id) {
        familyService.deactive(id);
    }

    @PutMapping("/{id}")
    public FamilyResponseDto updateFamily(@PathVariable String id, @RequestBody FamilyRequestDto familyRequestDto) {
        return familyService.update(id, familyRequestDto);
    }
}
