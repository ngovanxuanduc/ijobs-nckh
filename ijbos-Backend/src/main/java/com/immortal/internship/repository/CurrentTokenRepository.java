package com.immortal.internship.repository;

import com.immortal.internship.entity.CurrentTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CurrentTokenRepository extends JpaRepository<CurrentTokenEntity,String> {
    Optional<CurrentTokenEntity> findByUserName(String userName);
    Optional<CurrentTokenEntity> findByWebTokenOrMobileToken(String token, String mtoken);
}
