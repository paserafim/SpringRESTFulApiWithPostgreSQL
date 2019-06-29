package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoomAvailabilityResponseModel implements Serializable {

    @NotNull(message="startdate cannot be missing or empty")
    private LocalDate startDate;

    @NotNull(message="enddate cannot be missing or empty")
    private LocalDate endDate;

    private List<RoomType> roomTypes;

}
