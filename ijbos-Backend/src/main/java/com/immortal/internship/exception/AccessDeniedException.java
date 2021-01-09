package com.immortal.internship.exception;

public class AccessDeniedException extends BaseException{
    public AccessDeniedException(String errorMessage, String userMessage, int errorCode) {
        super(errorMessage, userMessage, errorCode);
    }
}
