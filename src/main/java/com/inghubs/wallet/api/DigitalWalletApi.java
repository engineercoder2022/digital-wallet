package com.inghubs.wallet.api;

import com.inghubs.wallet.api.model.request.ApproveRequest;
import com.inghubs.wallet.api.model.request.CreateWalletRequest;
import com.inghubs.wallet.api.model.response.WalletsResponse;
import com.inghubs.wallet.api.model.response.DigitalWalletErrorResponse;
import com.inghubs.wallet.constants.ApiConstraint.Name;
import com.inghubs.wallet.constants.ApiErrorResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(name = "digital-wallet-api", description = "Digital Wallet API")
@RequestMapping("/api/v1/digital-wallet")
public interface DigitalWalletApi {

  @Operation(summary = "Creates digital wallet.")
  @ApiResponse(responseCode = "201", description = "Digital wallet created")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> createDigitalWallet(
      final @Valid @RequestBody CreateWalletRequest request);

  @Operation(summary = "Retrieves customer wallets.")
  @ApiResponse(responseCode = "200", description = "Flights are retrieved.")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "404", description = "A specified resource is not found.",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.NOT_FOUND)}))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @Parameter(name = Name.CUSTOMER_ID_PARAM, description = "CustomerId", in = ParameterIn.PATH, example = "1")
  @GetMapping(value = "/wallet/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<WalletsResponse>> getCustomerWallets(
      final @PathVariable(value = Name.CUSTOMER_ID_PARAM) Long customerId);

  @Operation(summary = "Makes the deposit operation.")
  @ApiResponse(responseCode = "200", description = "Deposit processed")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> deposit(
      final @RequestBody WalletBalanceRequest request);

  @Operation(summary = "List transactions for a given wallet")
  @ApiResponse(responseCode = "200", description = "Transactions are retrieved.")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "404", description = "A specified resource is not found.",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.NOT_FOUND)}))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @Parameter(name = Name.WALLET_ID_PARAM, description = "wallet id", in = ParameterIn.PATH, example = "1111-3432423-343dsfsdf-dsfsd")
  @GetMapping(value = "/transaction/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<TransactionsResponse>> getWalletTransactions(
      final @PathVariable(value = Name.WALLET_ID_PARAM) Long walletId);

  @Operation(summary = "Makes the withdraw operation.")
  @ApiResponse(responseCode = "200", description = "Withdraw processed")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> withdraw(
      final @RequestBody WalletBalanceRequest request);

  @Operation(summary = "Approve or deny a transaction with given details below.")
  @ApiResponse(responseCode = "200", description = "Approve/Deny processed")
  @ApiResponse(responseCode = "400", description = "Bad request",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.INTERNAL_ERROR)}))
  @ApiResponse(responseCode = "503", description = "Service Unavailable",
      content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = DigitalWalletErrorResponse.class),
          examples = {@ExampleObject(value = ApiErrorResponseExample.SERVICE_UNAVAILABLE)}))
  @PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> approve(
      final @RequestBody ApproveRequest request);
}
