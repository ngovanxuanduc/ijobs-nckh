package com.immortal.internship.exception;

public class Base4xxException extends BaseException {
    public Base4xxException(String errorMessage, String userMessage, int errorCode) {
        super(errorMessage, userMessage, errorCode);
    }
}
