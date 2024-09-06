package com.globant.trainingnewgen.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SalesData(
        String productName,
        long unitsSold,
        BigDecimal totalRevenue
) {}
