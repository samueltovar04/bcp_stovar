package com.coralogix.calculator.services;

import com.coralogix.calculator.dao.ExchangeRateRepository;
import com.coralogix.calculator.helper.RestClient;
import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.request.LatestRequest;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeRateService {

    private final ExchangeRateRepository  repository;
    private final RestClient restClient;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository repository,
                               RestClient restClient) {
        this.repository = repository;
        this.restClient = restClient;
    }

    public ExchangeRate getExchangeRate(String originCurrency, String finalCurrency) {
        return repository
                .findByOriginCurrencyAndAndFinalCurrency(originCurrency,finalCurrency)
                .orElseGet(() -> getApisLastest(originCurrency,finalCurrency));
    }

    public List<ExchangeRate> getAllExchangeRate(){
        return  repository.findAll();
    }

    private ExchangeRate getApisLastest(String originCurrency, String finalCurrency) {
        String url = "http://localhost:8081/fixer/latest?base="+ originCurrency + "&symbols=" + finalCurrency;
        log.info("URl: {}",url);
        LatestRequest request = restClient.getForEntity(LatestRequest.class, url);
        log.info("Get de la apis {}",request);
        return repository.save(mapperExchangeRate(request,originCurrency,finalCurrency));
    }

    private ExchangeRate mapperExchangeRate(LatestRequest request,String originCurrency, String finalCurrency) {
        return ExchangeRate.builder()
                .originCurrency(originCurrency)
                .finalCurrency(finalCurrency)
                .date(request.getDate().toString())
                .value(String.valueOf(request.getRates().get(finalCurrency)))
                .build();
    }
}
