package com.inghubs.wallet.repository;

import com.inghubs.wallet.repository.entity.Customer;
import com.inghubs.wallet.repository.entity.Transaction;
import com.inghubs.wallet.repository.entity.Wallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByWallet(Wallet wallet);
}
