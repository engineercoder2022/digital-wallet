package com.inghubs.wallet.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessage {

  public static final String GENERAL_ERROR_MESSAGE = "Request failed with exception";
  public static final String BAD_REQUEST = "Request parameters didn't validate.";
  public static final String NOT_FOUND = "A specified resource is not found.";
  public static final String INTERNAL_SERVER = "Internal server error occurred.";
  public static final String SERVICE_UNAVAILABLE = "Service is unavailable. Please try again.";
  public static final String CREATE_DIGITAL_WALLET_ERROR_MESSAGE = "Error occurred while creating digital wallet.";
  public static final String SERVICE_ERROR_MESSAGE = "Service request received unexpected error";
}
