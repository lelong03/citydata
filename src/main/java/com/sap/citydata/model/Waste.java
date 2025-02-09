package com.sap.citydata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "waste", indexes = {
        @Index(name = "idx_waste_freq", columnList = "freq"),
        @Index(name = "idx_waste_report_date", columnList = "report_date")
})
@Data
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Collection frequency: Daily, Weekly'")
    private String freq;

    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50) COMMENT 'Segregation level (e.g., Organic, Plastic, Metal, Electronic)'")
    private String segregation;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Facility status: Normal, AtCapacity, Offline'")
    private String facility;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Total waste collected (tons)'")
    private Double total;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Recycling rate (%)'")
    private Double recycle;

    @Column(name = "report_date", nullable = false, columnDefinition = "DATE COMMENT 'Reporting date'")
    private Date reportDate;

    public Waste() {
    }

    public Long getId() {
        return this.id;
    }

    public String getFreq() {
        return this.freq;
    }

    public String getSegregation() {
        return this.segregation;
    }

    public String getFacility() {
        return this.facility;
    }

    public Double getTotal() {
        return this.total;
    }

    public Double getRecycle() {
        return this.recycle;
    }

    public Date getReportDate() {
        return this.reportDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public void setSegregation(String segregation) {
        this.segregation = segregation;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setRecycle(Double recycle) {
        this.recycle = recycle;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Waste)) return false;
        final Waste other = (Waste) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$freq = this.getFreq();
        final Object other$freq = other.getFreq();
        if (this$freq == null ? other$freq != null : !this$freq.equals(other$freq)) return false;
        final Object this$segregation = this.getSegregation();
        final Object other$segregation = other.getSegregation();
        if (this$segregation == null ? other$segregation != null : !this$segregation.equals(other$segregation))
            return false;
        final Object this$facility = this.getFacility();
        final Object other$facility = other.getFacility();
        if (this$facility == null ? other$facility != null : !this$facility.equals(other$facility)) return false;
        final Object this$total = this.getTotal();
        final Object other$total = other.getTotal();
        if (this$total == null ? other$total != null : !this$total.equals(other$total)) return false;
        final Object this$recycle = this.getRecycle();
        final Object other$recycle = other.getRecycle();
        if (this$recycle == null ? other$recycle != null : !this$recycle.equals(other$recycle)) return false;
        final Object this$reportDate = this.getReportDate();
        final Object other$reportDate = other.getReportDate();
        if (this$reportDate == null ? other$reportDate != null : !this$reportDate.equals(other$reportDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Waste;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $freq = this.getFreq();
        result = result * PRIME + ($freq == null ? 43 : $freq.hashCode());
        final Object $segregation = this.getSegregation();
        result = result * PRIME + ($segregation == null ? 43 : $segregation.hashCode());
        final Object $facility = this.getFacility();
        result = result * PRIME + ($facility == null ? 43 : $facility.hashCode());
        final Object $total = this.getTotal();
        result = result * PRIME + ($total == null ? 43 : $total.hashCode());
        final Object $recycle = this.getRecycle();
        result = result * PRIME + ($recycle == null ? 43 : $recycle.hashCode());
        final Object $reportDate = this.getReportDate();
        result = result * PRIME + ($reportDate == null ? 43 : $reportDate.hashCode());
        return result;
    }

    public String toString() {
        return "Waste(id=" + this.getId() + ", freq=" + this.getFreq() + ", segregation=" + this.getSegregation() + ", facility=" + this.getFacility() + ", total=" + this.getTotal() + ", recycle=" + this.getRecycle() + ", reportDate=" + this.getReportDate() + ")";
    }
}
