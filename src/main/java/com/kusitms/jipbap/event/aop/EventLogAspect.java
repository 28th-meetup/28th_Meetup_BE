package com.kusitms.jipbap.event.aop;

import com.kusitms.jipbap.event.model.response.EntryEventResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EventLogAspect {

    @AfterReturning(value = "execution(* com.kusitms.jipbap.event..*Controller.*(..))", returning = "result")
    public void eventLogging(JoinPoint joinPoint, EntryEventResponse result) {
        log.info("[LOG][event] event entered - user email: {}, timestamp: {}", result.getUserEmail(), System.currentTimeMillis());
    }

    @AfterThrowing(value = "execution(* com.kusitms.jipbap.event..*Controller.*(..))", throwing = "ex")
    public void eventErrorLogging(JoinPoint joinPoint, Exception ex) {
        log.info("[ERROR][event] error occurred - exception: {}, timestamp: {}", ex.getMessage(), System.currentTimeMillis());
    }
}
