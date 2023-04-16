package com.kosuri.rxkolan.repository;

import com.kosuri.rxkolan.entity.User;
import com.kosuri.rxkolan.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, String> {

    Optional<UserOtp> findByUserAndActiveTrue(User user);

    Optional<UserOtp> findByEmailAndActiveTrue(String email);

    Optional<UserOtp> findByPhoneNumberAndActiveTrue(String phoneNumber);

}
