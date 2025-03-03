package com.inghubs.wallet.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

public abstract class DigitalWalletApiException extends RuntimeException {

  protected DigitalWalletApiException(String messageTemplate, Object... params) {
    super(String.format(messageTemplate, params));
  }

  protected DigitalWalletApiException(Throwable cause, String messageTemplate, Object[] params) {
    super(String.format(messageTemplate, params), cause);
  }

  public HttpStatus getErrorStatus() {
    return INTERNAL_SERVER_ERROR;
  }
}
