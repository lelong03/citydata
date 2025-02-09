package com.sap.citydata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "electricity", indexes = {
        @Index(name = "idx_elec_source", columnList = "source"),
        @Index(name = "idx_elec_district", columnList = "district"),
        @Index(name = "idx_elec_ts", columnList = "ts"),
        @Index(name = "idx_elec_outage_ts", columnList = "outage_ts")
})
@Data
public class Electricity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source", length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Energy source: Solar, Wind, Hydro, Coal'")
    private String source;

    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50) COMMENT 'District/Area'")
    private String district;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Consumption in kWh'")
    private Double consumption;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Peak load hours (e.g., 17:00-19:00)'")
    private String peak;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Grid status: Active, Overloaded, Maintenance'")
    private String status;

    @Column(name = "outage_ts", columnDefinition = "TIMESTAMP NULL COMMENT 'Outage start timestamp'")
    private Timestamp outageTs;

    @Column(name = "outage_dur", columnDefinition = "INT NULL COMMENT 'Outage duration in minutes'")
    private Integer outageDur;

    @Column(name = "outage_area", length = 100, columnDefinition = "VARCHAR(100) NULL COMMENT 'Affected areas during outage'")
    private String outageArea;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Reading timestamp'")
    private Timestamp ts;

    public Electricity() {
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Electricity)) return false;
        final Electricity other = (Electricity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$source = this.getSource();
        final Object other$source = other.getSource();
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$district = this.getDistrict();
        final Object other$district = other.getDistrict();
        if (this$district == null ? other$district != null : !this$district.equals(other$district)) return false;
        final Object this$consumption = this.getConsumption();
        final Object other$consumption = other.getConsumption();
        if (this$consumption == null ? other$consumption != null : !this$consumption.equals(other$consumption))
            return false;
        final Object this$peak = this.getPeak();
        final Object other$peak = other.getPeak();
        if (this$peak == null ? other$peak != null : !this$peak.equals(other$peak)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$outageTs = this.getOutageTs();
        final Object other$outageTs = other.getOutageTs();
        if (this$outageTs == null ? other$outageTs != null : !this$outageTs.equals(other$outageTs)) return false;
        final Object this$outageDur = this.getOutageDur();
        final Object other$outageDur = other.getOutageDur();
        if (this$outageDur == null ? other$outageDur != null : !this$outageDur.equals(other$outageDur)) return false;
        final Object this$outageArea = this.getOutageArea();
        final Object other$outageArea = other.getOutageArea();
        if (this$outageArea == null ? other$outageArea != null : !this$outageArea.equals(other$outageArea))
            return false;
        final Object this$ts = this.getTs();
        final Object other$ts = other.getTs();
        if (this$ts == null ? other$ts != null : !this$ts.equals(other$ts)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Electricity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $source = this.getSource();
        result = result * PRIME + ($source == null ? 43 : $source.hashCode());
        final Object $district = this.getDistrict();
        result = result * PRIME + ($district == null ? 43 : $district.hashCode());
        final Object $consumption = this.getConsumption();
        result = result * PRIME + ($consumption == null ? 43 : $consumption.hashCode());
        final Object $peak = this.getPeak();
        result = result * PRIME + ($peak == null ? 43 : $peak.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $outageTs = this.getOutageTs();
        result = result * PRIME + ($outageTs == null ? 43 : $outageTs.hashCode());
        final Object $outageDur = this.getOutageDur();
        result = result * PRIME + ($outageDur == null ? 43 : $outageDur.hashCode());
        final Object $outageArea = this.getOutageArea();
        result = result * PRIME + ($outageArea == null ? 43 : $outageArea.hashCode());
        final Object $ts = this.getTs();
        result = result * PRIME + ($ts == null ? 43 : $ts.hashCode());
        return result;
    }

    public String toString() {
        return "Electricity(id=" + this.getId() + ", source=" + this.getSource() + ", district=" + this.getDistrict() + ", consumption=" + this.getConsumption() + ", peak=" + this.getPeak() + ", status=" + this.getStatus() + ", outageTs=" + this.getOutageTs() + ", outageDur=" + this.getOutageDur() + ", outageArea=" + this.getOutageArea() + ", ts=" + this.getTs() + ")";
    }

    public Long getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public String getDistrict() {
        return this.district;
    }

    public Double getConsumption() {
        return this.consumption;
    }

    public String getPeak() {
        return this.peak;
    }

    public String getStatus() {
        return this.status;
    }

    public Timestamp getOutageTs() {
        return this.outageTs;
    }

    public Integer getOutageDur() {
        return this.outageDur;
    }

    public String getOutageArea() {
        return this.outageArea;
    }

    public Timestamp getTs() {
        return this.ts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public void setPeak(String peak) {
        this.peak = peak;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOutageTs(Timestamp outageTs) {
        this.outageTs = outageTs;
    }

    public void setOutageDur(Integer outageDur) {
        this.outageDur = outageDur;
    }

    public void setOutageArea(String outageArea) {
        this.outageArea = outageArea;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}

