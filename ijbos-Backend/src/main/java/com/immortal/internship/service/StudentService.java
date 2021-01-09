package com.immortal.internship.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    String applyCampaignRecruitment(String idCR);

    String bookMarkCampaignRecruitment(String idCR);

    String unApplyCampaignRecruitment(String idCR);

    String unBookMarkCampaignRecruitment(String idCR);

    List<String> bookMarks();

    List<String> listApply();
}
