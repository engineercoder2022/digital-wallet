package com.inghubs.wallet.api.model.request;

import lombok.Builder;

@Builder
public record CustomerRequest (
    String name,
    String surname
){




}
