package ru.crm.taskboard.data.utils;

import java.util.Objects;
import java.util.function.Consumer;

public final class DataUtils {

    private DataUtils() {

    }

    public static <T> void checkAndSetValue(T oldValue, T newValue, Consumer<T> setter) {
        if (Objects.nonNull(newValue) && checkValuesInequality(oldValue, newValue)) {
            setter.accept(newValue);
        }
    }

    private static <T> boolean checkValuesInequality(T oldValue, T newValue) {
        return Objects.isNull(oldValue) || !oldValue.equals(newValue);
    }
}
