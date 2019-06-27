package com.paserafim.trivago.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Hotel")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(generator = "hotel_generator")
    @SequenceGenerator(name = "hotel_generator", sequenceName = "hotel_sequence", initialValue = 10)
    private Long hotelId;

    @Column(nullable = false, length = 50)
    private String name;

    public Hotel() {
    }

    public Hotel(String name) {
        this.name = name;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



