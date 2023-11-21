package com.czl.console.backend.aop.service;

import com.czl.console.backend.aop.domain.Logging;
import com.czl.console.backend.aop.dto.LogCriteria;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Map;

public interface LogService {

    /**
     * 保存日志
     */
    void saveLog(ProceedingJoinPoint joinPoint, Logging log);

    /**
     * 查询异常详情
     * @param id 日志ID
     */
    Map<String, Object> findByErrDetail(Long id);


    /**
     * 删除所有错误日志
     */
    Map<String, Object> delAllLogs();


    /**
     * 高级筛选用户日志
     */
    Map<String, Object> selectLogInfo(LogCriteria criteria);
}
