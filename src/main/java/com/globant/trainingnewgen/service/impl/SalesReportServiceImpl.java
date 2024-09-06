package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.dto.SalesData;
import com.globant.trainingnewgen.model.dto.SalesDataProjection;
import com.globant.trainingnewgen.model.dto.SalesReportDto;
import com.globant.trainingnewgen.repository.OrderRepository;
import com.globant.trainingnewgen.service.SalesReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public SalesReportDto getSalesReport(String date1, String date2) {
        LocalDate startDate = parseDate(date1);
        LocalDate endDate = parseDate(date2);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("The start date must be before the end date.");
        }

        List<SalesDataProjection> salesDataProjections = orderRepository.findSalesDataBetweenDates(startDate, endDate);

        if (salesDataProjections.isEmpty()) {
            return SalesReportDto.builder()
                    .salesData(Collections.emptyList())
                    .mostSoldProducts(Collections.emptyList())
                    .leastSoldProducts(Collections.emptyList())
                    .build();
        }

        List<SalesData> salesData = salesDataProjections.stream()
                .map(this::mapToSalesData)
                .collect(Collectors.toList());

        long maxUnitsSold = salesData.stream().mapToLong(SalesData::unitsSold).max().orElse(0);
        long minUnitsSold = salesData.stream().mapToLong(SalesData::unitsSold).min().orElse(0);

        List<String> mostSoldProducts = getProductsByUnitsSold(salesData, maxUnitsSold);
        List<String> leastSoldProducts = getProductsByUnitsSold(salesData, minUnitsSold);

        return SalesReportDto.builder()
                .salesData(salesData)
                .mostSoldProducts(mostSoldProducts)
                .leastSoldProducts(leastSoldProducts)
                .build();
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYYMMDD.");
        }
    }

    private SalesData mapToSalesData(SalesDataProjection projection) {
        return SalesData.builder()
                .productName(projection.getFantasyName())
                .unitsSold(projection.getTotalQuantity())
                .totalRevenue(projection.getTotalRevenue())
                .build();
    }

    private List<String> getProductsByUnitsSold(List<SalesData> salesData, long unitsSold) {
        return salesData.stream()
                .filter(s -> s.unitsSold() == unitsSold)
                .map(SalesData::productName)
                .collect(Collectors.toList());
    }
}


