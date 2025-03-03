package com.inghubs.wallet.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record WalletBalanceRequest(
    @Schema(description = "Amount to to deposit/withdraw")
    BigDecimal amount,
    @Schema(description = "Wallet id to make deposit into")
    Long walletId
){}
