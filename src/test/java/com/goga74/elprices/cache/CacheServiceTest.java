package com.goga74.elprices.cache;

import com.goga74.elprices.dto.PriceEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private final CacheService cacheService = new CacheService(1);


    @Test
    void testUpdateCacheAndRetrieveCachedPrices() {
        List<PriceEntry> mockPricesToday = Arrays.asList(
                new PriceEntry().setInterval("14:00").setPrice(1.0),
                new PriceEntry().setInterval("16:00").setPrice(2.0)
        );
        cacheService.updateCache("today", mockPricesToday);

        List<PriceEntry> cachedPrices = cacheService.getCachedPrices("today");
        assertNotNull(cachedPrices);
        assertEquals(mockPricesToday, cachedPrices);
    }

    @Test
    void testCacheExpirationAfterTimeout() throws InterruptedException {
        List<PriceEntry> mockPricesTomorrow = Arrays.asList(
                new PriceEntry().setInterval("14:00").setPrice(1.0),
                new PriceEntry().setInterval("15:00").setPrice(2.0)
        );
        cacheService.updateCache("tomorrow", mockPricesTomorrow);

        // Проверяем, что данные доступны сразу после обновления кеша
        List<PriceEntry> cachedPrices = cacheService.getCachedPrices("tomorrow");
        assertNotNull(cachedPrices);
        assertEquals(mockPricesTomorrow, cachedPrices);

        Thread.sleep(1200);

        // Проверяем, что кеш устарел и данные недоступны
        cachedPrices = cacheService.getCachedPrices("tomorrow");
        assertNull(cachedPrices);
    }

    @Test
    void testSeparateKeysForTodayAndTomorrow() {
        List<PriceEntry> mockPricesToday = Arrays.asList(
                new PriceEntry(),
                new PriceEntry()
        );
        List<PriceEntry> mockPricesTomorrow = Arrays.asList(
                new PriceEntry(),
                new PriceEntry()
        );

        // Обновляем кеш для разных ключей
        cacheService.updateCache("today", mockPricesToday);
        cacheService.updateCache("tomorrow", mockPricesTomorrow);

        // Проверяем данные в кеше для ключа "today"
        List<PriceEntry> cachedToday = cacheService.getCachedPrices("today");
        assertNotNull(cachedToday);
        assertEquals(mockPricesToday, cachedToday);

        // Проверяем данные в кеше для ключа "tomorrow"
        List<PriceEntry> cachedTomorrow = cacheService.getCachedPrices("tomorrow");
        assertNotNull(cachedTomorrow);
        assertEquals(mockPricesTomorrow, cachedTomorrow);
    }
}
