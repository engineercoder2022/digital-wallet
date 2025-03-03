package com.inghubs.wallet.constants;

import java.util.Arrays;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public enum ApiConstraint {
  CURRENCY(Name.CURRENCY, Reason.CURRENCY);

  private String name;
  private String reason;

  ApiConstraint(String name, String reason){
    this.name =name;
    this.reason = reason;
  }

  public static Optional<ApiConstraint> fromName(final String name) {
    return Arrays.stream(ApiConstraint.values())
        .filter(apiConstraint -> apiConstraint.name.equals(name)).findFirst();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Name {

    public static final String WALLET_NAME = "walletName";
    public static final String CURRENCY = "currency";
    public static final String ACTIVATE_FOR_SHOPPING = "activateForShopping";
    public static final String ACTIVATE_FOR_WITHDRAW = "activateForWithdraw";
    public static final String CUSTOMER_ID_PARAM = "customerId";
    public static final String WALLET_ID_PARAM = "walletId";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Reason {

    public static final String CURRENCY = "must be TRY, USD or EUR.";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Validation {

    public static final String CURRENCY = "TRY|USD|EUR";


  }
}
