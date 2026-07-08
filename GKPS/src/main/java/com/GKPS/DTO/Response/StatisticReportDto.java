package com.GKPS.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticReportDto {
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Object> summaryData;
    private List<Map<String, Object>> detailData;

    public void setSummary(Map<String, Object> summary) {
        this.summaryData = summary;
    }

    public Map<String, Object> getSummary() {
        return summaryData;
    }

    public void setDetails(List<Map<String, Object>> details) {
        this.detailData = details;
    }

    public List<Map<String, Object>> getDetails() {
        return detailData;
    }

    private GeneratedAt generatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneratedAt {
        private LocalDate date;
        private String time;
        private String generatedBy;
    }
}
