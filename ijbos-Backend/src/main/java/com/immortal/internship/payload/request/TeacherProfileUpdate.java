package com.immortal.internship.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class TeacherProfileUpdate {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "First name must not be blank")
    private String lastName;

    @NotNull(message = "Gender must not be null")
    private boolean gender;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Department must not be blank")
    private String department;

    private String logo;

    private String background;
}
