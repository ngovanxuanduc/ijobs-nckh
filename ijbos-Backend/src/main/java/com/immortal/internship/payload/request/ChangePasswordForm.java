package com.immortal.internship.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordForm {

    @Size(min = 8, max = 50, message = "Old password must be between 8 and 50 characters")
    @NotBlank(message = "Old password must not be blank")
    private String oldPassword;

    @Size(min = 8, max = 50, message = "New password must be between 8 and 50 characters")
    @NotBlank(message = "New password must not be blank")
    private String newPassword;

}
