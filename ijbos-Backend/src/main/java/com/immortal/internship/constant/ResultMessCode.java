package com.immortal.internship.constant;

import lombok.Getter;

public enum ResultMessCode {
    FAIL(400,"FAIL") ,
    CHANGE_PASS_SUCCESS(200, "CHANGE PASSWORD SUCCESS"),
    SUCCESS(200, "SUCCESS");

    @Getter
    private int code;
    @Getter
    private String message;

    ResultMessCode(int code, String value) {
        this.code = code;
        this.message = value;
    }
}
