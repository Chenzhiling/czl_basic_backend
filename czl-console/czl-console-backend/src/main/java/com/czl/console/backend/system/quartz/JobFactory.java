package com.czl.console.backend.system.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/11/18
 * Description:
 */
// 解决SpringBoot不能再Quartz中注入Bean的问题
@Component("JobFactory")
public class JobFactory extends AdaptableJobFactory {

    private final AutowireCapableBeanFactory factory;

    public JobFactory(AutowireCapableBeanFactory factory) {
        this.factory = factory;
    }

    /**
     * 创建Job实例,注入spring
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 实例化对象
        Object job = super.createJobInstance(bundle);
        // 进行注入（Spring管理该Bean）
        factory.autowireBean(job);
        //返回对象
        return job;
    }
}
