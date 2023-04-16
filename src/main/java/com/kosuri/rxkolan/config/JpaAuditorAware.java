package com.kosuri.rxkolan.config;

import com.kosuri.rxkolan.util.SecurityUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class JpaAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(SecurityUtil.getUserId());
        } catch (IllegalStateException | NullPointerException exception) {
            return Optional.of("1");
        }
    }
}