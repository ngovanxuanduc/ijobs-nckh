package com.immortal.internship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BaseStatistical {
    private UUID companyId;
    private String companyName;
    private String phoneNumber;
    private String email;
    private String location;
    private int numOfStudent;
}
