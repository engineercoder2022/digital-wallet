package com.inghubs.wallet.repository;

import com.inghubs.wallet.repository.entity.Customer;
import com.inghubs.wallet.repository.entity.Wallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DigitalWalletRepository extends JpaRepository<Wallet, Long> {

  List<Wallet> findAllByCustomer(Customer customer);


}
