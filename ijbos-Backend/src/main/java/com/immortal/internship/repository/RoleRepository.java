package com.immortal.internship.repository;

import com.immortal.internship.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
}
