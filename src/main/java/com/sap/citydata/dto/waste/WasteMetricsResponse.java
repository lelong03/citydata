package com.sap.citydata.dto.waste;

public class WasteMetricsResponse {
    private String reportDate;
    private Double totalWasteCollected;
    private Double totalWasteRecycle;

    public WasteMetricsResponse() {
    }

    public WasteMetricsResponse(String reportDate, Double totalWasteCollected, Double totalWasteRecycle) {
        this.reportDate = reportDate;
        this.totalWasteCollected = totalWasteCollected;
        this.totalWasteRecycle = totalWasteRecycle;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Double getTotalWasteCollected() {
        return totalWasteCollected;
    }

    public void setTotalWasteCollected(Double totalWasteCollected) {
        this.totalWasteCollected = totalWasteCollected;
    }

    public Double getTotalWasteRecycle() {
        return totalWasteRecycle;
    }

    public void setTotalWasteRecycle(Double totalWasteRecycle) {
        this.totalWasteRecycle = totalWasteRecycle;
    }
}
