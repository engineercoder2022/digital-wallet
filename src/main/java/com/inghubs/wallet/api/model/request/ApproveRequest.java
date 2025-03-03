package com.inghubs.wallet.api.model.request;

import com.inghubs.wallet.repository.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ApproveRequest(
    @Schema(example = "1", description = "transaction id for approve/deny operation")
    Long transactionId,
    @Schema(example = "APPROVED", description = "Transaction status")
    Status status
){}
