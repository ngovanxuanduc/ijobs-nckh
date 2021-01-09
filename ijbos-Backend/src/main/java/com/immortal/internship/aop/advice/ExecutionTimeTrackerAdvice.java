package com.immortal.internship.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

    private Logger logger = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);

    @Around("@annotation(com.immortal.internship.aop.annotation.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        long statTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Method name: "+pjp.getSignature() + " | Time take to execute: " +(endTime-statTime));
        return obj;
    }

}
