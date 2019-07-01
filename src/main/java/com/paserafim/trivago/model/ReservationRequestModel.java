package com.paserafim.trivago.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestModel implements Serializable {

    @NotNull(message="start date cannot be missing or empty")
    private Date startDate;

    @NotNull(message="end date cannot be missing or empty")
    private Date endDate;

    @NotNull(message="Customer FullName cannot be missing or empty")
    private String customerFullName;

    @NotNull(message="Customer Email cannot be missing or empty")
    private String customerMail;

    @NotNull(message="Room Types cannot be missing or empty")
    private ArrayList<RoomType> roomTypes;
}
