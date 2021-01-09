package com.immortal.internship.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileResponse {
    private UUID accountId;
    private String studentCode;
    private String schoolYear;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private boolean gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String department;
    private String klass;
    private String roleName;
    private String createBy;
    private String updatedBy;
    private String logo;
    private String background;

}
