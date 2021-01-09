package com.immortal.internship.payload.response;

import com.immortal.internship.entity.BaseAccountEntity;
import com.immortal.internship.entity.ImageEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AfterLoginResponse {
    private UUID id;
    private int status;
    private BaseAccountEntity inf;
    private List<NotificationLog> notificationLogList;
    private ImageEntity imageEntity;

}
