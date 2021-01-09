package com.immortal.internship.repository;

import com.immortal.internship.entity.DiscussEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscussRepository extends JpaRepository<DiscussEntity, String> {
    boolean existsDistinctByIdAndStatusEquals(String id, int status);
    Optional<List<DiscussEntity>> findAllByOwner(UUID id);
    Optional<List<DiscussEntity>> findAllByOwnerOrReceiver(UUID id1,UUID id2);
    //boolean existsDiscussEntitiesByOwnerAndReceiverAndRecruitmentID(UUID owner, UUID receiver,String idRecruitment);
    DiscussEntity findByOwnerAndReceiverAndRecruitmentID(UUID owner, UUID receiver,String idRecruitment);
}
