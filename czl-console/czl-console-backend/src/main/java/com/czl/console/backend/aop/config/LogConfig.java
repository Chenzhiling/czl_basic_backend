package com.czl.console.backend.aop.config;


import com.czl.console.backend.aop.domain.Logging;
import com.czl.console.backend.aop.service.LogService;
import com.czl.console.backend.utils.ThrowableUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
@Aspect
public class LogConfig {


    private final LogService logService;

    public LogConfig(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.czl.console.backend.aop.log.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        result = joinPoint.proceed();
        Logging log = new Logging("INFO");
        logService.saveLog(joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logging log = new Logging("ERROR");
        log.setExceptionDetail(ThrowableUtils.extractStackTrace(e).getBytes(StandardCharsets.UTF_8));
        logService.saveLog((ProceedingJoinPoint) joinPoint, log);
    }
}