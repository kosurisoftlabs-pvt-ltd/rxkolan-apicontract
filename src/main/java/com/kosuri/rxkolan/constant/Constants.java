package com.kosuri.rxkolan.constant;

public class Constants {

    public static final String CLIENT_HEADER = "client";
    public static final String BEARER_KEY = "bearer-key";
    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String LOCAL_TIME_FORMAT = "HH:mm:ss";

    public static final String PHONE_NUMBER_SEARCH_CONSTANT= "phoneNumber";
    public static final String VIN_SEARCH_CONSTANT= "vin";
    public static final String AMB_REG_SEARCH_CONSTANT= "registrationNumber";
    public static final 	String AUTH_TOKEN= "Auth-Token";

    //Custom
    public static final String USER_NAME_EXISTS = "User with that Email/Phone already exists In Our System";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "PhoneNumber already in use!!";
    public static final class Client {
        public static final String WEB = "web";

        private Client() {
            //NOOP
        }

    }

    public static final class Profile{
        public static final String DEV="dev";
        public static final String UAT="uat";
        public static final String LOCAL="local";
        public static final String QA="qa";
        public static final String PROD="prod";

    }

    private Constants() {
        //NOOP
    }

}