package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.exception.NotFoundException;
import com.kosuri.rxkolan.model.user.UserResponse;
import com.kosuri.rxkolan.repository.UserRepository;
import com.kosuri.rxkolan.service.UserService;
import com.kosuri.rxkolan.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponse fetchLoggedInUser() {
        final String userId = SecurityUtil.getUserId();
        return fetchUser(userId);
    }

    @Override
    public UserResponse fetchUser(final String id) {
        return new UserResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id)));
    }
}
