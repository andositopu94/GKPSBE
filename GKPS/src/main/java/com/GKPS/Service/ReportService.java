package com.GKPS.Service;

import com.GKPS.DTO.Request.ReportRequestDto;
import com.GKPS.DTO.Response.StatisticReportDto;
import com.GKPS.Model.Enum.RoleType;
import com.GKPS.Model.Keuangan.Transaction;
import com.GKPS.Repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {
    private final PersonRepository personRepository;
    private final FamilyRepository familyRepository;
    private final OrganizationRepository organizationRepository;
    private final TransactionRepositroy transactionRepositroy;
    private final AccountRepository accountRepository;

    public ReportService(PersonRepository personRepository, FamilyRepository familyRepository, OrganizationRepository organizationRepository, TransactionRepositroy transactionRepositroy, AccountRepository accountRepository) {
        this.personRepository = personRepository;
        this.familyRepository = familyRepository;
        this.organizationRepository = organizationRepository;
        this.transactionRepositroy = transactionRepositroy;
        this.accountRepository = accountRepository;
    }

    public StatisticReportDto generateReport(ReportRequestDto reportRequestDto) {
        validateDateRange(reportRequestDto.getStartDate(), reportRequestDto.getEndDate());
        String currentUser = getCurrentUsername();
        LocalDate today = LocalDate.now();

        StatisticReportDto report = switch (reportRequestDto.getReportType().toLowerCase()) {
            case "jemaat" -> generateJemaatReport(reportRequestDto, currentUser);
            case "keuangan" -> generateFinancialReport(reportRequestDto, currentUser);
            case "keuangan_seksi" -> generateFinancialReportBySection(reportRequestDto, currentUser);
            case "organisasi" -> generateOrganizationReport(reportRequestDto, currentUser);
            case "ibadah" -> generateIbadahReport(reportRequestDto, currentUser);
            default -> throw new IllegalArgumentException("Invalid report type: " + reportRequestDto.getReportType());
        };

        report.getGeneratedAt(new StatisticReportDto.GeneratedAt(today, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), currentUser));
        return report;
    }

    //laporan statistik jemaat

    private StatisticReportDto generateJemaatReport(ReportRequestDto reportRequestDto, String generatedBy) {
        Map<String, Object> summary = new LinkedHashMap<>();

        long totalJemaat = personRepository.count();
        long totalKeluarga = familyRepository.count();

        Map<String, Long> genderStatistics = getGenderStatistics();
        Map<String, Long> baptismStatistics = getBaptismStatistics();
        Map<String, Long> sidiStatistics = getSidiStatistics();
        Map<String, Long> pengurusStatistics = getPengurusStatistics();
        Map<String, Long> sektorStatistics = getSektorStatistics();
        Map<String, Long> ageGroupStatistics = getAgeGroupStatistics();

        summary.put("totalJemaat", totalJemaat);
        summary.put("totalKeluarga", totalKeluarga);
        summary.put("genderStatistics", genderStatistics);
        summary.put("baptismStatistics", baptismStatistics);
        summary.put("sidiStatistics", sidiStatistics);
        summary.put("pengurusStatistics", pengurusStatistics);
        summary.put("sektorStatistics", sektorStatistics);
        summary.put("ageGroupStatistics", ageGroupStatistics);

        List<Map<String, Object>> jemaatDetails = getJemaatDetails(reportRequestDto);

        StatisticReportDto report = new StatisticReportDto();
        report.setReportType("JEMAAT");
        report.setStartDate(reportRequestDto.getStartDate());
        report.setEndDate(reportRequestDto.getEndDate());
        report.setSummary(summary);
        report.setDetails(jemaatDetails);

        return report;
    }

    //laporan statistik keuangan
    private StatisticReportDto generateFinancialReport(ReportRequestDto reportRequestDto, String generatedBy) {
        LocalDate startDate = reportRequestDto.getStartDate();
        LocalDate endDate = reportRequestDto.getEndDate();
        Map<String, Object> summary = new LinkedHashMap<>();

        long totalTransactions = transactionRepositroy.count();
        double totalPemasukkan = calculateTotalByType(startDate, endDate, "Masuk");
        double totalPengeluaran = calculateTotalByType(startDate, endDate, "Keluar");
        double saldo = totalPemasukkan - totalPengeluaran;

        //pemasukkan perkategori
        Map<String, Double> pengeluaranByCategory = getTransactionsByCategory(startDate, endDate, "Keluar");
        Map<String, Double> pemasukkanByCategory = getTransactionsByCategory(startDate, endDate, "Masuk");
        Map<String, Map<String, Double>> monthlyTrend = getMonthlyTrend(startDate, endDate);

        summary.put("totalTransactions", totalTransactions);
        summary.put("totalPemasukkan", totalPemasukkan);
        summary.put("totalPengeluaran", totalPengeluaran);
        summary.put("saldo", saldo);
        summary.put("pengeluaranByCategory", pengeluaranByCategory);
        summary.put("pemasukkanByCategory", pemasukkanByCategory);
        summary.put("monthlyTrend", monthlyTrend);

        List<Map<String, Object>> financialDetails = getKeuanganDetails(reportRequestDto, startDate, endDate);

        StatisticReportDto report = new StatisticReportDto();
        report.setReportType("KEUANGAN");
        report.setStartDate(reportRequestDto.getStartDate());
        report.setEndDate(reportRequestDto.getEndDate());
        report.setSummary(summary);
        report.setDetails(financialDetails);

        return report;
    }

    //laporan statistik keuangan per seksi
    private StatisticReportDto generateFinancialReportBySection(ReportRequestDto reportRequestDto, String generatedBy) {
        LocalDate startDate = reportRequestDto.getStartDate();
        LocalDate endDate = reportRequestDto.getEndDate();
        Map<String, Object> summary = new LinkedHashMap<>();

        long totalTransactions = transactionRepositroy.count();
        double totalPemasukkan = calculateTotalByType(startDate, endDate, "Masuk");
        double totalPengeluaran = calculateTotalByType(startDate, endDate, "Keluar");
        double saldo = totalPemasukkan - totalPengeluaran;

        //pemasukkan perkategori
        Map<String, Double> pengeluaranByCategory = getTransactionsByCategory(startDate, endDate, "Keluar");
        Map<String, Double> pemasukkanByCategory = getTransactionsByCategory(startDate, endDate, "Masuk");
        Map<String, Map<String, Double>> monthlyTrend = getMonthlyTrend(startDate, endDate);

        summary.put("totalTransactions", totalTransactions);
        summary.put("totalPemasukkan", totalPemasukkan);
        summary.put("totalPengeluaran", totalPengeluaran);
        summary.put("saldo", saldo);
        summary.put("pengeluaranByCategory", pengeluaranByCategory);
        summary.put("pemasukkanByCategory", pemasukkanByCategory);
        summary.put("monthlyTrend", monthlyTrend);

        List<Map<String, Object>> financialDetails = getKeuanganDetailsBySection(reportRequestDto, startDate, endDate);

        StatisticReportDto report = new StatisticReportDto();
        report.setReportType("KEUNGAN_SEKSI");
        report.setStartDate(reportRequestDto.getStartDate());
        report.setEndDate(reportRequestDto.getEndDate());
        report.setSummary(summary);
        report.setDetails(financialDetails);

        return report;
    }

    //laporan statistik organisasi
    private StatisticReportDto generateOrganizationReport(ReportRequestDto reportRequestDto, String generatedBy) {
        Map<String, Object> summary = new LinkedHashMap<>();

        long totalOrganizations = organizationRepository.count();
        Map<RoleType, Long> roleDistribution = getRoleDistribution();
        Map<String, Long> seksiDistribution = getSeksiDistribution();

        summary.put("totalPengurus", totalOrganizations);
        summary.put("roleDistribution", roleDistribution);
        summary.put("seksiDistribution", seksiDistribution);

        List<Map<String, Object>> organizationDetails = getOrganizationDetails(reportRequestDto);

        StatisticReportDto report = new StatisticReportDto();
        report.setReportType("ORGANISASI");
        report.setStartDate(reportRequestDto.getStartDate());
        report.setEndDate(reportRequestDto.getEndDate());
        report.setSummary(summary);
        report.setDetails(organizationDetails);

        return report;
    }

    //Laporan statistik ibadah
    private StatisticReportDto generateIbadahReport(ReportRequestDto reportRequestDto, String generatedBy) {
        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("Message", "Ibadah Statistik Report");

        StatisticReportDto report = new StatisticReportDto();
        report.setReportType("IBADAH");
        report.setStartDate(reportRequestDto.getStartDate());
        report.setEndDate(reportRequestDto.getEndDate());
        report.setSummary(summary);
        report.setDetails(new ArrayList<>());

        return report;
    }

    // ==================== HELPER METHODS ====================

    private Map<String, Long> getGenderStatistics() {
        // Implementasi sesuai dengan field di Person model
        Map<String, Long> stats = new HashMap<>();
        stats.put("Laki-laki", 0L);
        stats.put("Perempuan", 0L);
        // TODO: Query actual data from repository
        return stats;
    }

    private Map<String, Long> getBaptismStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("Sudah Baptis", 0L);
        stats.put("Belum Baptis", 0L);
        stats.put("Sidi", 0L);
        // TODO: Query actual data from repository
        return stats;
    }

    private Map<String, Long> getSectorStatistics() {
        Map<String, Long> stats = new HashMap<>();
        // TODO: Query sectors from Person/Family repository
        return stats;
    }

    private Map<String, Long> getAgeGroupStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("0-12 (Anak)", 0L);
        stats.put("13-17 (Remaja)", 0L);
        stats.put("18-35 (Pemuda)", 0L);
        stats.put("36-59 (Dewasa)", 0L);
        stats.put("60+ (Lansia)", 0L);
        // TODO: Calculate from birth dates in Person repository
        return stats;
    }

    private List<Map<String, Object>> getJemaatDetails(ReportRequestDto request) {
        List<Map<String, Object>> details = new ArrayList<>();
        // TODO: Add detailed jemaat data if needed
        return details;
    }

    private double calculateTotalByType(LocalDateTime start, LocalDateTime end, String type) {
        List<Transaction> transactions = transactionRepositroy.findByTypeAndCreatedAtBetween(
                type.equals("Masuk") ?
                        com.GKPS.Model.Enum.TransactionType.Masuk :
                        com.GKPS.Model.Enum.TransactionType.Keluar,
                start, end
        );

        return transactions.stream()
                .mapToDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                .sum();
    }

    private Map<String, Double> getTransactionsByCategory(LocalDateTime start, LocalDateTime end, String type) {
        List<Transaction> transactions = transactionRepositroy.findByTypeAndCreatedAtBetween(
                type.equals("Masuk") ?
                        com.GKPS.Model.Enum.TransactionType.Masuk :
                        com.GKPS.Model.Enum.TransactionType.Keluar,
                start, end
        );

        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getKategori() != null ? t.getKategori().name() : "Lainnya",
                        Collectors.summingDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                ));
    }

    private Map<String, Map<String, Double>> getMonthlyTrend(LocalDateTime start, LocalDateTime end) {
        List<Transaction> allTransactions = transactionRepositroy.findByCreatedAtBetween(start, end);

        return allTransactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCreatedAt().getMonth().toString(),
                        Collectors.groupingBy(
                                t -> t.getType().name(),
                                Collectors.summingDouble(t -> t.getAmount() != null ? t.getAmount() : 0.0)
                        )
                ));
    }

    private List<Map<String, Object>> getKeuanganDetails(ReportRequestDto request,
                                                         LocalDateTime start,
                                                         LocalDateTime end) {
        List<Map<String, Object>> details = new ArrayList<>();

        List<Transaction> transactions = transactionRepositroy.findByCreatedAtBetween(start, end);
        for (Transaction t : transactions) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("date", t.getCreatedAt().toLocalDate());
            detail.put("deskripsi", t.getDeskripsi());
            detail.put("category", t.getKategori() != null ? t.getKategori().name() : "N/A");
            detail.put("type", t.getType().name());
            detail.put("amount", t.getAmount());
            details.add(detail);
        }

        return details;
    }

    private Map<RoleType, Long> getRoleDistribution() {
        Map<RoleType, Long> stats = new HashMap<>();
        // TODO: Query from OrganizationRepository
        return stats;
    }

    private Map<String, Long> getSeksiDistribution() {
        Map<String, Long> stats = new HashMap<>();
        // TODO: Query from OrganizationRepository
        return stats;
    }

    private List<Map<String, Object>> getOrganisasiDetails(ReportRequestDto request) {
        List<Map<String, Object>> details = new ArrayList<>();
        // TODO: Add detailed organization data
        return details;
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "system";
    }

}
