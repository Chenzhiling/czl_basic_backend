package com.czl.console.backend.aop.dto;

import com.czl.console.backend.base.domain.BaseCriteria;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class LogCriteria extends BaseCriteria {

    private String userName;

    private String logType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Timestamp startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Timestamp endTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getLogType() {
        return logType;
    }
}
