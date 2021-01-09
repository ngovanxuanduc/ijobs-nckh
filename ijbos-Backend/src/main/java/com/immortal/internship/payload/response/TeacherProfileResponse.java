package com.immortal.internship.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherProfileResponse {

    private UUID accountId;

    private String firstName;

    private String lastName;

    private boolean gender;

    private String email;


    private String phoneNumber;


    private String address;


    private String department;


    private String roleName;


    private String createBy;


    private String updatedBy;


    private String logo;


    private String background;
}
