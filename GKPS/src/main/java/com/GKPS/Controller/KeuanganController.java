package com.GKPS.Controller;

import com.GKPS.DTO.KeuanganSummaryDto;
import com.GKPS.Model.Enum.TransactionType;
import com.GKPS.Model.Keuangan.Account;
import com.GKPS.Model.Keuangan.Transaction;
import com.GKPS.Service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/keuangan", "/api/admin/keuangan"})
public class KeuanganController {

    private KeuanganService keuanganService;

    public KeuanganController(KeuanganService keuanganService) {
        this.keuanganService = keuanganService;
    }

    @GetMapping("/uang-masuk")
    public ResponseEntity<List<KeuanganSummaryDto>> getUangMasuk(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(keuanganService.getUangMasukSummary(startDate, endDate));
    }

     @GetMapping("/uang-keluar")
    public ResponseEntity<List<KeuanganSummaryDto>> getUangKeluar(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(keuanganService.getUangKeluarSummary(startDate, endDate));
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
        double totalKeluar = uangKeluar.stream().mapToDouble(KeuanganSummaryDto::getTotalPengeluaran).sum();

        response.put("totalMasuk", totalMasuk);
        response.put("totalKeluar", totalKeluar);
        response.put("saldo", totalMasuk - totalKeluar);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(keuanganService.getAllAccounts());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        return keuanganService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/accounts")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(keuanganService.createAccount(account));
    }

    @PutMapping("/accounts/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Account> updateAccount(@PathVariable String id, @RequestBody Account account) {
        return ResponseEntity.ok(keuanganService.updateAccount(id, account));
    }

    @DeleteMapping("/accounts/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'SEKRETARIS', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        keuanganService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("transactions")
    public ResponseEntity<Map<String, Object>> getTransactions(@RequestParam(required = false) String accountId,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> response = new HashMap<>();

        if (accountId != null && !accountId.isBlank()) {
            List<Transaction> transactions = keuanganService.getTransactionsByAccountId(accountId);
            response.put("transactions", transactions);
            response.put("totalElements", transactions.size());
            response.put("totalPages", 1);
            response.put("currentPage", 0);
            return ResponseEntity.ok(response);
        }

        if (startDate != null || endDate != null) {
            LocalDateTime start = startDate != null ? startDate.atStartOfDay() : LocalDateTime.MIN;
            LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : LocalDateTime.MAX;

            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<Transaction> transactionsPage = keuanganService.getTransactionsByDateRange(start, end, pageable);

            response.put("content", transactionsPage.getContent());
            response.put("totalElements", transactionsPage.getTotalElements());
            response.put("totalPages", transactionsPage.getTotalPages());
            response.put("currentPage", transactionsPage.getNumber());
            response.put("size", transactionsPage.getSize());
            return ResponseEntity.ok(response);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Transaction> transactionsPage = keuanganService.getAllTransactions(pageable);
        response.put("content", transactionsPage.getContent());
        response.put("totalElements", transactionsPage.getTotalElements());
        response.put("totalPages", transactionsPage.getTotalPages());
        response.put("currentPage", transactionsPage.getNumber());
        response.put("size", transactionsPage.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        return keuanganService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/transactions")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(keuanganService.createTransaction(transaction));
    }

    @PostMapping("/transactions/income")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Transaction> createIncomeTransaction(@RequestBody Transaction transaction) {
        transaction.setType(TransactionType.Masuk);
        return ResponseEntity.ok(keuanganService.createTransaction(transaction));
    }

    @PostMapping("/transactions/expense")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Transaction> createExpenseTransaction(@RequestBody Transaction transaction) {
        transaction.setType(TransactionType.Keluar);
        return ResponseEntity.ok(keuanganService.createTransaction(transaction));
    }

    @PutMapping("/transactions/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(keuanganService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/transactions/{id}")
    @PreAuthorize("hasAnyRole('MAJELIS', 'BENDAHARA_SEKSI', 'BENDAHARA', 'ADMIN')")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        keuanganService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

}
