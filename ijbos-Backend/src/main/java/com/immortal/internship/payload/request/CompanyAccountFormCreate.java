package com.immortal.internship.payload.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CompanyAccountFormCreate extends BaseAccountForm{

    @NotBlank(message = "Company name must not be blank")
    private String name;

    @NotBlank(message = "Company infor must not be blank")
    private String companyInfo;

    @NotBlank(message = "Company location must not be blank")
    private String location;

    @Pattern(regexp="(^$|[0-9]{10})")
    @NotBlank(message = "Company phone must not be blank")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Company email must not be blank")
    private String email;

    private String workingDate;

    @NotBlank(message = "Country must not be blank")
    private String country;

    @NotBlank(message = "Overview must not be blank")
    private String overView;

}
