package com.inghubs.wallet.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record WithdrawRequest(
    @Schema(description = "Amount to to withdraw")
    BigDecimal amount,
    @Schema(description = "Wallet id to make withdraw-pay from")
    String walletId,

    @Schema(description = "Destination")
        String iban
){}
