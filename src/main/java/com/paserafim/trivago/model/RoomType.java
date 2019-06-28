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
    private Set<Reservation> reservations;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "occupancyId", nullable = false)
    private Occupancy occupancy;

    @Column(nullable = false, length = 3)
    private String roomTypeCode;

    @Column(nullable = false, length = 15)
    private String roomTypeName;

    @Column(nullable = false, precision = 2)
    private Double amount;

    @Column(nullable = false)
    private Integer roomsAvailable;
}
