package com.goga74.elprices.service;

import com.goga74.elprices.dto.PriceEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CacheService {

    private List<PriceEntry> cachedPrices; // Данные кеша
    private LocalDateTime lastUpdated; // Время последнего обновления кеша

    // Получение данных из кеша
    public List<PriceEntry> getCachedPrices() {
        return cachedPrices;
    }

    // Установка новых данных в кеш
    public void updateCache(List<PriceEntry> prices) {
        this.cachedPrices = prices;
        this.lastUpdated = LocalDateTime.now();
    }

    // Проверка, нужно ли обновлять кеш
    public boolean isCacheValid() {
        return lastUpdated != null && lastUpdated.isAfter(LocalDateTime.now().minusHours(1));
    }

    // Получение времени последнего обновления кеша
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

}
