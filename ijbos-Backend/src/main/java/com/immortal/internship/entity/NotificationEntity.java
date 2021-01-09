package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name ="content")
    private String content;

    @Column(name = "created_by")
    private UUID createBy;

    @Column(name = "group_id")
    private int group;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private Collection<NotificationHistoryEntity> notificationHistories;
}
