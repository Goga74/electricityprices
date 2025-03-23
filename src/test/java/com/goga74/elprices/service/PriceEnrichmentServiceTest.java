package com.goga74.elprices.service;

import com.goga74.elprices.dto.PriceEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceEnrichmentServiceTest {
	
	private final PriceEnrichmentService priceEnrichmentService = new PriceEnrichmentService();
	
	@Test
	void testEnrichPricesWithHighlightCurrentInterval() {
		// Arrange: создаём тестовые данные
		PriceEntry entry1 = new PriceEntry();
		entry1.setTimestamp("1742562000"); // Unix timestamp
		entry1.setPrice(50.0);
		
		PriceEntry entry2 = new PriceEntry();
		entry2.setTimestamp("1742565600"); // Unix timestamp
		entry2.setPrice(45.0); // Минимальная цена
		
		List<PriceEntry> entries = Arrays.asList(entry1, entry2);
		
		// Act: вызываем метод enrichPrices с подсветкой текущего интервала
		List<PriceEntry> enrichedEntries = priceEnrichmentService.enrichPrices(entries, true);
		
		// Assert: проверяем результаты
		assertThat(enrichedEntries).hasSize(2);
		
		// Проверяем первый элемент
		assertThat(enrichedEntries.get(0).getInterval()).isEqualTo("16:00 - 17:00");
		assertThat(enrichedEntries.get(0).isMinPrice()).isFalse();
		assertThat(enrichedEntries.get(0).isCurrentInterval()).isFalse(); // Зависит от текущего времени
		
		// Проверяем второй элемент (с минимальной ценой)
		assertThat(enrichedEntries.get(1).getInterval()).isEqualTo("17:00 - 18:00");
		assertThat(enrichedEntries.get(1).isMinPrice()).isTrue();
		assertThat(enrichedEntries.get(1).isCurrentInterval()).isFalse(); // Зависит от текущего времени
	}
	
	@Test
	void testEnrichPricesWithoutHighlightCurrentInterval() {
		// Arrange: создаём тестовые данные
		PriceEntry entry1 = new PriceEntry();
		entry1.setTimestamp("1742562000"); // Unix timestamp
		entry1.setPrice(50.0);
		
		PriceEntry entry2 = new PriceEntry();
		entry2.setTimestamp("1742565600"); // Unix timestamp
		entry2.setPrice(45.0); // Минимальная цена
		
		List<PriceEntry> entries = Arrays.asList(entry1, entry2);
		
		// Act: вызываем метод enrichPrices без подсветки текущего интервала
		List<PriceEntry> enrichedEntries = priceEnrichmentService.enrichPrices(entries, false);
		
		// Assert: проверяем результаты
		assertThat(enrichedEntries).hasSize(2);
		
		// Проверяем первый элемент
		assertThat(enrichedEntries.get(0).getInterval()).isEqualTo("16:00 - 17:00");
		assertThat(enrichedEntries.get(0).isMinPrice()).isFalse();
		assertThat(enrichedEntries.get(0).isCurrentInterval()).isFalse(); // Подсветка отключена
		
		// Проверяем второй элемент (с минимальной ценой)
		assertThat(enrichedEntries.get(1).getInterval()).isEqualTo("17:00 - 18:00");
		assertThat(enrichedEntries.get(1).isMinPrice()).isTrue();
		assertThat(enrichedEntries.get(1).isCurrentInterval()).isFalse(); // Подсветка отключена
	}
	
	@Test
	void testInvalidTimestamp() {
		// Arrange: создаём тестовые данные с некорректным timestamp
		PriceEntry invalidEntry = new PriceEntry();
		invalidEntry.setTimestamp("invalid"); // Некорректный формат
		
		List<PriceEntry> entries = Arrays.asList(invalidEntry);
		
		// Act: вызываем метод enrichPrices
		List<PriceEntry> enrichedEntries = priceEnrichmentService.enrichPrices(entries, true);
		
		// Assert: проверяем результаты
		assertThat(enrichedEntries).hasSize(1);
		
		PriceEntry enrichedEntry = enrichedEntries.get(0);
		
		assertThat(enrichedEntry.getInterval()).isEqualTo("Unknown");
		assertThat(enrichedEntry.isMinPrice()).isFalse();
		assertThat(enrichedEntry.isCurrentInterval()).isFalse();
	}
}
