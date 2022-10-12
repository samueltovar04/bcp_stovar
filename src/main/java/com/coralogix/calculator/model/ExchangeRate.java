package com.coralogix.calculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Long id;

    private String originCurrency;
    private String finalCurrency;
    private String date;
    private String value;

}
