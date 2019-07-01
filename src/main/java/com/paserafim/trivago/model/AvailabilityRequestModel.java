package com.paserafim.trivago.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class AvailabilityRequestModel implements Serializable {

    @NotNull(message="start date cannot be missing or empty")
    private LocalDate startDate;

    @NotNull(message="end date cannot be missing or empty")
    private LocalDate endDate;

    private ArrayList<Occupancy> occupancy;

}
