package com.goga74.elprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class ElPricesApplication
{
	private static final Logger logger = LogManager.getLogger(ElPricesApplication.class);

	public static void main(String[] args)
	{
		logger.info("Запуск приложения ElPrices...");
		SpringApplication.run(ElPricesApplication.class, args);
		logger.info("Приложение ElPrices успешно запущено.");
	}
}