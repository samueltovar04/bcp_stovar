package com.coralogix.calculator.dao;

import com.coralogix.calculator.model.ExchangeRate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByOriginCurrencyAndAndFinalCurrency(String originCurrency, String finalCurrency);
}
