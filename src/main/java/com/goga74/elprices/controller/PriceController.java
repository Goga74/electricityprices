package com.goga74.elprices.controller;

import com.goga74.elprices.service.ElPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;

@Controller
public class PriceController {
	
	private final ElPriceService elPriceService;
	
	public PriceController(ElPriceService elPriceService) {this.elPriceService = elPriceService;}
	
	@GetMapping("/")
	public String today(Model model) {
		model.addAttribute("prices", elPriceService.getTodayPrices());
		model.addAttribute("date", ZonedDateTime.now().toLocalDate().toString()); // Текущая дата
		model.addAttribute("header", "Today " + ZonedDateTime.now().toLocalDate().toString()); // Today + текущая дата
		return "prices";
	}
	
	@GetMapping("/tomorrow")
	public String tomorrow(Model model) {
		model.addAttribute("prices", elPriceService.getTomorrowPrices());
		model.addAttribute("date", ZonedDateTime.now().plusDays(1).toLocalDate().toString()); // Завтрашняя дата
		model.addAttribute("header", "Tomorrow " + ZonedDateTime.now().plusDays(1).toLocalDate().toString()); // Tomorrow + завтрашняя дата
		return "prices";
	}
}
