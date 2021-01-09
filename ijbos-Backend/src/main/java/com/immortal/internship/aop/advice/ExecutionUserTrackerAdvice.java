package com.immortal.internship.aop.advice;

import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.exception.NotCompleteProfileException;
import com.immortal.internship.utility.UserProvider;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionUserTrackerAdvice {

    private Logger logger = LoggerFactory.getLogger(ExecutionUserTrackerAdvice.class);

    @Autowired
    private UserProvider user;

    @Before("@annotation(com.immortal.internship.aop.annotation.TrackExecutionUser)")
    public void checkUserProfileComplete() throws NotCompleteProfileException {
        logger.info("User Name: "+user.getCurrentUser().getUsername()
                + " | Profile Complete: "+user.getCurrentUser().isProfileComplete() );
        if (!user.getCurrentUser().isProfileComplete()){
            throw new NotCompleteProfileException(MessageConstants.ForSystem.NOT_COMPLETE_PROFILE
                    ,MessageConstants.ForUser.NOT_COMPLETE_PROFILE
                    ,MessageConstants.ResultCode.PROFILE_NOT_COMPLETE);
        }
    }

}
