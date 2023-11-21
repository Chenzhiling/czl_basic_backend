package com.czl.console.backend.system.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/11/18
 * Description: 将quartz注入到bean中
 */
@Configuration
public class QuartzConfig {
    @Resource
    private JobFactory jobFactory;
    /**
     * 配置SchedulerFactoryBean
     *
     * 将一个方法产生为Bean并交给Spring容器管理
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        // Spring提供SchedulerFactoryBean为Scheduler提供配置信息,并被Spring容器管理其生命周期
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 设置自定义Job Factory，用于Spring管理Job bean
        factory.setJobFactory(jobFactory);
        return factory;
    }

    //配置调度器
    //一个调度容器中可以注册多个 JobDetail 和 Trigger
    @Bean(name = "scheduler")
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.start();
        return scheduler;
    }
}
