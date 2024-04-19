package com.kusitms.jipbap.event.aop;

import com.kusitms.jipbap.event.model.response.EnterEventResponse;
import com.kusitms.jipbap.event.model.response.RegisterEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class EventLogAspect {

    private final EventLogService eventLogService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() {
    }

    // 이벤트 신규등록 로깅
    @AfterReturning(value = "execution(* com.kusitms.jipbap.event..*Controller.*(..)) && (putMapping())", returning = "result")
    public void eventLogging(JoinPoint joinPoint, RegisterEventResponse result) {
        log.info("[LOG][event-REGISTER] event registered - , timestamp: {}", System.currentTimeMillis());
        eventLogService.createEventRegisterLog(result, Action.REGISTER);
    }

    // 이벤트 응모 로깅
    @AfterReturning(value = "execution(* com.kusitms.jipbap.event..*Controller.*(..)) && (postMapping())", returning = "result")
    public void eventLogging(JoinPoint joinPoint, EnterEventResponse result) {
        log.info("[LOG][event-ENTER] event entered - user email: {}, timestamp: {}", result.getUserEmail(), System.currentTimeMillis());
        eventLogService.createEventEnterLog(result, Action.ENTER);
    }

    // 이벤트 에러 로깅
    @AfterThrowing(value = "execution(* com.kusitms.jipbap.event..*Controller.*(..))", throwing = "ex")
    public void eventErrorLogging(JoinPoint joinPoint, Exception ex) {
        log.info("[ERROR][event] error occurred - exception: {}, timestamp: {}", ex.getMessage(), System.currentTimeMillis());
    }
}
