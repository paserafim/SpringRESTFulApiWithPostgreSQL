package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "RoomType")
public class RoomType implements Serializable {

    @Id
    @GeneratedValue(generator = "roomType_generator")
    @SequenceGenerator(
            name = "roomType_generator",
            sequenceName = "roomType_sequence",
            initialValue = 10
    )
    @JsonIgnore
    private Long roomTypeId;

    @ManyToMany(mappedBy = "roomTypes")
    @JsonIgnore
    @JsonIgnoreProperties("roomTypes")
    private Set<Reservation> reservations;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    //@JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "occupancyId", nullable = false)
    private Occupancy occupancy;

    @Column(nullable = false, length = 3)
    private String roomTypeCode;

    @Column(length = 15)
    private String roomTypeName;

    @Column(precision = 2)
    private Double amount;

    //@JsonIgnore
    private Integer roomsAvailable;

}
