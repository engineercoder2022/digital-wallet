package com.inghubs.wallet.api.model.response;

import com.inghubs.wallet.constants.ApiConstraint.Validation;
import com.inghubs.wallet.repository.entity.Status;
import com.inghubs.wallet.repository.entity.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record TransactionsResponse(
    @Schema(example = "DEPOSIT|WITHDRAW")
    TransactionType transactionType,

    @Schema(example = "USD", pattern = Validation.CURRENCY)
    Status status,

    @Schema(example = "55.33", description = "Transaction amount")
    BigDecimal amount
) {

}
