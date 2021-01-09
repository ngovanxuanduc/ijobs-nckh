package com.immortal.internship.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentModel {
    private int id;
    private String title;
    private Boolean active;
    private String description;
    private List skills;
    private Date startTime;
    private String campaignName;
    private Boolean location;
    private String locationDescription;
    private Integer createdBy;
    private Integer approvedBy;
    private Boolean isDelete;
}
