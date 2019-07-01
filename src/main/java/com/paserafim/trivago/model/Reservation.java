package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
//@data
@Getter
@Setter
@Table(name = "Reservation")
public class Reservation implements Serializable {

    @Id
   @GeneratedValue(generator = "reservation_generator")
   @SequenceGenerator(
            name = "reservation_generator",
            sequenceName = "reservation_sequence",
            initialValue = 1000)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long reservationId;

    @ManyToMany//(cascade = CascadeType.ALL)
    @JoinTable(name = "ReservationTypeRoom",
            joinColumns = {@JoinColumn(name = "reservationId")},
            inverseJoinColumns = {@JoinColumn(name = "roomTypeId")})
    @JsonIgnore
    @JsonIgnoreProperties("reservations")
    private Set<RoomType> roomTypes;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 10, unique = true)
    private String reference;

    @Column(name = "totalAmount", nullable = false, precision = 2)
    private Double totalAmount;

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", customer=" + customer +
                ", reference='" + reference + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
