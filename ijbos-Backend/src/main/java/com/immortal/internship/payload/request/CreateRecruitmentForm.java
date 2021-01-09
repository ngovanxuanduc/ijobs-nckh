package com.immortal.internship.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CreateRecruitmentForm {

    @NotBlank(message = "Tittle must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotEmpty(message = "skills not empty")
    private List<Integer> skills;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    @NotBlank(message = "CampaignName must not be blank")
    private String campaignName;

    //@NotEmpty(message = "skills not empty")
    @NotNull(message = "location not null")
    private Boolean location;

    @NotBlank(message = "Location description must not be blank")
    private String locationDescription;

    private String image;
}
