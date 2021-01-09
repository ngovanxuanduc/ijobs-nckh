package com.immortal.internship.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginForm {
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotNull(message = "System Type Not Null")
    private Integer systemType;
}
