package com.immortal.internship.exception;

public class InvalidParamException extends BaseException {

    public InvalidParamException(String errorMessage, String userMessage, int errorCode) {
        super(errorMessage, userMessage, errorCode);
    }
}
