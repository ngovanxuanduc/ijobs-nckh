package com.immortal.internship.repository;

import com.immortal.internship.entity.DiscussContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussContentRepository extends JpaRepository<DiscussContentEntity, Long> {
    Optional<List<DiscussContentEntity>> findAllByDiscussIDOrderByCreatedAtDesc(String discussId);

}
