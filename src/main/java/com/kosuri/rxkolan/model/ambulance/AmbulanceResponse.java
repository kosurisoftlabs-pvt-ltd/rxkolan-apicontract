package com.kosuri.rxkolan.model.ambulance;

import com.kosuri.rxkolan.entity.Ambulance;
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

    private boolean verified;

    public AmbulanceResponse(Ambulance ambulance){
        this.registrationNumber = ambulance.getAmbulanceRegNumber();;
        this.vin= ambulance.getVin();
        this.phoneNumber = ambulance.getVin();
        this.contactPerson= ambulance.getOwnerName();
        this.state=ambulance.getState();
        this.verified = ambulance.isVerified();
    }

}
