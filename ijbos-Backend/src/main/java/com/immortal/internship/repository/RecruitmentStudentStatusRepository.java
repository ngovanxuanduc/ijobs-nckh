package com.immortal.internship.repository;

import com.immortal.internship.entity.RecruitmentStudentStatusEntity;
import com.immortal.internship.entity.key.RecruitmentStudentStatusKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecruitmentStudentStatusRepository extends JpaRepository<RecruitmentStudentStatusEntity, RecruitmentStudentStatusKey> {
    List<RecruitmentStudentStatusEntity> findAllByRecruitment_Id(String idCR);
    Optional<RecruitmentStudentStatusEntity> findByRecruitment_IdAndStudent_Id(String idCR, UUID idST);
    long countAllByRecruitment_IdAndStateGreaterThanEqual(String idCR, int st);
    List<RecruitmentStudentStatusEntity> findAllByRecruitment_IdAndStateGreaterThanEqual(String idCR, int st);
    List<RecruitmentStudentStatusEntity> findAllByStudent_Id(UUID idST);
    RecruitmentStudentStatusEntity findByStudentIdAndRecruitmentIdAndStateGreaterThanEqual(UUID stID, String reID, int st);
}
