package com.inghubs.wallet.api.model.response;

import com.inghubs.wallet.constants.ApiConstraint.Reason;
import com.inghubs.wallet.constants.ApiConstraint.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record WalletsResponse(
    @Schema(example = "sample-wallet-name", description = "Customer wallet name")
    String walletName,

    @Pattern(regexp = Validation.CURRENCY, message = Reason.CURRENCY)
    @Schema(example = "USD", pattern = Validation.CURRENCY)
    String currency,

    @Schema(example = "34.3", description = "Balance")
    BigDecimal balance,

    @Schema(example = "34.3", description = "Usable balance")
    BigDecimal usableBalance,

    @Schema(example = "false")
    boolean activeForWithdraw,

    @Schema(example = "false")
    boolean activeForShopping
) {

}
