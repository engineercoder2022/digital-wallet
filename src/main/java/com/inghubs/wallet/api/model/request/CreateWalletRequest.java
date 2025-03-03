package com.inghubs.wallet.api.model.request;

import com.inghubs.wallet.constants.ApiConstraint.Reason;
import com.inghubs.wallet.constants.ApiConstraint.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record CreateWalletRequest (
  @NotBlank
  @Schema(example = "sample-wallet")
  String walletName,

  @NotBlank
  @Schema(example = "EUR", description = "TRY|USD|EUR")
  @Pattern(regexp = Validation.CURRENCY, message = Reason.CURRENCY)
  String currency,

  @Schema(description = "wallet activation for shopping")
  boolean activateForShopping,

  @Schema(description = "wallet activation for withdraw")
  boolean activateForWithdraw,

  @Schema(description = "wallet owner customer")
  Long customerId
){}
