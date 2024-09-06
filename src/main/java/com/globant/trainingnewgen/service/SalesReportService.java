package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.model.dto.SalesReportDto;

import java.time.LocalDate;

public interface SalesReportService {
    SalesReportDto getSalesReport(String startDate, String endDate);
}
