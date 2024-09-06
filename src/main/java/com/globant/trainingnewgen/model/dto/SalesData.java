package com.globant.trainingnewgen.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesData {

    private String productName;
    private long unitsSold;
    private BigDecimal totalRevenue;


}
