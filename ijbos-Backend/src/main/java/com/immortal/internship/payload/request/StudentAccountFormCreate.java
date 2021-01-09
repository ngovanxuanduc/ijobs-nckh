package com.immortal.internship.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class StudentAccountFormCreate extends BaseAccountForm{

    @NotBlank(message = "Student code must not be blank")
    private String studentCode;

    @NotBlank(message = "School Year code must not be blank")
    private String schoolYear;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "Gender must not be null")
    private boolean gender;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    private String address;

    @NotBlank(message = "Department must not be blank")
    private String department;

    @NotBlank(message = "Class must not be blank")
    private String klass;
}
