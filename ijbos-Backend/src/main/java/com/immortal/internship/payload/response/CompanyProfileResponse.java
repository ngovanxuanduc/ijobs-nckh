package com.immortal.internship.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileResponse {
    private UUID accountId;
    private String name;
    private String companyInfo;
    private String location;
    private String phoneNumber;
    private String email;
    private String workingDay;
    private String country;
    private String overView;
    private String roleName;
    private String createBy;
    private String updatedBy;
    private String logo;
    private String background;
}
