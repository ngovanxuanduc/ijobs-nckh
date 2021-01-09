package com.immortal.internship.payload.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse<T> extends BaseResponse{
    private String userMessage;
    private String internalMessage;
    private int code;
    private T errors;

}
