package com.immortal.internship.service;

import com.immortal.internship.entity.RecruitmentEntity;
import com.immortal.internship.payload.request.CreateRecruitmentForm;
import com.immortal.internship.payload.response.DetailsCampaignRecruitmentResponse;
import com.immortal.internship.payload.response.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RecruitmentService {

    /**
     *
     * @param pageNumber
     * @param pageSize defaultValue = "5"
     * @return list of recruitment by sort
     */
    PageResponse getCampaignRecruitmentSortByPage(Integer pageNumber
            , Integer pageSize, String sortBy, String typeSort);

    /**
     *
     * @param createRecruitmentRequest
     * @return recruitment which was created
     */
    RecruitmentEntity createCampaignRecruitment(CreateRecruitmentForm createRecruitmentRequest);

    /**
     *
     * @param update
     * @return
     */
    RecruitmentEntity updateRecruitment(String idUpdate ,CreateRecruitmentForm update);

    String deleteRecruitment(String id);

    String approveRecruitment(String id);

    DetailsCampaignRecruitmentResponse getCampaignRecruitmentByID(String idCR);

    List<RecruitmentEntity> getRecruitmentsByCreateBy(UUID id);

    /**
     * Recruitment public for anonymous user, sort by time create
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResponse getRecruitmentPublic(Integer pageNumber, Integer pageSize);

    RecruitmentEntity getRecruitmentById(String idCr);
}
