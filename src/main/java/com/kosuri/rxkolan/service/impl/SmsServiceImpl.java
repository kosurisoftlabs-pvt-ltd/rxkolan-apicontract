package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.service.SMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SMSService {

    private final AppProperties appProperties;

    @Override
    public boolean sendSMSMessage(String mobileNumber, String message) {
        try {
            String apiKey = "apikey=" + URLEncoder.encode(appProperties.getTextLocal().getApiKey(), StandardCharsets.UTF_8);
            String encodedMessage = "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
            String sender = "&sender=" + URLEncoder.encode(appProperties.getTextLocal().getSender(), StandardCharsets.UTF_8);
            String numbers = "&numbers=91" + URLEncoder.encode(mobileNumber, StandardCharsets.UTF_8);

            String data = appProperties.getTextLocal().getUrl() + apiKey + numbers + encodedMessage + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            return true;
        }catch(Exception exception){
            log.error("Exception while Sending SMS to Mobile Number {} with error {}",mobileNumber,exception.getMessage());
            return false;
        }
    }
}
