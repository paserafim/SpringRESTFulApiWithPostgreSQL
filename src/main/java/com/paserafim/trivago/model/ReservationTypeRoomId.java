package com.paserafim.trivago.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ReservationTypeRoomId implements Serializable {

    private Long reservationId;
    private Long roomTypeId;
}
