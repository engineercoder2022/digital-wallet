package com.inghubs.wallet.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiErrorResponseExample {

  public static final String INTERNAL_ERROR = "{\n" +
      "    \"message\": \"" + ErrorMessage.INTERNAL_SERVER + "\"\n" +
      "}\n";
  public static final String SERVICE_UNAVAILABLE = "{\n" +
      "    \"message\": \"" + ErrorMessage.SERVICE_UNAVAILABLE + "\"\n" +
      "}\n";

  public static final String NOT_FOUND = "{\n"
      + "\t\"message\": \"" + ErrorMessage.BAD_REQUEST + "\",\n"
      + "\t\"details\": [\n"
      + "\t\t{\n"
      + "\t\t\t\"errorName\": \"currency\",\n"
      + "\t\t\t\"errorReason\": \"must be TRY|USD|EUR.\"\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";
}
