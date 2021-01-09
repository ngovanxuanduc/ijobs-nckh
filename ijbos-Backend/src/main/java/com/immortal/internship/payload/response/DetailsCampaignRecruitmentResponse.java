package com.immortal.internship.payload.response;

import com.immortal.internship.model.ShortInformationAccount;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetailsCampaignRecruitmentResponse {
    private CampaignRecruitmentResponse campaignRecruitment;

    private List<ShortInformationAccount> students;

}
