package com.coralogix.calculator.services;

import com.coralogix.calculator.dao.ExchangeRateRepository;
import com.coralogix.calculator.helper.RecipeWebService;
import com.coralogix.calculator.helper.RestClient;
import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.request.LatestRequest;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ExchangeRateService {

    private final ExchangeRateRepository  repository;
    private final RestClient restClient;
    private final RecipeWebService webClient;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository repository,
                               RestClient restClient,
                               RecipeWebService webClient) {
        this.repository = repository;
        this.restClient = restClient;
        this.webClient = webClient;
    }

    public Mono<ExchangeRate> getExchangeRate(String originCurrency, String finalCurrency) {
        Optional<ExchangeRate>  exchangeRateOptional = repository
                .findByOriginCurrencyAndAndFinalCurrency(originCurrency,finalCurrency);
        if (exchangeRateOptional.isPresent())
            return Mono.just(exchangeRateOptional.get());

         return getApisLastest(originCurrency,finalCurrency);

    }

    public Flux<ExchangeRate> getAllExchangeRate(){
        return  Flux.fromIterable(repository.findAll());
    }

    private Mono<ExchangeRate> getApisLastest(String originCurrency, String finalCurrency) {
        String url = "/fixer/latest?base="+ originCurrency + "&symbols=" + finalCurrency;
        log.info("URl: {}",url);
        LatestRequest latestRequest = webClient.getWebClient()
                .get()
                .uri(url)
                .retrieve().bodyToMono(LatestRequest.class)
                .block();

        //LatestRequest request = restClient.getForEntity(LatestRequest.class, url);
        log.info("Get de la apis {}",latestRequest);
        ExchangeRate exchangeRate = repository.save(mapperExchangeRate(latestRequest,originCurrency,finalCurrency));
        return Mono.just(exchangeRate);
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
