package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.entity.User;
import com.kosuri.rxkolan.entity.UserOtp;
import com.kosuri.rxkolan.repository.UserOtpRepository;
import com.kosuri.rxkolan.service.EmailService;
import com.kosuri.rxkolan.service.OtpService;
import com.kosuri.rxkolan.service.SMSService;
import com.kosuri.rxkolan.template.EmailTemplate;
import com.kosuri.rxkolan.util.PhoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final EmailService emailService;
    private final UserOtpRepository userOtpRepository;
    private final SMSService smsService;
    private final AppProperties appProperties;

    @Override
    public boolean sendOtpToEmail(String email, User user) {
        //Generate The Template to send OTP
        Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserAndActiveTrue(user);
        String otp = String.valueOf(PhoneUtil.generateFourDigitOTP());
        EmailTemplate template = new EmailTemplate("static/send-otp.html");
        Map<String,String> replacements = new HashMap<>();

        replacements.put("user", email);
        replacements.put("otp", otp);
        String message = template.getTemplate(replacements);
        boolean messageSent =  emailService.sendEmailMessage(email,message,appProperties.getOtp().getEmailSubject());
       if(messageSent) {
           UserOtp userOtp;
           if (userOtpOptional.isPresent()) {
               userOtp = userOtpOptional.get();
               userOtp.setEmailOtp(otp);
               userOtp.setEmailOtpDate(LocalDate.now(ZoneOffset.UTC));
           } else {
               userOtp = UserOtp.builder().emailOtpDate(LocalDate.now(ZoneOffset.UTC))
                       .emailOtp(otp).email(email).user(user).build();
           }
           userOtpRepository.save(userOtp);
       }
        return messageSent;
    }

    @Override
    public boolean sendOtpToPhoneNumber(String phoneNumber,User user) {
        //Generate The Template to send OTP
        Optional<UserOtp> userOtpOptional = userOtpRepository.findByUserAndActiveTrue(user);
        String otp = String.valueOf(PhoneUtil.generateFourDigitOTP());
        String smsMessage = String.format(appProperties.getOtp().getSmsMessage(), otp);
        boolean messageSent = smsService.sendSMSMessage(phoneNumber,smsMessage);
        if(messageSent) {
            UserOtp userOtp;
            if (userOtpOptional.isPresent()) {
                userOtp = userOtpOptional.get();
                userOtp.setPhoneOtp(otp);
                userOtp.setPhoneOtpDate(LocalDate.now(ZoneOffset.UTC));
                userOtp.setPhoneNumber(phoneNumber);
            } else {
                userOtp = UserOtp.builder().phoneOtpDate(LocalDate.now(ZoneOffset.UTC))
                        .phoneOtp(otp).phoneNumber(phoneNumber).user(user).build();
            }
            userOtpRepository.save(userOtp);
        }
        return messageSent;
    }

    @Override
    public boolean validateEmailOtp(String otp, String email) {
        log.info("Validating Email OTP sent to Email {}",email);
        Optional<UserOtp> userOtpOptional = userOtpRepository.findByEmailAndActiveTrue(email);
        if(userOtpOptional.isPresent()){
            UserOtp userOtp = userOtpOptional.get();
            return userOtp.getEmailOtp().equals(otp);
        }
        return false;
    }

    @Override
    public boolean validatePhoneOtp(String otp, String mobileNumber) {
        log.info("Validating Email OTP sent to MobileNumber {}",mobileNumber);
        Optional<UserOtp> userOtpOptional = userOtpRepository.findByPhoneNumberAndActiveTrue(mobileNumber);
        if(userOtpOptional.isPresent()){
            UserOtp userOtp = userOtpOptional.get();
            return userOtp.getPhoneOtp().equals(otp);
        }
        return false;
    }

}
