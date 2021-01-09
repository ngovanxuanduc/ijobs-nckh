package com.immortal.internship.repository;

import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.payload.response.StudentProfileResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentEntity,UUID> {

    Optional<List<StudentEntity>> findAllById(int status);

}
