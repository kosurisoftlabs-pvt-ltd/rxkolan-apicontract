package com.kosuri.rxkolan.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomDataGenerator {

    private RandomDataGenerator() {

    }

    public static int getRandomNumber(int bound) {
        return RandomNumber.getRandomNumber(bound);
    }

    private static final class RandomNumber {
        private static Random rand;

        private static int getRandomNumber(int bound) {
            if (rand == null) {
                try {
                    rand = SecureRandom.getInstanceStrong();
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalArgumentException("Could not generate Random Number", e);
                }
            }

            return rand.nextInt(bound);
        }
    }
}