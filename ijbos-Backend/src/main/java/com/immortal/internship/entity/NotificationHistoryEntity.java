package com.immortal.internship.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.immortal.internship.entity.key.NotificationHistoryKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notification_account_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationHistoryEntity {

    @EmbeddedId
    private NotificationHistoryKey id = new NotificationHistoryKey();

    @ManyToOne
    @MapsId("notificationId")
    @JoinColumn(name = "notification_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NotificationEntity notification;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    private AccountEntity account;

    @Column(name = "status")
    private int status;

   
}

