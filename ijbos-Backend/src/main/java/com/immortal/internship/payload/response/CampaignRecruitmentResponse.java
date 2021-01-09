package com.immortal.internship.payload.response;


import com.immortal.internship.entity.BaseAccountEntity;
import com.immortal.internship.entity.SkillsEntity;
import com.immortal.internship.model.ShortInformationAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRecruitmentResponse {

    private String id;

    private String title;

    private int status;

    private String description;

    private Date startTime;

    private Date endTime;

    private String campaignName;

    private Boolean location;

    private String locationDescription;

    private BaseAccountEntity createdBy;

    private String approvedBy;

    private String imageUrl;

    private Collection<SkillsEntity> skills;

    private long numberOfStudentApply;

    private Date createdAt;

    private ShortInformationAccount shortInfoAccount;

    private int studentBookMarkApply;

    @Override
    public String toString() {
        return "CampaignRecruitmentResponse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", campaignName='" + campaignName + '\'' +
                ", location=" + location +
                ", locationDescription='" + locationDescription + '\'' +
                ", createdBy=" + createdBy +
                ", approvedBy='" + approvedBy + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", skills=" + skills +
                '}';
    }

}
