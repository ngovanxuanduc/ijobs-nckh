package com.immortal.internship.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private String userMessage;
    private String errorMessage;
    private int statusCode;

    public BaseException(String errorMessage, String userMessage,int errorCode){
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.statusCode = errorCode;
        this.userMessage = userMessage;
    }
}
