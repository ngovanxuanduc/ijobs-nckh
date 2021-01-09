package com.immortal.internship.repository;

import com.immortal.internship.entity.RecruitmentEntity;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, String> {
//    Optional<List<RecruitmentEntity>> findAllByCreatedByAndStatusGreaterThanOrderByStartTimeDesc(UUID id, int status);
//
//    Optional<List<RecruitmentEntity>> findAllByAndStatusGreaterThanOrderByStartTimeDesc(int status);
//
//    Optional<List<RecruitmentEntity>> findAllByStatusOrderByStartTimeDesc(int status);

//    Optional<List<RecruitmentEntity>> findAllByCreatedByAndStatusGreaterThan(UUID id, int status, Pageable pageable);

    Page<RecruitmentEntity> findAllByCreatedByAndStatusGreaterThan(UUID id, int status, Pageable pageable);


    //    Optional<List<RecruitmentEntity>> findAllByAndStatusGreaterThan(int status, Pageable pageable);
    Page<RecruitmentEntity> findAllByAndStatusGreaterThan(int status, Pageable pageable);

    //    Optional<List<RecruitmentEntity>> findAllByStatus(int status, Pageable pageable);
    Page<RecruitmentEntity> findAllByStatus(int status, Pageable pageable);


    boolean existsById(String id);

    Optional<RecruitmentEntity> findByIdAndCreatedBy(String id, UUID createBy);

    RecruitmentEntity findByIdAndStatusEquals(String id, int status);

    List<RecruitmentEntity> findAllByCreatedByAndStatusGreaterThanEqual(UUID id, int status);


}
