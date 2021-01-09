package com.immortal.internship.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCVResponse {
    private UUID accountId;
    private String studentCode;
    private String schoolYear;
    private String firstName;
    private String lastName;
    private boolean active;
}
