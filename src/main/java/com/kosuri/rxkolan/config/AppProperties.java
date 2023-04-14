package com.kosuri.rxkolan.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rxkolan")
public class AppProperties {

    private final Auth auth = new Auth();
    private final TextLocal textLocal = new TextLocal();
    private final Otp otp = new Otp();

    @Getter
    @Setter
    public static class Auth {
        private long refreshTokenExpiryMin;
        private long tokenExpirationMSec;
    }

    @Getter
    @Setter
    public static class TextLocal {
        private String apiKey;
        private String sender;
        private String url;
    }

    @Getter
    @Setter
    public static class Otp {
        private String smsMessage;
        private String emailSubject;
    }
}