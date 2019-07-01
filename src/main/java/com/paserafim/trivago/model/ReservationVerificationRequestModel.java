package com.paserafim.trivago.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVerificationRequestModel implements Serializable {
    @NotNull(message = "Reference cannot be null or empty")
    private String reference;
}
