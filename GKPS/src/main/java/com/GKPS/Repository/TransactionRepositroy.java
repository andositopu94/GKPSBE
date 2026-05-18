package com.GKPS.Repository;

import com.GKPS.Model.Enum.ApprovalStatus;
import com.GKPS.Model.Enum.TransactionCategory;
import com.GKPS.Model.Enum.TransactionType;
import com.GKPS.Model.Keuangan.Approval;
import com.GKPS.Model.Keuangan.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepositroy extends MongoRepository<Transaction, String> {
    List<Transaction> findByAccountIdAndIsDeletedFalse(String accountId);

    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByApprovalStatusStatus(ApprovalStatus status);
    List<Transaction>findByApprovalStatus(Approval status);

    List<Transaction> findByAccountIdAndCreatedAtBetween(String accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction>findByType(TransactionType type);
    List<Transaction>findByAccountId(String accountId);
    List<Transaction>findByKategori(TransactionCategory kategori);
}

