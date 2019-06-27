package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "RoomType")
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(generator = "roomType_generator")
    @SequenceGenerator(
            name = "roomType_generator",
            sequenceName = "roomType_sequence",
            initialValue = 10
    )
    private Long roomTypeId;

    @ManyToMany(mappedBy = "roomTypes")
    private Set<Reservation> reservations;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "occupancyId", nullable = false)
    private Occupancy occupancy;

    @Column(nullable = false, length = 3)
    private String roomTypeCode;

    @Column(nullable = false, precision = 2)
    private Double amount;

    @Column(nullable = false)
    private Integer roomsAvailable;

    public RoomType() { }

    public RoomType(Long roomTypeId, Set<Reservation> reservations, Occupancy occupancy, String roomTypeCode, Double amount, Integer roomsAvailable) {
        this.roomTypeId = roomTypeId;
        this.reservations = reservations;
        this.occupancy = occupancy;
        this.roomTypeCode = roomTypeCode;
        this.amount = amount;
        this.roomsAvailable = roomsAvailable;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Occupancy getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Occupancy occupancy) {
        this.occupancy = occupancy;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(Integer roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }
}
