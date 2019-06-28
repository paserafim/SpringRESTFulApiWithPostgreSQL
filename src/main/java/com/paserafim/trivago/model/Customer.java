package com.paserafim.trivago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(generator = "customer_generator")
    @SequenceGenerator(
            name = "customer_generator",
            sequenceName = "customer_sequence",
            initialValue = 100
    )
    private Long customerId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String email;

}
