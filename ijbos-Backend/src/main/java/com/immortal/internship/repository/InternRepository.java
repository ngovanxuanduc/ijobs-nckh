package com.immortal.internship.repository;

import com.immortal.internship.entity.InternEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface InternRepository extends JpaRepository<InternEntity, String> {

}
