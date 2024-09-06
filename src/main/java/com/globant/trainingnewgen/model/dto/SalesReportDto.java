package com.globant.trainingnewgen.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesReportDto {

    private List<SalesData> salesData;
    private List<String> mostSoldProducts;
    private List<String> leastSoldProducts;

}
