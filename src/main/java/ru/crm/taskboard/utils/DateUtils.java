package ru.crm.taskboard.utils;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class DateUtils {

    private DateUtils() {

    }

    public static LocalDateTime toLocalDateTime(String stringDateTime, DateFormat format) throws IllegalArgumentException {
        if (Strings.isNullOrEmpty(stringDateTime) || Objects.isNull(format)) {
            throw new IllegalArgumentException("Не был передан один из обязательных параметров для конвертации!");
        }

        return LocalDateTime.parse(stringDateTime, DateTimeFormatter.ofPattern(format.getFormat()));
    }

    public static LocalDate toLocalDate(String stringDateTime, DateFormat format) throws IllegalArgumentException {
        if (Strings.isNullOrEmpty(stringDateTime) || Objects.isNull(format)) {
            throw new IllegalArgumentException("Не был передан один из обязательных параметров для конвертации!");
        }

        return LocalDate.parse(stringDateTime, DateTimeFormatter.ofPattern(format.getFormat()));
    }

    public enum DateFormat {

        DATE("yyyy-MM-dd"),
        DATE_TIME("yyyy-MM-dd'T'HH:mm:ss"),
        DATE_TIME_ISO_8601("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        private final String format;

        DateFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }
}
