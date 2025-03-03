package com.inghubs.wallet.util;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.inghubs.wallet.api.model.request.FieldInfo;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FieldLocator {

  public static FieldInfo locate(final List<Reference> references) {
    final StringBuilder fieldLocationBuilder = new StringBuilder();
    String fieldName = StringUtils.EMPTY;
    for (Reference reference : references) {
      fieldName = reference.getFieldName();
      if (isCollectionReference(reference)) {
        appendCollectionIndex(reference, fieldLocationBuilder);
      } else {
        appendCurrentFieldLocation(reference, fieldLocationBuilder);
      }
    }

    return new FieldInfo(fieldName, fieldLocationBuilder.toString());
  }

  private static boolean isCollectionReference(Reference reference) {
    return Objects.isNull(reference.getFieldName()) && reference.getIndex() >= 0;
  }

  private static void appendCollectionIndex(final Reference reference,
      final StringBuilder locationBuilder) {
    locationBuilder.append('[').append(reference.getIndex()).append(']');
  }

  private static void appendCurrentFieldLocation(final Reference reference,
      final StringBuilder fieldLocationBuilder) {
    if (isNestedField(fieldLocationBuilder)) {
      fieldLocationBuilder.append('.');
    }

    fieldLocationBuilder.append(reference.getFieldName());
  }

  private static boolean isNestedField(StringBuilder fieldLocationBuilder) {
    return StringUtils.isNotBlank(fieldLocationBuilder);
  }
}
