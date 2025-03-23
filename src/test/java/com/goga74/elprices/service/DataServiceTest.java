package com.goga74.elprices.service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

class DataServiceTest {
	
	private final DateService dateService = new DateService();
	
	@Test
	void testFormatDate() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2025-03-21T00:00:00Z");
		String formattedDate = dateService.formatDate(dateTime);
		Assertions.assertThat(formattedDate).isEqualTo("2025-03-21T00:00:00Z");
	}
	
	@Test
	void testGetStartOfDay() {
		ZonedDateTime dateTime = ZonedDateTime.parse("2025-03-21T10:15:30Z");
		ZonedDateTime startOfDay = dateService.getStartOfDay(dateTime);
		Assertions.assertThat(startOfDay).isEqualTo(ZonedDateTime.parse("2025-03-21T00:00:00Z"));
	}
	
	@Test
	void testGetEndOfDay() {
		ZonedDateTime startOfDay = ZonedDateTime.parse("2025-03-21T00:00:00Z");
		ZonedDateTime endOfDay = dateService.getEndOfDay(startOfDay);
		Assertions.assertThat(endOfDay).isEqualTo(ZonedDateTime.parse("2025-03-22T00:00:00Z"));
	}
}
