package com.GKPS.Controller;

import com.GKPS.DTO.KeuanganSummaryDto;
import com.GKPS.Model.Enum.TransactionType;
import com.GKPS.Model.Keuangan.Account;
import com.GKPS.Model.Keuangan.Transaction;
import com.GKPS.Service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
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
        double totalKeluar = uangKeluar.stream().mapToDouble(KeuanganSummaryDto::getTotalPemasukan).sum();

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
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam(required = false) String accountId,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (accountId != null && !accountId.isBlank()) {
            return ResponseEntity.ok(keuanganService.getTransactionsByAccountId(accountId));
        }
        if (startDate != null || endDate != null) {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(LocalTime.MAX);
            return ResponseEntity.ok(keuanganService.getTransactionsByDateRange(start, end));
        }
        return ResponseEntity.ok(keuanganService.getAllTransactions());
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
