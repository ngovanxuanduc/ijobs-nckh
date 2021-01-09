package com.immortal.internship.repository;

import com.immortal.internship.entity.CVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface CVRepository extends JpaRepository<CVEntity, String> {
    CVEntity findByStudentIDAndActiveGreaterThanEqual(UUID studentId, @Param("active") Boolean active);
    CVEntity findByStudentID(UUID studentId);
    Boolean existsByStudentID(UUID studentId);
    CVEntity findByStudentIDAndApprovedByEquals(UUID studentID, UUID approveID);
}
