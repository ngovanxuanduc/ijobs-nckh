package com.immortal.internship.repository;

import com.immortal.internship.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
    Optional<ImageEntity> findById(UUID id);
}