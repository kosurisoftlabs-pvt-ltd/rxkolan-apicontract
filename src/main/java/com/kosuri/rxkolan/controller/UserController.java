package com.kosuri.rxkolan.controller;

import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kosuri.rxkolan.constant.Constants.BEARER_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/user")
@SecurityRequirement(name = BEARER_KEY)
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse fetchUserProfile() {
        return userService.fetchLoggedInUser();
    }
}