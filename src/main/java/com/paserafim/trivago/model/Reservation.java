package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(generator = "reservation_generator")
    @SequenceGenerator(
            name = "reservation_generator",
            sequenceName = "reservation_sequence",
            initialValue = 1000
    )
    private Long reservationId;

    @ManyToMany
    @JoinTable(name = "ReservationTypeRoom",
               joinColumns = {@JoinColumn(name = "reservationId")},
               inverseJoinColumns = {@JoinColumn(name = "roomTypeId")})
    private Set<RoomType> roomTypes;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 10,unique = true)
    private String reference;

    @Column(name = "totalAmount", nullable = false, precision = 2)
    private Double totalAmount;

}
