package com.immortal.internship.exception;

public class NotCompleteProfileException extends BaseException{
    public NotCompleteProfileException(String errorMessage, String userMessage, int errorCode) {
        super(errorMessage, userMessage, errorCode);
    }
}
