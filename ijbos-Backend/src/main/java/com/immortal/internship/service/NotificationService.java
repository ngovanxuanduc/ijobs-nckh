package com.immortal.internship.service;

import com.immortal.internship.constant.NotificationConstants;
import com.immortal.internship.entity.NotificationEntity;
import com.immortal.internship.payload.request.CreateNotification;

public interface NotificationService {

    NotificationEntity createNotification(CreateNotification cNof);

    NotificationConstants.GroupID[] getGroup();
}
