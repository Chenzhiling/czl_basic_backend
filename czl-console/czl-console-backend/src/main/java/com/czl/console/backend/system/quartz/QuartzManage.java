package com.czl.console.backend.system.quartz;

import com.czl.console.backend.system.entity.QuartzTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/2/25
 * Description: 构建一个定时任务，放入schedule中
 */
@Component
@Slf4j
public class QuartzManage {
    private static final String JOB_NAME = "TASK_";
    @Resource(name = "scheduler")
    private Scheduler scheduler;

    //将定时任务放入到job中
    public void addJob(QuartzTask quartzTask){
        //根据数据定时任务，构建jobDetail
        try {
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(JOB_NAME + quartzTask.getId()).build();

            //根据cron表达式，构建触发器
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JOB_NAME + quartzTask.getId())
                    .startNow()
                    //获取cron表达式
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzTask.getCronExpression()))
                    .build();
            //一个job对于一个触发器
            cronTrigger.getJobDataMap().put(QuartzTask.JOB_KEY, quartzTask);

            //重置启动时间
            ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

            //将任务和触发器放入schedule,执行定时任务
            //需要放入bean中
            scheduler.scheduleJob(jobDetail,cronTrigger);
            if (quartzTask.getIsPause()) {
                pauseJob(quartzTask);
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
        }
    }



    /**
     * 暂停一个job
     * @param quartzTask 定时任务
     */
    public void pauseJob(QuartzTask quartzTask){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzTask.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e){
            log.error("定时任务暂停失败", e);
        }
    }
}
