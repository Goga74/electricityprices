package com.goga74.elprices.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimestampValidatorTest {

    @Test
    void testValidTimestamp() {
        assertThat(TimestampValidator.isValid("1742565600")).isTrue(); // Валидный timestamp
    }

    @Test
    void testInvalidTimestamp() {
        assertThat(TimestampValidator.isValid("invalid")).isFalse(); // Невалидный формат
        assertThat(TimestampValidator.isValid("-1234567890")).isFalse(); // Отрицательный timestamp
        assertThat(TimestampValidator.isValid("")).isFalse(); // Пустая строка
        assertThat(TimestampValidator.isValid(null)).isFalse(); // null значение
    }

    @Test
    void testFutureTimestamp() {
        long futureTimestamp = System.currentTimeMillis() / 1000 + 100000; // Будущее время
        assertThat(TimestampValidator.isValid(String.valueOf(futureTimestamp))).isFalse();
    }
}
