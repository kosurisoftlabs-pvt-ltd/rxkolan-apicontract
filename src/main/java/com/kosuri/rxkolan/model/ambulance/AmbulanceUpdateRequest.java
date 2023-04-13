package com.kosuri.rxkolan.model.ambulance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AmbulanceUpdateRequest {

    private String location;

    private Boolean available;

    private BigDecimal updatedPrice;

    private String waitingTime;
}
