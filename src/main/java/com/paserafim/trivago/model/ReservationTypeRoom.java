package com.paserafim.trivago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ReservationTypeRoom")
public class ReservationTypeRoom implements Serializable {

    @EmbeddedId
    private ReservationTypeRoomId reservationTypeRoomId;

    @Temporal(TemporalType.DATE)
    //@Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    //@Column(nullable = false)
    private Date endDate;

    //@Column(nullable = false)
    private Integer quantity;
}
