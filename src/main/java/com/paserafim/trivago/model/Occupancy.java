package com.paserafim.trivago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Occupancy")
public class Occupancy implements Serializable {
    @Id
    @GeneratedValue(generator = "occupancy_generator")
    @SequenceGenerator(
            name = "occupancy_generator",
            sequenceName = "occupancy_sequence",
            initialValue = 10
    )
    @JsonIgnore
    private Long occupancyId;

    @Column(name = "adults", nullable = false)
    private Integer adults;

    @Column(name = "juniors", nullable = false)
    private Integer juniors;

    @Column(name = "babies", nullable = false)
    private Integer babies;

    @Override
    public String toString() {
        return "Occupancy{" +
                ", adults=" + adults +
                ", juniors=" + juniors +
                ", babies=" + babies +
                '}';
    }
}
