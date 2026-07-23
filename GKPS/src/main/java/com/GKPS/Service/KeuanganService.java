package com.GKPS.Service;

import com.GKPS.DTO.ItemKeuanganDto;
import com.GKPS.DTO.KeuanganSummaryDto;
import com.GKPS.Model.Enum.TransactionType;
import com.GKPS.Model.Keuangan.Account;
import com.GKPS.Model.Keuangan.Transaction;
import com.GKPS.Repository.AccountRepository;
import com.GKPS.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeuanganService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final List<String> UANG_MASUK_CATEGORIES = Arrays.asList(
            "Persembahan Ibadah Utama", "Persembahan Ibadah Sektor", "Persembahan Ibadah Seksi Bapa",
            "Persembahan Ibadah Seksi Wanita", "Persembahan Ibadah Seksi Pemuda", "Persembahan Ibadah Seksi Sekolah Minggu",
            "Persembahan Ibadah Seksi lainnya", "Perpuluhan", "Donasi","Dana Usaha", "Dana Pembangunan", "Lainnya");
    private static final List<String> UANG_KELUAR_CATEGORIES = Arrays.asList(
            "Operasional Gereja", "Pembangunan", "Pemeliharaan Gedung", "Kegiatan Sosial",
            "Acara Ibadah Hari Besar", "Modal Usaha","Sumbangan Kematian","Sumbangan Pernikahan",
            "Sumbangan Baptis", "Sumbangan Angkat Sidi", "Gaji Majelis","Donasi ke Pendeta", "Lainnya");

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAllAccount(){
        return accountRepository.findByIsDeletedFalse();
    }

    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(String id, Account account) {
        account.setId(id);
        return accountRepository.save(account);
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }

    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountIdAndIsDeletedFalse(accountId);
    }

    public Page<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return transactionRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(String id, Transaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }

    //get summary uang masuk
    public List<KeuanganSummaryDto> getUangMasukSummary(LocalDate startDate, LocalDate endDate){
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionRepository.findByTypeAndCreatedAtBetween(TransactionType.Masuk, startDateTime, endDateTime);

        return groupTransactionsByDate(transactions, UANG_MASUK_CATEGORIES);
    }

    //get summary uang keluar
    public List<KeuanganSummaryDto> getUangKeluarSummary(LocalDate starDate, LocalDate endDate){
        LocalDateTime startDateTime = starDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionRepository.findByTypeAndCreatedAtBetween(TransactionType.Keluar, startDateTime, endDateTime);

        return groupTransactionsByDate(transactions, UANG_KELUAR_CATEGORIES);
    }

    private List<KeuanganSummaryDto> groupTransactionsByDate(List<Transaction> transactions, List<String> categories){
        //group by date
        Map<LocalDate, List<Transaction>> groupByDate = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getCreatedAt().toLocalDate()));

        List<KeuanganSummaryDto> summaries = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Transaction>> entry : groupByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Transaction> dayTransactions = entry.getValue();

            //create map of category to amount
            Map<String, Double> categoryAmounts = new LinkedHashMap<>();
            for (String category : categories) {
                categoryAmounts.put(category, 0.0);
            }

            //sum amount by category
            for (Transaction t : dayTransactions) {
                String kategori = t.getKategori() != null ? t.getKategori().name() : "Lainnya";
                String matchedCategory = findMatchingCategory(kategori, categories);

                if (matchedCategory != null) {
                    double currentAmount = categoryAmounts.getOrDefault(matchedCategory, 0.0);
                    categoryAmounts.put(matchedCategory, currentAmount + (t.getAmount() !=null ? t.getAmount() : 0.0));
                }
            }

            //konver ke ItemKeuanganDto
            List<ItemKeuanganDto> items = new ArrayList<>();
            double total = 0.0;
            for (Map.Entry<String, Double> catEntry : categoryAmounts.entrySet()) {
                items.add(new ItemKeuanganDto(catEntry.getKey(), catEntry.getValue(), ""));
                total += catEntry.getValue();
            }

            summaries.add(new KeuanganSummaryDto(date, items, total,0.0));
        }

        //sort by desc
        summaries.sort((a,b) -> b.getTanggal().compareTo(a.getTanggal()));

        return summaries;
    }

    private String findMatchingCategory(String kategori, List<String> categories) {
        String normalizedKategori = kategori.trim().toLowerCase();
        for (String category : categories) {
            String normalizedCategory = category.trim().toLowerCase();
            if (normalizedKategori.contains(normalizedCategory) || normalizedCategory.contains(normalizedKategori)) {
                return category; // return original category name
            }
        }

        for (String category : categories) {
            if (category.equalsIgnoreCase("lainnya")){
                return category; // return "Lainnya" if no other match found
            }
        }

        return null;
    }


}
