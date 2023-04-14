package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.constant.Constants;
import com.kosuri.rxkolan.entity.Role;
import com.kosuri.rxkolan.entity.User;
import com.kosuri.rxkolan.exception.BadRequestException;
import com.kosuri.rxkolan.model.user.AuthenticationRequest;
import com.kosuri.rxkolan.model.user.AuthenticationResponse;
import com.kosuri.rxkolan.model.user.RegistrationRequest;
import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.model.user.ValidateUserRequest;
import com.kosuri.rxkolan.model.user.VerifyUserOtpRequest;
import com.kosuri.rxkolan.repository.UserRepository;
import com.kosuri.rxkolan.security.TokenProvider;
import com.kosuri.rxkolan.service.AuthenticationService;
import com.kosuri.rxkolan.service.OtpService;
import com.kosuri.rxkolan.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final OtpService otpService;



    @Override
    @Transactional
    public UserResponse register(RegistrationRequest registerRequest) {
        log.info("AuthService :: processSignUp() :: Init ");
        Optional<User> optionalUser = userRepository.findByEmailOrPhoneNumber(registerRequest.getEmail(),registerRequest.getPhoneNumber());
        if (optionalUser.isPresent()) {
            throw new BadRequestException(Constants.USER_NAME_EXISTS);
        }
        List<Role> roles = roleService.findAllRoleByName(registerRequest.getRoleName());
        var user = User.builder().email(registerRequest.getEmail()).phoneNumber(registerRequest.getPhoneNumber())
                .accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
                .address(registerRequest.getAddress()).businessName(registerRequest.getBusinessName())
                .enabled(false).contactPerson(registerRequest.getContactPerson())
                .emailVerified(false).phoneVerified(false).password(passwordEncoder.encode(registerRequest.getPassword()))
                .speciality(registerRequest.getSpeciality()).serviceOffer(registerRequest.getServiceOffered().getDescription())
                .districtLocation(registerRequest.getDistrictLocation()).roles(!CollectionUtils.isEmpty(roles)?roles:null)
                .build();
        user = userRepository.save(user);
        boolean emailOtpSent = otpService.sendOtpToEmail(registerRequest.getEmail(), user);
        boolean phoneOtpSent = otpService.sendOtpToPhoneNumber(registerRequest.getPhoneNumber(), user);
        if(!emailOtpSent && !phoneOtpSent){
            log.error("Rollback the Entire Transaction as OTP Service Error");
            throw new BadRequestException("Failed to Register User "+registerRequest.getPhoneNumber());
        }
        return new UserResponse(user);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        if(!authentication.isAuthenticated()){
            //Trying with Phone Number than Email Authentication as Email Authentication has Failed
            authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(), authenticationRequest.getPassword())
            );
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalDateTime expiredTime = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(appProperties.getAuth().getRefreshTokenExpiryMin());
        var user = userRepository.findByEmailOrPhoneNumber(authenticationRequest.getEmail(),authenticationRequest.getPhoneNumber()).orElseThrow();
        String accessToken = tokenProvider.getToken(user, expiredTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        return ResponseEntity.ok().body(new AuthenticationResponse(accessToken));
    }

    @Override
    public Boolean validateUser(ValidateUserRequest validateUserRequest) {
        return false;
    }

    @Override
    @Transactional
    public UserResponse verifyUserOtp(VerifyUserOtpRequest verifyUserOtpRequest) {
        boolean isEmailOtpValid = false;
        boolean isPhoneOtpValid = false;
        Optional<User> userOptional;
        if(StringUtils.isNotEmpty(verifyUserOtpRequest.getEmail())){
            log.info("Verifying OTP send to Email {}",verifyUserOtpRequest.getEmail());
            userOptional =  userRepository.findByEmail(verifyUserOtpRequest.getEmail());
            isEmailOtpValid = otpService.validateEmailOtp(verifyUserOtpRequest.getOtp(), verifyUserOtpRequest.getEmail());
        }else{
            log.info("Verifying OTP send to Email {}",verifyUserOtpRequest.getPhoneNumber());
            userOptional =  userRepository.findByPhoneNumber(verifyUserOtpRequest.getPhoneNumber());
            isPhoneOtpValid = otpService.validatePhoneOtp(verifyUserOtpRequest.getOtp(), verifyUserOtpRequest.getPhoneNumber());
        }

        if(userOptional.isPresent() && (isEmailOtpValid || isPhoneOtpValid)){
           User user =  userOptional.get();
           user.setEmailVerified(isEmailOtpValid);
           user.setPhoneVerified(isPhoneOtpValid);
            user = userRepository.save(user);
            return new UserResponse(user);
        }
        return UserResponse.builder().build();
    }
}
