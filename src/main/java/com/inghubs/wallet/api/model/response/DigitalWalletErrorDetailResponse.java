package com.inghubs.wallet.api.model.response;

import com.inghubs.wallet.constants.ApiConstraint.Name;
import com.inghubs.wallet.constants.ApiConstraint.Reason;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DigitalWalletErrorDetailResponse(
    @Schema(example = Name.CURRENCY)
    String errorName,
    @Schema(example = Reason.CURRENCY)
    String errorReason
) {

}
