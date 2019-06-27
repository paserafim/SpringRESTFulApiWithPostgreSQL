package com.paserafim.trivago.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Occupancy")
public class Occupancy implements Serializable {

    @Id
    @GeneratedValue(generator = "occupancy_generator")
    @SequenceGenerator(
            name = "occupancy_generator",
            sequenceName = "occupancy_sequence",
            initialValue = 10
    )
    private Long occupancyId;

    @Column(name = "adults", nullable = false)
    private Integer adults;

    @Column(name = "children", nullable = false)
    private Integer children;

    @Column(name = "babies", nullable = false)
    private Integer babies;

    public Occupancy() { }

    public Occupancy(Long occupancyId, Integer adults, Integer children, Integer babies) {
        this.occupancyId = occupancyId;
        this.adults = adults;
        this.children = children;
        this.babies = babies;
    }

    public Long getOccupancyId() {
        return occupancyId;
    }

    public void setOccupancyId(Long occupancyId) {
        this.occupancyId = occupancyId;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Integer getBabies() {
        return babies;
    }

    public void setBabies(Integer babies) {
        this.babies = babies;
    }
}
