package com.inghubs.wallet.api;

import com.inghubs.wallet.api.model.request.CustomerRequest;
import com.inghubs.wallet.repository.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Validated
@Tag(name = "customer-api", description = "Customer API")
@RequestMapping("/api/v1/customer")
public interface CustomerApi {

  @Operation(summary = "Creates a new customer")
  @ApiResponse(responseCode = "201", description = "Digital wallet created")
  @GetMapping(value = "/createCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Long createCustomer(CustomerRequest customer);

  @Operation(summary = "Creates a new customer")
  @GetMapping(value = "/getCustomerList", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<Customer> listCustomers();




}
