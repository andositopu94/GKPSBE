package com.GKPS.Controller;

import com.GKPS.DTO.KeuanganSummaryDto;
import com.GKPS.Service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/keuangan")
public class KeuanganController {
    @Autowired
    private KeuanganService keuanganService;

    @GetMapping("/uang-masuk")
    public ResponseEntity<List<KeuanganSummaryDto>> getUangMasuk(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<KeuanganSummaryDto> summary = keuanganService.getUangMasukSummary(startDate, endDate);
        return ResponseEntity.ok(summary);

    }

     @GetMapping("/uang-keluar")
    public ResponseEntity<List<KeuanganSummaryDto>> getUangKeluar(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<KeuanganSummaryDto> summary = keuanganService.getUangKeluarSummary(startDate, endDate);
        return ResponseEntity.ok(summary);

    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getKeuanganSummary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<KeuanganSummaryDto> uangMasuk = keuanganService.getUangMasukSummary(startDate, endDate);
        List<KeuanganSummaryDto> uangKeluar = keuanganService.getUangKeluarSummary(startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("uangMasuk", uangMasuk);
        response.put("uangKeluar", uangKeluar);

        //jumlah total
        double totalMasuk = uangMasuk.stream().mapToDouble(KeuanganSummaryDto::getTotalPemasukan).sum();
        double totalKeluar = uangKeluar.stream().mapToDouble(KeuanganSummaryDto::getTotalPemasukan).sum();

        response.put("totalMasuk", totalMasuk);
        response.put("totalKeluar", totalKeluar);
        response.put("saldo", totalMasuk - totalKeluar);

        return ResponseEntity.ok(response);

    }
}
