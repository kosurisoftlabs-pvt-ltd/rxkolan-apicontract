package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.model.user.UserResponse;

public interface UserService {
    UserResponse fetchLoggedInUser();


    UserResponse fetchUser(String id);
}
