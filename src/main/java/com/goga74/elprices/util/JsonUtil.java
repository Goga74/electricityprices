package com.goga74.elprices.util;

import com.goga74.elprices.dto.PriceEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class JsonUtil
{
    public static String convertToJson(List<PriceEntry> priceEntries)
    {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(priceEntries);
    }
}