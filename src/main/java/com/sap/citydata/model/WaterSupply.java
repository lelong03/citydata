package com.sap.citydata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "water_supply", indexes = {
        @Index(name = "idx_water_source", columnList = "source"),
        @Index(name = "idx_water_ts", columnList = "ts")
})
@Data
public class WaterSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Water source: River, Reservoir, Groundwater'")
    private String source;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Liters per household per day'")
    private Double consumption;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Water quality pH'")
    private Double ph;

    @Column(nullable = false, columnDefinition = "DOUBLE COMMENT 'Water quality turbidity (NTU)'")
    private Double turbidity;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COMMENT 'Infrastructure status: Normal, Leak, Maintenance'")
    private String status;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Reading timestamp'")
    private Timestamp ts;

    public WaterSupply() {
    }

    public Long getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public Double getConsumption() {
        return this.consumption;
    }

    public Double getPh() {
        return this.ph;
    }

    public Double getTurbidity() {
        return this.turbidity;
    }

    public String getStatus() {
        return this.status;
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

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public void setTurbidity(Double turbidity) {
        this.turbidity = turbidity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WaterSupply)) return false;
        final WaterSupply other = (WaterSupply) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$source = this.getSource();
        final Object other$source = other.getSource();
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$consumption = this.getConsumption();
        final Object other$consumption = other.getConsumption();
        if (this$consumption == null ? other$consumption != null : !this$consumption.equals(other$consumption))
            return false;
        final Object this$ph = this.getPh();
        final Object other$ph = other.getPh();
        if (this$ph == null ? other$ph != null : !this$ph.equals(other$ph)) return false;
        final Object this$turbidity = this.getTurbidity();
        final Object other$turbidity = other.getTurbidity();
        if (this$turbidity == null ? other$turbidity != null : !this$turbidity.equals(other$turbidity)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$ts = this.getTs();
        final Object other$ts = other.getTs();
        if (this$ts == null ? other$ts != null : !this$ts.equals(other$ts)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WaterSupply;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $source = this.getSource();
        result = result * PRIME + ($source == null ? 43 : $source.hashCode());
        final Object $consumption = this.getConsumption();
        result = result * PRIME + ($consumption == null ? 43 : $consumption.hashCode());
        final Object $ph = this.getPh();
        result = result * PRIME + ($ph == null ? 43 : $ph.hashCode());
        final Object $turbidity = this.getTurbidity();
        result = result * PRIME + ($turbidity == null ? 43 : $turbidity.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $ts = this.getTs();
        result = result * PRIME + ($ts == null ? 43 : $ts.hashCode());
        return result;
    }

    public String toString() {
        return "WaterSupply(id=" + this.getId() + ", source=" + this.getSource() + ", consumption=" + this.getConsumption() + ", ph=" + this.getPh() + ", turbidity=" + this.getTurbidity() + ", status=" + this.getStatus() + ", ts=" + this.getTs() + ")";
    }
}
