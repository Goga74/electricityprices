package com.goga74.elprices.util;

import com.goga74.elprices.dto.PriceEntry;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilTest
{
    @Test
    public void testConvertToJson()
    {
        // Arrange
        PriceEntry entry1 = new PriceEntry();
        entry1.setPrice(10.0);
        entry1.setTimestamp("2025-03-28T00:00:00Z");

        PriceEntry entry2 = new PriceEntry();
        entry2.setPrice(15.0);
        entry2.setTimestamp("2025-03-29T00:00:00Z");

        List<PriceEntry> priceEntries = Arrays.asList(entry1, entry2);

        // Act
        String jsonResult = JsonUtil.convertToJson(priceEntries);

        // Assert
        String expectedJson = "[{\"timestamp\":\"2025-03-28T00:00:00Z\",\"price\":10.0},{\"timestamp\":\"2025-03-29T00:00:00Z\",\"price\":15.0}]";
        assertThat(jsonResult).isEqualTo(expectedJson);
    }
}