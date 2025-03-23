package com.goga74.elprices.service;

import com.goga74.elprices.dto.ApiResponse;
import com.goga74.elprices.dto.PriceEntry;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final CacheService cacheService;

	public ElPriceService(DateService dateService, PriceEnrichmentService priceEnrichmentService, CacheService cacheService)
	{
		this.dateService = dateService;
		this.priceEnrichmentService = priceEnrichmentService;
		this.cacheService = cacheService;
	}

	public List<PriceEntry> getTodayPrices() {
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
			return priceEnrichmentService.enrichPrices(response.getData().getEe(), true); // Подсвечиваем текущий интервал
		} else {
			throw new RuntimeException("Failed to fetch data from API");
		}
	}
	
	public List<PriceEntry> getTomorrowPrices() {
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
			return priceEnrichmentService.enrichPrices(response.getData().getEe(), false); // Не подсвечиваем текущий интервал
		} else {
			throw new RuntimeException("Failed to fetch data from API");
		}
	}
}
