package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.model.user.AuthenticationRequest;
import com.kosuri.rxkolan.model.user.AuthenticationResponse;
import com.kosuri.rxkolan.model.user.RegistrationRequest;
import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.model.user.ValidateUserRequest;
import com.kosuri.rxkolan.model.user.VerifyUserOtpRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    UserResponse register(RegistrationRequest registerRequest);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request);

    Boolean validateUser(ValidateUserRequest validateUserRequest);

    UserResponse verifyUserOtp(VerifyUserOtpRequest verifyUserOtpRequest);
}
