package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.model.user.AuthenticationRequest;
import com.kosuri.rxkolan.model.user.AuthenticationResponse;
import com.kosuri.rxkolan.model.user.RegistrationRequest;
import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.model.user.ValidateUserRequest;
import com.kosuri.rxkolan.model.user.VerifyUserOtpRequest;
import com.kosuri.rxkolan.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public UserResponse register(RegistrationRequest registerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public Boolean validateUser(ValidateUserRequest validateUserRequest) {
        return null;
    }

    @Override
    public Boolean verifyUserOtp(VerifyUserOtpRequest verifyUserOtpRequest) {
        return null;
    }
}
