package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.entity.User;

public interface OtpService {

     boolean sendOtpToEmail(String email, User user);

    boolean sendOtpToPhoneNumber(String phoneNumber, User user);


    boolean validateEmailOtp(String otp,String email);

    boolean validatePhoneOtp(String otp,String mobileNumber);


}
