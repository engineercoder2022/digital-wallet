package com.inghubs.wallet.exception.handler;

import static com.inghubs.wallet.constants.ErrorMessage.GENERAL_ERROR_MESSAGE;
import static com.inghubs.wallet.constants.ErrorMessage.SERVICE_ERROR_MESSAGE;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.inghubs.wallet.api.model.response.DigitalWalletErrorDetailResponse;
import com.inghubs.wallet.api.model.response.DigitalWalletErrorResponse;
import com.inghubs.wallet.constants.ErrorMessage;
import com.inghubs.wallet.exception.DigitalWalletApiException;
import com.inghubs.wallet.exception.UnexpectedProcessingException;
import com.inghubs.wallet.util.ErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class DigitalWalletApiExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error(GENERAL_ERROR_MESSAGE, e);
    final List<DigitalWalletErrorDetailResponse> errorDetails = ErrorUtil.handleErrorDetails(e);
    return ResponseEntity.badRequest()
        .body(new DigitalWalletErrorResponse(ErrorMessage.BAD_REQUEST, errorDetails));
  }

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    log.error(GENERAL_ERROR_MESSAGE, e);
    final Throwable rootCause = e.getCause();
    final List<DigitalWalletErrorDetailResponse> details =
        (rootCause instanceof InvalidFormatException invalidFormatException)
            ? ErrorUtil.handleErrorDetails(invalidFormatException)
            : List.of();

    return ResponseEntity.badRequest()
        .body(new DigitalWalletErrorResponse(ErrorMessage.BAD_REQUEST, details));
  }

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e) {
    log.error(GENERAL_ERROR_MESSAGE, e);
    final List<DigitalWalletErrorDetailResponse> errorDetails = ErrorUtil.handleErrorDetails(e);
    return ResponseEntity.badRequest()
        .body(new DigitalWalletErrorResponse(ErrorMessage.BAD_REQUEST, errorDetails));
  }

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.error(GENERAL_ERROR_MESSAGE, e);
    final List<DigitalWalletErrorDetailResponse> errorDetails = ErrorUtil.handleErrorDetails(e);
    return ResponseEntity.badRequest()
        .body(new DigitalWalletErrorResponse(ErrorMessage.BAD_REQUEST, errorDetails));
  }

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleDisruptionApiServiceException(
      final DigitalWalletApiException serviceException, final HttpServletRequest request) {
    log.error(SERVICE_ERROR_MESSAGE, serviceException);
    return getDigitalWalletErrorResponse(serviceException);
  }

  @ExceptionHandler
  public ResponseEntity<DigitalWalletErrorResponse> handleGeneralException(
      final Exception exception) {
    log.error(GENERAL_ERROR_MESSAGE, exception);
    UnexpectedProcessingException unexpectedException
        = new UnexpectedProcessingException(exception.toString());
    return getDigitalWalletErrorResponse(unexpectedException);
  }

  private ResponseEntity<DigitalWalletErrorResponse> getDigitalWalletErrorResponse(
      DigitalWalletApiException e) {
    final List<DigitalWalletErrorDetailResponse> errorDetails = ErrorUtil.handleErrorDetails(e);
    return ResponseEntity.status(e.getErrorStatus())
        .body(new DigitalWalletErrorResponse(e.getErrorStatus().toString(),
            errorDetails));
  }
}
