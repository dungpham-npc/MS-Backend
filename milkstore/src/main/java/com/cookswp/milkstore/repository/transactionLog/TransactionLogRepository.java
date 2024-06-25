package com.cookswp.milkstore.repository.transactionLog;


import com.cookswp.milkstore.pojo.entities.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
