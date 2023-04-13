package com.kosuri.rxkolan.model.user;

import com.kosuri.rxkolan.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {


    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$",
            message = "Invalid Password pattern. Password must contain 6 to 20 characters at least one digit, lower, upper case and one special character."
    )
    private String password;

    private List<Role> roles;

    private boolean emailVerified = false;

    private boolean phoneVerified = false;

    private String businessName;

    private String contactPerson;

    private String address;

    private String districtLocation;

    private String speciality;


}
