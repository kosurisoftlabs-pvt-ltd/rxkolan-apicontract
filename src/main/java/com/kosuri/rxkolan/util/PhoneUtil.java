package com.kosuri.rxkolan.util;

import com.kosuri.rxkolan.constant.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneUtil {

    private static final int NINETY_THOUSAND = 90000;
    private static final int TEN_THOUSAND = 100000;

    private static final int NINE_THOUSAND = 9000;
    private static final int ONE_THOUSAND = 1000;


    public static int generateSixDigitOTP() {
        return  TEN_THOUSAND + RandomDataGenerator.getRandomNumber(NINETY_THOUSAND);
    }

    public static int generateFourDigitOTP() {
        return  ONE_THOUSAND + RandomDataGenerator.getRandomNumber(NINE_THOUSAND);
    }

    public static boolean allowMasterOtp(String[] activeProfiles) {
        return Arrays.stream(activeProfiles).anyMatch(List.of(Constants.Profile.LOCAL)::contains);
    }


}