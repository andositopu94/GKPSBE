package com.GKPS.Controller;

import com.GKPS.DTO.EwartaPortalDto;
import com.GKPS.Service.EwartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ewarta")
public class EwartaController {
    @Autowired
    private EwartaService ewartaService;

    @GetMapping("/beranda")
    public ResponseEntity<EwartaPortalDto> getEwartaPortalData() {
        EwartaPortalDto dto = ewartaService.getEwartaPortalDto();
        return ResponseEntity.ok(dto);
    }
}
