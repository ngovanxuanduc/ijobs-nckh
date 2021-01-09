package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.entity.NotificationHistoryEntity;
import com.immortal.internship.payload.response.AfterLoginResponse;
import com.immortal.internship.payload.response.NotificationLog;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.CommonService;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private DBContext dbContext;

    @Autowired
    private UserProvider userProvider;

    @Override
    public AfterLoginResponse loadInformation() {
        AfterLoginResponse inf = new AfterLoginResponse();
        UserPrinciple currentUser = userProvider.getCurrentUser();
        inf.setId(currentUser.getAccount().getId());
        inf.setStatus(currentUser.getAccount().getStatus());
        inf.setInf(currentUser.getAccount().getInformationAccount());
        inf.setImageEntity(currentUser.getAccount().getImageEntity());
        inf.setNotificationLogList(getNotificationLog(currentUser));
        return inf;
    }

    private NotificationLog parseNotificationLog(NotificationHistoryEntity log) {
        return NotificationLog.builder()
                .id(log.getNotification().getId())
                .content(log.getNotification().getContent())
                .title(log.getNotification().getTitle())
                .status(log.getStatus())
                .build();
    }

    private List<NotificationLog> getNotificationLog(UserPrinciple user) {
        return dbContext.notificationHistoryRepository.findByAccount_Id(user.getAccount().getId())
                .map(hLogList -> hLogList
                        .stream()
                        .map(hLog -> parseNotificationLog(hLog))
                        .collect(Collectors.toList())
                )
                .orElseGet(ArrayList::new);

    }
}
