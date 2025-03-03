package com.inghubs.wallet.controller;

import com.inghubs.wallet.api.CustomerApi;
import com.inghubs.wallet.api.model.request.CustomerRequest;
import com.inghubs.wallet.repository.CustomerRepository;
import com.inghubs.wallet.repository.entity.Customer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

  private final CustomerRepository customerRepository;

  @Override
  public Long createCustomer(CustomerRequest request) {
    Customer customer = new Customer();
    customer.setName(request.name());
    customer.setSurname(request.surname());
    return customerRepository.save(customer).getId();
  }

  @Override
  public List<Customer> listCustomers() {
    return Streamable.of(customerRepository.findAll()).toList();
  }
}
