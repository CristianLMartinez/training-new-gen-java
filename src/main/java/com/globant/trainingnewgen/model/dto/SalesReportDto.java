package com.globant.trainingnewgen.model.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record SalesReportDto(
         List<SalesData> salesData,
         List<String> mostSoldProducts,
         List<String> leastSoldProducts
) {}
