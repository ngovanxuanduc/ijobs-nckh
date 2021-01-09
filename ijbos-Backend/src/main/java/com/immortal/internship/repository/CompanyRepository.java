package com.immortal.internship.repository;


import com.immortal.internship.entity.CompanyEntity;
import com.immortal.internship.entity.StudentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    @Query(value = "select * from company c inner join account a on a.id = c.account_id where a.active>0", nativeQuery = true)
    List<CompanyEntity> findAllCompanyProfileSortByPage(Pageable pageable);
}
