package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
@Table(name = "Availability")
public class Availabitity implements Serializable {
    @Id
    @GeneratedValue(generator = "availability_generator")
    @SequenceGenerator(
            name = "availability_generator",
            sequenceName = "availability_sequence",
            initialValue = 1
    )
    //@JsonIgnore
    private Long AvailabilityId;

    @Temporal(TemporalType.DATE)
    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "endDate", nullable = false)
    private Date endDate;

    //Foreign Key
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;

    @Override
    public String toString() {
        return "Availabitity{" +
                "AvailabilityId=" + AvailabilityId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", roomType=" + roomType .toString() +
                '}';
    }
}
