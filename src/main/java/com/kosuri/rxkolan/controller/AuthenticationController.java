package com.kosuri.rxkolan.controller;

import com.kosuri.rxkolan.model.user.AuthenticationRequest;
import com.kosuri.rxkolan.model.user.AuthenticationResponse;
import com.kosuri.rxkolan.model.user.RegistrationRequest;
import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.model.user.ValidateUserRequest;
import com.kosuri.rxkolan.model.user.VerifyUserOtpRequest;
import com.kosuri.rxkolan.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Validated RegistrationRequest registerRequest) {
        UserResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        return authenticationService.authenticate(authenticationRequest, request);
    }

    @PostMapping(value = "/user/validate")
    public ResponseEntity<Boolean> validateUser(@RequestBody ValidateUserRequest validateUserRequest) {
        Boolean response = authenticationService.validateUser(validateUserRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/otp/validate")
    public ResponseEntity<UserResponse> verifyUserOtp(@RequestBody VerifyUserOtpRequest verifyUserOtpRequest) {
        UserResponse response = authenticationService.verifyUserOtp(verifyUserOtpRequest);
        return ResponseEntity.ok(response);
    }
}
