package com.goga74.elprices.service;

import com.goga74.elprices.cache.CacheService;
import com.goga74.elprices.dto.ApiResponse;
import com.goga74.elprices.dto.PriceEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ElPriceService {
	
	@Value("${elering.api.url}")
	private String apiUrl;
	
	private final DateService dateService;
	
	private final PriceEnrichmentService priceEnrichmentService;

	private final CacheService cacheService = new CacheService(7200);

	public ElPriceService(DateService dateService, PriceEnrichmentService priceEnrichmentService)
	{
		this.dateService = dateService;
		this.priceEnrichmentService = priceEnrichmentService;
	}

	public List<PriceEntry> getTodayPrices() {
		List<PriceEntry> cachedPrices = cacheService.getCachedPrices("today");
		if (cachedPrices != null) {
			return cachedPrices; // Возвращаем данные из кеша, если они актуальны
		}

		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		ZonedDateTime start = dateService.getStartOfDay(now);
		ZonedDateTime end = dateService.getEndOfDay(start);
		
		String formattedStart = dateService.formatDate(start);
		String formattedEnd = dateService.formatDate(end);
		
		RestTemplate restTemplate = new RestTemplate();
		ApiResponse response = restTemplate.getForObject(
				apiUrl + "?start=" + formattedStart + "&end=" + formattedEnd,
				ApiResponse.class
		);

		if (response != null && response.getData() != null) {
			List<PriceEntry> enrichedPrices =
					priceEnrichmentService.enrichPrices(response.getData().getEe(), true);
			cacheService.updateCache("today", enrichedPrices); // Обновляем кеш новыми данными
			return enrichedPrices;
		} else {
			throw new RuntimeException("Failed to fetch data from API");
		}
	}
	
	public List<PriceEntry> getTomorrowPrices() {
		List<PriceEntry> cachedPrices = cacheService.getCachedPrices("tomorrow");
		if (cachedPrices != null) {
			return cachedPrices; // Возвращаем данные из кеша, если они актуальны
		}

		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).plusDays(1);
		ZonedDateTime start = dateService.getStartOfDay(now);
		ZonedDateTime end = dateService.getEndOfDay(start);
		
		String formattedStart = dateService.formatDate(start);
		String formattedEnd = dateService.formatDate(end);
		
		RestTemplate restTemplate = new RestTemplate();
		ApiResponse response = restTemplate.getForObject(
				apiUrl + "?start=" + formattedStart + "&end=" + formattedEnd,
				ApiResponse.class
		);

		if (response != null && response.getData() != null) {
			List<PriceEntry> enrichedPrices =
					priceEnrichmentService.enrichPrices(response.getData().getEe(), false);
			cacheService.updateCache("tomorrow", enrichedPrices); // Обновляем кеш новыми данными
			return enrichedPrices;
		} else {
			throw new RuntimeException("Failed to fetch data from API");
		}
	}
}
