package com.globant.trainingnewgen.model.dto;

import java.math.BigDecimal;

public interface SalesDataProjection {
    String getFantasyName();
    Long getTotalQuantity();
    BigDecimal getTotalRevenue();
}