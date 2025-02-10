package com.sap.citydata.dto.waste;

public class RecyclingRateResponse {
    private Double recycledRate;
    private Double nonRecycledRate;

    public RecyclingRateResponse() {
    }

    public RecyclingRateResponse(Double recycledRate) {
        this.recycledRate = recycledRate;
        this.nonRecycledRate = 100 - recycledRate;
    }

    public Double getRecycledRate() {
        return recycledRate;
    }

    public void setRecycledRate(Double recycledRate) {
        this.recycledRate = recycledRate;
        this.nonRecycledRate = 100 - recycledRate;
    }

    public Double getNonRecycledRate() {
        return nonRecycledRate;
    }

    public void setNonRecycledRate(Double nonRecycledRate) {
        this.nonRecycledRate = nonRecycledRate;
    }
}

