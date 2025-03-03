package com.inghubs.wallet.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.inghubs.wallet.constants.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_EMPTY)
public record DigitalWalletErrorResponse(

    @Schema(example = ErrorMessage.BAD_REQUEST)
    String message,
    @Schema
    List<DigitalWalletErrorDetailResponse> details
) {

}
