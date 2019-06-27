package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
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

    @Column(name = "totalAmount", nullable = false, precision = 2)
    private Double totalAmout;

    public Reservation() { }

    public Reservation(Long reservationId, Customer customer, Double totalAmout) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.totalAmout = totalAmout;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(Double totalAmout) {
        this.totalAmout = totalAmout;
    }
}
