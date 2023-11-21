package com.czl.console.backend.aop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "sys_log")
public class Logging extends BaseEntity {

    /** 描述 */
    @TableField(value = "description")
    private String description;

    /** 用户名 */
    @TableField(value = "user_name")
    private String userName;

    /** 异常详细  */
    @TableField(value = "exception_detail")
    private byte[] exceptionDetail;

    /** 方法名 */
    @TableField(value = "method")
    private String method;

    /** 日志类型 */
    @TableField(value = "log_type")
    private String logType;

    /** 入参 */
    @TableField(value = "params")
    private String params;

    public Logging(String logType) {
        this.logType = logType;
    }
}
