package com.sap.citydata.dto.water;

public class DailyConsumptionResponse {
    private String day;
    private Double avgConsumption;

    public DailyConsumptionResponse() {
    }

    public DailyConsumptionResponse(String day, Double avgConsumption) {
        this.day = day;
        this.avgConsumption = avgConsumption;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(Double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }
}
