package com.goga74.elprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ElPricesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElPricesApplication.class, args);
	}
}
