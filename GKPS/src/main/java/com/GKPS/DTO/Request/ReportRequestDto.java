package com.GKPS.DTO.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequestDto {
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filterBy;
    private String groupBy;
}
