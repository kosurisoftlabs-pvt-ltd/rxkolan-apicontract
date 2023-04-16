package com.kosuri.rxkolan.util;

import com.kosuri.rxkolan.entity.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@UtilityClass
@Slf4j
public final class SecurityUtil {

    public static String getUserId() {
        return getUser().map(User::getPhoneNumber).orElse(null);
    }

    public static Optional<User> getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast);
    }

}