package com.GKPS.Controller;

import com.GKPS.DTO.Request.ReportRequestDto;
import com.GKPS.DTO.Response.StatisticReportDto;
import com.GKPS.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;


    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('MAJELIS', 'PENDETA', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<StatisticReportDto> generateReport(@RequestBody ReportRequestDto reportRequestDto) {
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    //generate laporan jemaat
    @PostMapping("/generate/jemaat")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'SEKTOR', 'ADMIN')")
    public ResponseEntity<StatisticReportDto> generateJemaatReport(@RequestBody ReportRequestDto reportRequestDto) {
        reportRequestDto.setReportType("jemaat");
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    //generate laporan keuangan
    @PostMapping("/generate/financial")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'BENDAHARA_SEKSI', 'ADMIN')")
    public ResponseEntity<StatisticReportDto> generateFinancialReport(@RequestBody ReportRequestDto reportRequestDto) {
        reportRequestDto.setReportType("keuangan");
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    //generate laporan keuangan per seksi
    @PostMapping("/generate/financial/section")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'BENDAHARA_SEKSI', 'ADMIN')")
    public ResponseEntity<StatisticReportDto> generateFinancialReportBySection(@RequestBody ReportRequestDto reportRequestDto) {
        reportRequestDto.setReportType("keuangan_seksi");
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    //generate laporan organisasi
    @PostMapping("/organisasi")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'ADMIN')")
    public ResponseEntity<StatisticReportDto> generateOrganizationReport(@RequestBody ReportRequestDto reportRequestDto) {
        reportRequestDto.setReportType("organisasi");
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

    //generate laporan ibadah
    @PostMapping("/ibadah")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'ADMIN', 'PENDETA', 'BENDAHARA', 'BENDAHARA_SEKSI', 'SINTUA')")
    public ResponseEntity<StatisticReportDto> generateIbadahReport(@RequestBody ReportRequestDto reportRequestDto) {
        reportRequestDto.setReportType("ibadah");
        StatisticReportDto report = reportService.generateReport(reportRequestDto);
        return ResponseEntity.ok(report);
    }

}
