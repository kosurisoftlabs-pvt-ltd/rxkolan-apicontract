package com.kosuri.rxkolan.model.ambulance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AmbulanceResponse {

    private String registrationNumber;

    private String vin;

    private String phoneNumber;

    private String contactPerson;

    private String registrationRTO;

    private String state;

    private String rcDocument;

    private String licenseNumber;

    private String status;

}
