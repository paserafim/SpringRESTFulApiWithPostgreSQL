package com.paserafim.trivago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomAvailabilityResponseModel implements Serializable {

    @NotNull(message="start date cannot be missing or empty")
    private LocalDate startDate;

    @NotNull(message="end date cannot be missing or empty")
    private LocalDate endDate;

    private List<RoomType> roomTypes;
}
