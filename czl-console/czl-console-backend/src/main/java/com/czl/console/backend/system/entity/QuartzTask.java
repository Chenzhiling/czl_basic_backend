package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_quartz_job")
public class QuartzTask extends BaseEntity implements Serializable{
    public static final String JOB_KEY = "JOB_KEY";
    /**
     * Spring Bean名称
     */
    @TableField(value = "bean_name")
    private String beanName;
    /**
     * cron 表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;
    /**
     * 状态：1暂停、0启用
     */
    @TableField(value = "is_pause")
    private Boolean isPause;
    /**
     * 任务名称
     */
    @TableField(value = "job_name")
    private String jobName;
    /**
     * 方法名称
     */
    @TableField(value = "method_name")
    private String methodName;
    /**
     * 参数
     */
    @TableField(value = "params")
    private String params;
    /**
     * 备注
     */
    private String remark;
}
