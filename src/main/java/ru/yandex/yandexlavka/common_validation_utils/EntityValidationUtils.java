package ru.yandex.yandexlavka.common_validation_utils;

public class EntityValidationUtils {

    public static boolean isEntityValid(StringBuilder sb,
                                        String entityName,
                                        int index,
                                        StringBuilder errorStringBuilder) {
        if (errorStringBuilder.length() != 0) {
            sb.append(entityName)
                    .append(" ")
                    .append(index + 1)
                    .append(". ")
                    .append(errorStringBuilder)
                    .append("| ");
        }

        return errorStringBuilder.length() == 0;
    }
}
