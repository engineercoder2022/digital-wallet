package com.inghubs.wallet.exception;

public class UnexpectedProcessingException extends DigitalWalletApiException {

  public UnexpectedProcessingException(String messageTemplate, Object... params) {
    super(messageTemplate, params);
  }
}
