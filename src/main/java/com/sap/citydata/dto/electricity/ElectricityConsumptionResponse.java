package com.sap.citydata.dto.electricity;

public class ElectricityConsumptionResponse {
    private String district;
    private Double avgConsumption;

    public ElectricityConsumptionResponse() {
    }

    public ElectricityConsumptionResponse(String district, Double avgConsumption) {
        this.district = district;
        this.avgConsumption = avgConsumption;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(Double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }
}
