package com.kosuri.rxkolan.model.ambulance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceCreationRequest {

    @NotNull(message="Service Name Cannot Be Empty")
    private String serviceName;

    @NotNull(message="Vehicle  Brand Cannot Be Empty")
    private String vehicleBrand;

    @NotNull(message="Vehicle  Model Cannot Be Empty")
    private String vehicleModel;

    @NotNull(message="Vehicle  VIN Cannot Be Empty")
    private String vin;

    @NotNull(message="Vehicle  Registration Number Cannot Be Empty")
    private String registrationNumber;

    @NotNull(message="Vehicle  Owner Name Cannot Be Empty")
    private String ownerName;

    private boolean fittedWithOxygen;

    private boolean primaryCarer;

    @NotNull(message="Contact Number Cannot Be Empty")
    private String contactNumber;

    @NotNull(message = "RTO Registered Location")
    private  String rtoRegisteredLocation;

    @NotNull(message="State Cannot Be Empty")
    private String state;

    @NotNull(message="State Cannot Be Empty")
    private Boolean validLicense;

    @NotNull(message="Base Location Cannot Be Empty")
    private String baseLocation;

    @NotNull(message="Additional Features Cannot Be Empty")
    private String additionalFeatures;
}
