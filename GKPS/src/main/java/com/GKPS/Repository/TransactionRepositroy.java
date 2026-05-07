package com.GKPS.Repository;

import com.GKPS.Model.Enum.ApprovalStatus;
import com.GKPS.Model.Enum.transactionCategory;
import com.GKPS.Model.Enum.transactionType;
import com.GKPS.Model.Keuangan.transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepositroy extends MongoRepository<transaction, String> {
    List<transaction> findByAccountAndIsDeleteFalse(String accountId);

    List<transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<transaction> findByApprovalStatusStatus(ApprovalStatus status);
    List<transaction>findByApproval_Status(String status);

    List<transaction> findByAccountIdAndCreatedAtBetween(String accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<transaction>findByType(transactionType type);
    List<transaction>findByAccountId(String accountId);
    List<transaction>findByKategori(transactionCategory kategori);
}

