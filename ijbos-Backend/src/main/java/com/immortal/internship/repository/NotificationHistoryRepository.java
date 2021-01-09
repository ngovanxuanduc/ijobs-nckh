package com.immortal.internship.repository;

import com.immortal.internship.entity.NotificationHistoryEntity;
import com.immortal.internship.entity.key.NotificationHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity, NotificationHistoryKey> {
    Optional<List<NotificationHistoryEntity>> findAllByAccount_Id(UUID id);
    Optional<List<NotificationHistoryEntity>> findByAccount_Id(UUID id);
}
