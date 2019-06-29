package com.paserafim.trivago.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class AvailabilityRequestModel implements Serializable {

    @NotNull(message="start date cannot be missing or empty")
    private LocalDate startDate;

    @NotNull(message="end date cannot be missing or empty")
    private LocalDate endDate;

    private ArrayList<Occupancy> occupancy;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Occupancy> getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(ArrayList<Occupancy> occupancy) {
        this.occupancy = occupancy;
    }
}
