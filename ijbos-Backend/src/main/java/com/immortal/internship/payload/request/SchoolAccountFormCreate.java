package com.immortal.internship.payload.request;


import lombok.Data;

import javax.validation.constraints.*;


@Data
public class SchoolAccountFormCreate extends BaseAccountForm{

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotNull(message = "Gender must not be null")
    private boolean gender;

    @NotBlank(message = "Department must not be blank")
    private String department;
}
