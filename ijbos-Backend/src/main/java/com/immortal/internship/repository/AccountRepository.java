package com.immortal.internship.repository;

import com.immortal.internship.entity.AccountEntity;
import com.immortal.internship.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUserName(String userName);

    AccountEntity findByIdAndStatusGreaterThan(UUID idAccount, int status);

    Optional<List<AccountEntity>> findAllByStatusGreaterThan(int status);

    boolean existsByUserName(String userName);


}
