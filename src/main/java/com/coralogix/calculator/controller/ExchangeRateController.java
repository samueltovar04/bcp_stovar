package com.coralogix.calculator.controller;

import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.services.ExchangeRateService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExchangeRateController {

    private final ExchangeRateService service;

    @Autowired
    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping("getExchengeRate")
    public ExchangeRate getExchengeRate(String originCurrency, String finalCurrency) {
            return service.getExchangeRate(originCurrency, finalCurrency);
    }

    @GetMapping("getAllExchengeRate")
    public List<ExchangeRate> getAllExchengeRate() {
        return service.getAllExchangeRate();
    }
}
