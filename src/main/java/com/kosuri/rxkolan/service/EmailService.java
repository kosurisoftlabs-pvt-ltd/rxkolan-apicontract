package com.kosuri.rxkolan.service;



public interface EmailService {

    boolean sendEmailMessage(String to, String subject, String message);
}
