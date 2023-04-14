package com.kosuri.rxkolan.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyUserOtpRequest {

    private String phoneNumber;
    private String email;

    @NotEmpty(message = "OTP Cannot Be Empty")
    private String otp;
}
