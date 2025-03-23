package com.goga74.elprices.service;

import com.goga74.elprices.dto.PriceEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceEnrichmentServiceTest {

	private final PriceEnrichmentService priceEnrichmentService = new PriceEnrichmentService();

	@Test
	void testTimezoneProperty() {
		String res = priceEnrichmentService.getTimeZone().toString();
		assertThat(res).isEqualTo("Europe/Tallinn");
	}

	@Test
	void testTimezoneConfiguration() {
		PriceEntry entry = new PriceEntry();
		entry.setTimestamp("1742565600"); // Unix timestamp (20:00 UTC on 23 March 2025)
		entry.setPrice(50.0);

		List<PriceEntry> prices = List.of(entry);

		List<PriceEntry> enrichedPrices = priceEnrichmentService.enrichPrices(prices, true);

		assertThat(enrichedPrices.get(0).getInterval()).isEqualTo("16:00 - 17:00");
	}

	@Test
	void testEnrichPricesWithHighlightCurrentInterval() {
		PriceEntry entry1 = new PriceEntry();
		entry1.setTimestamp("1742562000"); // 25 Jul 2025 13:00:00 UTC
		entry1.setPrice(50.0);

		PriceEntry entry2 = new PriceEntry();
		entry2.setTimestamp("1742565600"); // 17:00:00 26 Jul 2025
		entry2.setPrice(45.0); // min price

		List<PriceEntry> entries = Arrays.asList(entry1, entry2);

		List<PriceEntry> enrichedEntries = priceEnrichmentService.enrichPrices(entries, true);

		assertThat(enrichedEntries).hasSize(2);

		assertThat(enrichedEntries.get(0).getInterval()).isEqualTo("15:00 - 16:00");
		assertThat(enrichedEntries.get(0).isMinPrice()).isFalse();
		assertThat(enrichedEntries.get(0).isCurrentInterval()).isFalse();

		// element with min price
		assertThat(enrichedEntries.get(1).getInterval()).isEqualTo("16:00 - 17:00");
		assertThat(enrichedEntries.get(1).isMinPrice()).isTrue();
		assertThat(enrichedEntries.get(1).isCurrentInterval()).isFalse();
	}
}
