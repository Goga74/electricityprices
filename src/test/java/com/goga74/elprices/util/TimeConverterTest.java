package com.goga74.elprices.util;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeConverterTest {

    /**
     * Параметризованный тест для проверки функции getTimeInTimezone.
     *
     * @param timestamp       Входной timestamp.
     * @param timezone        Смещение временной зоны (например, 2 для GMT+2).
     * @param expectedTime    Ожидаемое время в формате "HH:mm".
     */
    @ParameterizedTest
    @CsvSource({
            "1742565600, 2, 16:00", // 21 March 2025 14:00 UTC
            "1742757000, 2, 21:10", // Sunday, 23 March 2025 19:10
            "1753282800, 2, 17:00", // 23 July 2025, 15:00:00, summer time +1 hour
    })
    void testGetTimeInTimezone(int timestamp, int timezone, String expectedTime) {
        // Вызываем тестируемую функцию
        String actualTime = TimeConverter.getTimeInTimezone(timestamp, timezone);

        // Проверяем, что результат совпадает с ожидаемым значением
        assertEquals(expectedTime, actualTime);
    }
}