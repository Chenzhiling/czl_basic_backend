package com.czl.console.backend.aop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.console.backend.aop.dao.LogMapper;
import com.czl.console.backend.aop.domain.Logging;
import com.czl.console.backend.aop.dto.LogCriteria;
import com.czl.console.backend.aop.dto.LogVo;
import com.czl.console.backend.aop.log.Log;
import com.czl.console.backend.aop.service.LogService;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.base.dozer.Convertor;
import com.czl.console.backend.security.utils.SecurityUtils;
import com.czl.console.backend.system.service.Impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Logging> implements LogService {

    private final LogMapper logMapper;

    private final Convertor convertor;

    public LogServiceImpl(LogMapper logMapper, Convertor convertor) {
        this.logMapper = logMapper;
        this.convertor = convertor;
    }

    @Override
    public void saveLog(ProceedingJoinPoint joinPoint, Logging log) {
        //获取aop切入点信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);
        // 获得调用的方法
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName()+ "()";
        // 获得用户名
        String userName = SecurityUtils.getCurrentUsername();
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        StringBuilder params = new StringBuilder();
        //将参数值写入params
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(null != argValues){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        log.setDescription(null == aopLog? "": aopLog.value());
        //类型 0-后台 1-前台
        //log.setType(null == aopLog?null:aopLog.type());
        log.setMethod(methodName);
        log.setUserName(userName);
        log.setParams(params.toString());
        logMapper.insert(log);
    }

    @Override
    public Map<String, Object> findByErrDetail(Long id) {

        QueryWrapper<Logging> loggingQueryWrapper = new QueryWrapper<>();
        loggingQueryWrapper.eq("id", id);
        Logging log = this.getOne(loggingQueryWrapper);
        if (ObjectUtils.isNull(log)) {
            return RestResponse.fail("cannot find the log record");
        }
        byte[] details = log.getExceptionDetail();
        return RestResponse.success().data(new String(ObjectUtils.isNotEmpty(details) ? details : "".getBytes()));
    }


    @Override
    public Map<String, Object> delAllLogs() {
        try {
            QueryWrapper<Logging> loggingQueryWrapper = new QueryWrapper<>();
            this.remove(loggingQueryWrapper);
            log.info("{} truncate logs success", SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error("truncate logs failed {}", e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }



    @Override
    public Map<String, Object> selectLogInfo(LogCriteria criteria) {
        QueryWrapper<Logging> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", false);
        wrapper.orderByDesc("create_time");
        if (!ObjectUtils.isEmpty(criteria.getUserName())) {
            wrapper.eq("user_name", criteria.getUserName());
        }
        if (!ObjectUtils.isEmpty(criteria.getLogType())) {
            wrapper.eq("log_type", criteria.getLogType());
        }
        if (!ObjectUtils.isEmpty(criteria.getStartTime())
                && !ObjectUtils.isEmpty(criteria.getStartTime())) {
            wrapper.between("create_time", criteria.getStartTime(), criteria.getEndTime());
        }
        try {
            Page<Logging> page = new Page<>(criteria.getCurrent(), criteria.getSize());
            Page<Logging> logPage = this.page(page, wrapper);
            List<Logging> records = logPage.getRecords();
            List<LogVo> vos = convertor.convert(records, LogVo.class);
            return RestResponse.success(vos).put("total", logPage.getTotal());
        } catch (Exception e) {
            log.error("query log failed {}", e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }
}
