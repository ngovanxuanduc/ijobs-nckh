package com.immortal.internship.payload.request;

import com.immortal.internship.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class BaseAccountForm {

    @NotBlank(message = "user name must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotEmpty(message = "Roles cannot be empty.")
    private List<Integer> roles;

    private String imageLogoURL;

    private String imageBackgroundURL;
}
