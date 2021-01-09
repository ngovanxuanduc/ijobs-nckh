package com.immortal.internship.repository;

import com.immortal.internship.entity.SkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SkillsRepository extends JpaRepository<SkillsEntity, Integer> {
    Optional<SkillsEntity> findById(Integer id);
    boolean existsBySkillName(String skill);
    Optional<SkillsEntity> findBySkillName(String skillName);
}
