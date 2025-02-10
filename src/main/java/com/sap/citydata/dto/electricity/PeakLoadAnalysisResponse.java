package com.sap.citydata.dto.electricity;

public class PeakLoadAnalysisResponse {
    private String peak;
    private Double avgConsumption;
    private Double maxConsumption;
    private Double totalConsumption;

    public PeakLoadAnalysisResponse() {
    }

    public PeakLoadAnalysisResponse(String peak, Double avgConsumption, Double maxConsumption, Double totalConsumption) {
        this.peak = peak;
        this.avgConsumption = avgConsumption;
        this.maxConsumption = maxConsumption;
        this.totalConsumption = totalConsumption;
    }

    public String getPeak() {
        return peak;
    }

    public void setPeak(String peak) {
        this.peak = peak;
    }

    public Double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(Double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }

    public Double getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Double maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}

