package com.immortal.internship.entity.key;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
@Embeddable
@Data
public class NotificationHistoryKey implements Serializable {
    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "account_id")
    private UUID accountId;
}
