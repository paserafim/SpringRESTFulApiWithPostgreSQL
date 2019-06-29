package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Set<Reservation> reservations;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @JoinColumn(name = "occupancyId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Occupancy occupancy;

    @Column(nullable = false, length = 3)
    private String roomTypeCode;

    @Column(nullable = false, length = 15)
    private String roomTypeName;

    @Column(nullable = false, precision = 2)
    private Double amount;

    @Column(nullable = false)
    private Integer roomsAvailable;

    @Override
    public String toString() {
        return "RoomType{" +
                ", occupancy=" + occupancy +
                ", roomTypeCode='" + roomTypeCode + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", amount=" + amount +
                ", roomsAvailable=" + roomsAvailable +
                '}';
    }
}
