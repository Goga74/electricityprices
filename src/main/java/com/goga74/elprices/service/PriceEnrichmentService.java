package com.goga74.elprices.service;

import com.goga74.elprices.dto.PriceEntry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceEnrichmentService {
	
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	
	public List<PriceEntry> enrichPrices(List<PriceEntry> prices, boolean highlightCurrentInterval) {
		double minPrice = prices.stream()
				.mapToDouble(PriceEntry::getPrice)
				.min()
				.orElse(Double.MAX_VALUE);
		
		LocalTime currentHour = highlightCurrentInterval ? LocalTime.now() : null;
		
		return prices.stream()
				.map(price -> {
					try {
						// Преобразуем Unix timestamp в LocalDateTime
						long unixTimestamp = Long.parseLong(price.getTimestamp());
						LocalDateTime dateTime = Instant.ofEpochSecond(unixTimestamp)
								.atZone(ZoneId.systemDefault())
								.toLocalDateTime();
						
						// Устанавливаем интервал времени
						LocalTime time = dateTime.toLocalTime();
						price.setInterval(time.format(TIME_FORMATTER) + " - " + time.plusHours(1).format(TIME_FORMATTER));

						double formattedPrice = price.getPrice();
						price.setPrice(formattedPrice);
						
						// Устанавливаем флаги для минимальной цены и текущего интервала
						price.setMinPrice(price.getPrice() == minPrice);
						price.setCurrentInterval(highlightCurrentInterval && currentHour.getHour() == time.getHour());
					} catch (NumberFormatException e) {
						// Логируем ошибку и устанавливаем значения по умолчанию
						System.err.println("Invalid timestamp format: " + price.getTimestamp());
						price.setInterval("Unknown");
						price.setMinPrice(false);
						price.setCurrentInterval(false);
					}
					return price;
				})
				.collect(Collectors.toList());
	}
}
