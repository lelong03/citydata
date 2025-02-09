package com.sap.citydata.dto.water;

public class DailyWaterLossResponse {
    private String day;
    private Double totalConsumption;
    private Double lostConsumption;
    private Double lossRate;

    public DailyWaterLossResponse() {
    }

    public DailyWaterLossResponse(String day, Double totalConsumption, Double lostConsumption, Double lossRate) {
        this.day = day;
        this.totalConsumption = totalConsumption;
        this.lostConsumption = lostConsumption;
        this.lossRate = lossRate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Double getLostConsumption() {
        return lostConsumption;
    }

    public void setLostConsumption(Double lostConsumption) {
        this.lostConsumption = lostConsumption;
    }

    public Double getLossRate() {
        return lossRate;
    }

    public void setLossRate(Double lossRate) {
        this.lossRate = lossRate;
    }
}
