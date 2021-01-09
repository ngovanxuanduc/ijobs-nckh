package com.immortal.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "discuss_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscussContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "discuss_id")
    private String discussID;

    @Column(columnDefinition = "BINARY(16)", name = "owner")
    private UUID owner;

    @Column(name = "content")
    private String content;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
