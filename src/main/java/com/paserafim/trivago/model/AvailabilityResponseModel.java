package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AvailabilityResponseModel implements Serializable {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<RoomType> roomTypes;

}
