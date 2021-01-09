package com.immortal.internship.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SearchCVResponse {
    private String cvId;
    private UUID accountId;
    private String fullName;
    private String schoolYear;
    private String email;
    private String logo;
    private String background;
}
