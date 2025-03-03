package com.inghubs.wallet.repository;

import com.inghubs.wallet.repository.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
