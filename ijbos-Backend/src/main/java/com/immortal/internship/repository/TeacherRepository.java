package com.immortal.internship.repository;


import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.entity.TeacherEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

    Optional<TeacherEntity> findById(UUID id);

//    @Query(value = "select * from teacher t inner join account a on t.account_id = a.id where a.active>0", nativeQuery = true)
//    List<TeacherEntity> findAllTeacherProfileSortByPage(Pageable Pageable);

    @Query(value = "select * from teacher s inner join account a on a.id = s.account_id where a.active >0", nativeQuery = true)
    List<TeacherEntity> findAllTeacherProfileSortByPage(Pageable pageable);
}
