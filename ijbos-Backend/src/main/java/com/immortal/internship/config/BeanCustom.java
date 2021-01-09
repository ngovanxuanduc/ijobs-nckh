package com.immortal.internship.config;

import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.exception.Base4xxException;
import com.immortal.internship.exception.InvalidParamException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanCustom {
    @Bean(name = "ExceptionCampaignRecruitmentNotExist")
    Base4xxException exceptionCampaignRecruitmentNotExist(){
        return new Base4xxException(MessageConstants.ForSystem.ENTITY_NOT_FOUND
                , MessageConstants.ForUser.RECRUITMENT_NOT_EXIST
                , MessageConstants.ResultCode.NOT_FOUND);
    }

    @Bean(name = "ExceptionAccessDenied")
    Base4xxException exceptionAccessDenied(){
        return new Base4xxException(MessageConstants.ForSystem.FORBIDDEN
                , MessageConstants.ForUser.ACCESS_DENIED
                , MessageConstants.ResultCode.NOT_PERMISSION);
    }

    @Bean(name= "ExceptionAccountNotFound")
    InvalidParamException exceptionAccountNotFound(){
        return new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                , MessageConstants.ForUser.ACCOUNT_NOT_FOUND
                , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
    }

    @Bean(name= "ExceptionCVNotFound")
    InvalidParamException exceptionCVNotFound(){
        return new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                , MessageConstants.ForUser.CV_NOT_FOUND
                , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
    }

    @Bean(name= "ExceptionDiscussNotFound")
    InvalidParamException exceptionDiscussNotFound(){
        return new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                , MessageConstants.ForUser.DISCUSS_NOT_FOUND
                , MessageConstants.ResultCode.ENTITY_NOT_FOUND);
    }



}
