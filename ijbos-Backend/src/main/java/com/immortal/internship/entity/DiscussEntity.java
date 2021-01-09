package com.immortal.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "discuss")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscussEntity {
    @Id
    private String id;

    @Column(name = "recruitment_id")
    private String recruitmentID;

    @Column(columnDefinition = "BINARY(16)", name = "owner")
    private UUID owner;

    @Column(columnDefinition = "BINARY(16)", name = "receiver")
    private UUID receiver;

    @Column(name = "status")
    private int status;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
