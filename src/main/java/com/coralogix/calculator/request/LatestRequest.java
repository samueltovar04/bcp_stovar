package com.coralogix.calculator.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LatestRequest {
    private String base;
    private LocalDate date;
    private boolean success;
    private Long timestamp;
    private Map<String, Double> rates;

}

