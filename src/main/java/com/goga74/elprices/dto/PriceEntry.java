package com.goga74.elprices.dto;

public class PriceEntry {
	private String timestamp;
	private Double price;
	private String sPrice;
	private String interval; // Интервал времени (например, "01:00 - 02:00")
	private boolean isMinPrice; // Флаг минимальной цены
	private boolean isCurrentInterval; // Флаг текущего интервала
	
	// Геттеры и сеттеры
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
		// Форматируем цену и сохраняем её в sPrice
		this.sPrice = String.format("%.2f", price);
	}
	
	public String getInterval() {
		return interval;
	}
	
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
	public boolean isMinPrice() {
		return isMinPrice;
	}
	
	public void setMinPrice(boolean minPrice) {
		isMinPrice = minPrice;
	}
	
	public boolean isCurrentInterval() {
		return isCurrentInterval;
	}
	
	public void setCurrentInterval(boolean currentInterval) {
		isCurrentInterval = currentInterval;
	}

	// Метод для Thymeleaf (или другого HTML-шаблонизатора)
	public String getPriceAsString() {
		return sPrice != null ? sPrice : String.format("%.2f", price);
	}
}
