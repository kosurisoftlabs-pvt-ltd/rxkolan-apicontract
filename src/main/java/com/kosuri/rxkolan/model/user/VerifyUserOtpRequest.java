package com.kosuri.rxkolan.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyUserOtpRequest {

    private String username;
    private String phoneNumber;
    private String email;
    private String otp;
}
