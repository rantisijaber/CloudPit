package com.jaberrantisi.backendlambda.model;

import lombok.*;

import java.time.LocalDate;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class CostEntry {
    private LocalDate date;
    private String service;
    private Double amount;
}
