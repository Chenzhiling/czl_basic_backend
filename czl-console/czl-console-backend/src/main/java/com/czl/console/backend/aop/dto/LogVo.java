package com.czl.console.backend.aop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/26
 * Description:
 */
@Data
public class LogVo {

    private Long id;

    /** 描述 */
    private String description;

    /** 用户名 */
    private String userName;

    /** 方法名 */
    private String method;

    /** 日志类型 */
    private String logType;

    /** 入参 */
    private String params;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

}
