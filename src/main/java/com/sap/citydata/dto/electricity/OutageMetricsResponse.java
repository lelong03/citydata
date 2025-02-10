package com.sap.citydata.dto.electricity;

public class OutageMetricsResponse {
    private String outageDate;
    private Long outageFrequency;
    private Double avgOutageDuration;
    private Double totalOutageDuration;

    public OutageMetricsResponse() {
    }

    public OutageMetricsResponse(String outageDate, Long outageFrequency, Double avgOutageDuration, Double totalOutageDuration) {
        this.outageDate = outageDate;
        this.outageFrequency = outageFrequency;
        this.avgOutageDuration = avgOutageDuration;
        this.totalOutageDuration = totalOutageDuration;
    }

    public String getOutageDate() {
        return outageDate;
    }

    public void setOutageDate(String outageDate) {
        this.outageDate = outageDate;
    }

    public Long getOutageFrequency() {
        return outageFrequency;
    }

    public void setOutageFrequency(Long outageFrequency) {
        this.outageFrequency = outageFrequency;
    }

    public Double getAvgOutageDuration() {
        return avgOutageDuration;
    }

    public void setAvgOutageDuration(Double avgOutageDuration) {
        this.avgOutageDuration = avgOutageDuration;
    }

    public Double getTotalOutageDuration() {
        return totalOutageDuration;
    }

    public void setTotalOutageDuration(Double totalOutageDuration) {
        this.totalOutageDuration = totalOutageDuration;
    }
}

