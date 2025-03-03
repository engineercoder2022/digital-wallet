package com.inghubs.wallet.util;

import static com.inghubs.wallet.constants.ErrorMessage.GENERAL_ERROR_MESSAGE;
import static java.util.Objects.isNull;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.inghubs.wallet.api.model.request.FieldInfo;
import com.inghubs.wallet.api.model.response.DigitalWalletErrorDetailResponse;
import com.inghubs.wallet.constants.ApiConstraint;
import com.inghubs.wallet.exception.DigitalWalletApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorUtil {

  private static final String DEFAULT_INVALID_PARAMETER_REASON = "must be validated.";

  public static List<DigitalWalletErrorDetailResponse> handleErrorDetails(
      MethodArgumentNotValidException e) {
    final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    return fieldErrors.stream()
        .map(fieldError -> new DigitalWalletErrorDetailResponse(
            fieldError.getField(), fieldError.getDefaultMessage())).toList();
  }

  public static List<DigitalWalletErrorDetailResponse> handleErrorDetails(
      InvalidFormatException invalidFormatException) {
    final List<Reference> references = invalidFormatException.getPath();
    final FieldInfo fieldInfo = FieldLocator.locate(references);
    final String reason = getErrorReasonFromConstraintName(fieldInfo.name());
    return List.of(new DigitalWalletErrorDetailResponse(fieldInfo.location(), reason));
  }

  public static List<DigitalWalletErrorDetailResponse> handleErrorDetails(
      final ConstraintViolationException violationException) {
    final Set<ConstraintViolation<?>> constraintViolations = violationException.getConstraintViolations();
    return constraintViolations.stream()
        .map(ErrorUtil::mapConstraintViolationToErrorDetail)
        .toList();
  }

  public static List<DigitalWalletErrorDetailResponse> handleErrorDetails(
      final MethodArgumentTypeMismatchException mismatchException) {
    final String constraintName = mismatchException.getName();
    return Collections.singletonList(createErrorDetailFromConstraintName(constraintName));
  }

  private static DigitalWalletErrorDetailResponse mapConstraintViolationToErrorDetail(
      final ConstraintViolation<?> violation) {
    final String constraintName =  violation.getPropertyPath().iterator().next().getName();
    return createErrorDetailFromConstraintName(constraintName);
  }

  private static DigitalWalletErrorDetailResponse createErrorDetailFromConstraintName(
      String constraintName) {
    final String reason = getErrorReasonFromConstraintName(constraintName);
    return new DigitalWalletErrorDetailResponse(constraintName, reason);
  }

  private static String getErrorReasonFromConstraintName(String constraintName) {
    final Optional<ApiConstraint> constraintOpt = ApiConstraint.fromName(constraintName);
    return constraintOpt
        .map(ApiConstraint::getReason)
        .orElse(DEFAULT_INVALID_PARAMETER_REASON);
  }

  public static List<DigitalWalletErrorDetailResponse> handleErrorDetails(
      DigitalWalletApiException e) {
    final String errorMessage =
        isNull(e.getMessage()) ? GENERAL_ERROR_MESSAGE : e.getMessage();
    final DigitalWalletErrorDetailResponse response =
        new DigitalWalletErrorDetailResponse(e.getErrorStatus().toString(), errorMessage);
    return List.of(response);
  }
}
