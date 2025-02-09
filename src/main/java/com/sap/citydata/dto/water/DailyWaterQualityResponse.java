package com.sap.citydata.dto.water;

public class DailyWaterQualityResponse {
    private String day;
    private Double avgPh;
    private Double avgTurbidity;

    public DailyWaterQualityResponse() {
    }

    public DailyWaterQualityResponse(String day, Double avgPh, Double avgTurbidity) {
        this.day = day;
        this.avgPh = avgPh;
        this.avgTurbidity = avgTurbidity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getAvgPh() {
        return avgPh;
    }

    public void setAvgPh(Double avgPh) {
        this.avgPh = avgPh;
    }

    public Double getAvgTurbidity() {
        return avgTurbidity;
    }

    public void setAvgTurbidity(Double avgTurbidity) {
        this.avgTurbidity = avgTurbidity;
    }
}
