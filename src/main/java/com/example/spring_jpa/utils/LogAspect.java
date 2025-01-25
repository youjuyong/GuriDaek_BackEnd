package com.example.spring_jpa.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("within(com.example.spring_jpa.api.v1.*)")
    public void controller() {
    }

    @Before("controller()")
    public void beforeController(JoinPoint joinPoint) {
        log.info("Start Request - {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "controller()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("End Request - {}", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "controller()", throwing = "ex")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception ex) {
        log.error("Error In Request - {}", joinPoint.getSignature().toShortString());
        log.error("\t{}", ex.getMessage());
    }
}
