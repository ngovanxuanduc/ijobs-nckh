package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.NotificationConstants;
import com.immortal.internship.entity.NotificationEntity;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.payload.request.CreateNotification;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.EmailService;
import com.immortal.internship.service.NotificationService;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.NotificationValidationUtil;
import com.immortal.internship.utility.RandomIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private DBContext dbContext;
    @Autowired
    private EmailService emailService;

    @Override
    public NotificationEntity createNotification(CreateNotification cNof) {

        validParamRequest(NotificationValidationUtil.isValidateGroupID(cNof.getGroupId()),MessageConstants.ForUser.GROUP_NOT_EXIST);

        NotificationEntity nof = NotificationEntity.builder()
                .id(generateIDNotification())
                .title(cNof.getTitle())
                .content(cNof.getOverView())
                .createBy(getCurrentAccount().getAccount().getId())
                .group(cNof.getGroupId())
                .build();
        //Gui mail:
        String[] emailAddress = dbContext.customRepository.getEmailByGroupID(cNof.getGroupId());
        if (emailAddress.length >0 ){
            emailService.sendMessageWithAttachment(cNof.getTitle(), cNof.getOverView()
                    , emailAddress);
        }
        return dbContext.notificationRepository.save(nof);
    }

    @Override
    public NotificationConstants.GroupID[] getGroup() {
        return NotificationConstants.groupIDS;
    }

    private void validParamRequest(boolean validatedParam, String messErrorForUser) {
        if (!validatedParam) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    , messErrorForUser
                    , MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS);
        }
    }

    private String generateIDNotification() {
        String res;
        do {
            res = RandomIDUtil.getGeneratedString();
        } while (dbContext.notificationRepository.existsById(res));
        return res;
    }

    private UserPrinciple getCurrentAccount() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
