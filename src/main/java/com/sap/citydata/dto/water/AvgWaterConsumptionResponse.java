package com.sap.citydata.dto.water;

public class AvgWaterConsumptionResponse {
    private String source;
    private Double avgConsumption;

    public AvgWaterConsumptionResponse() {
    }

    public AvgWaterConsumptionResponse(String source, Double avgConsumption) {
        this.source = source;
        this.avgConsumption = avgConsumption;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(Double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }
}
