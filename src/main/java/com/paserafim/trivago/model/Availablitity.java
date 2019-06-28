package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Availability")
public class Availablitity implements Serializable {
    @Id
    @GeneratedValue(generator = "availability_generator")
    @SequenceGenerator(
            name = "availability_generator",
            sequenceName = "availability_sequence",
            initialValue = 1
    )
    private Long AvailabilityId;

    @Temporal(TemporalType.DATE)
    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "endDate", nullable = false)
    private Date endDate;

    //Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;

}
