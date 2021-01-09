package com.immortal.internship.exception;


public class InvalidCurrentUserException extends BaseException{

    public InvalidCurrentUserException(String errorMessage, String userMessage, int errorCode) {
        super(errorMessage, userMessage, errorCode);
    }
}
