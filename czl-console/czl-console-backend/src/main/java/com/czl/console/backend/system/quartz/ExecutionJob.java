package com.czl.console.backend.system.quartz;

import com.czl.console.backend.system.entity.QuartzTask;
import com.czl.console.backend.system.service.QuartzTaskService;
import com.czl.console.backend.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/2/25
 * Description: 执行需要调度的定时任务 通过bean名称，方法名称
 */
@Slf4j
public class ExecutionJob extends QuartzJobBean {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    //需要调度的任务
    protected void executeInternal(JobExecutionContext context) {
        QuartzTask quartzTask = (QuartzTask) context.getMergedJobDataMap().get(QuartzTask.JOB_KEY);
        // 获取spring bean

        QuartzTaskService quartzTaskService = SpringContextHolder.getBean(QuartzTaskService.class);
        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            log.info("任务准备执行，任务名称：{}",quartzTask.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzTask.getBeanName(), quartzTask.getMethodName(),
                    quartzTask.getParams());
            Future<?> future = executorService.submit(task);
            future.get();
            long endTimes = System.currentTimeMillis() - startTime;
            // 任务状态
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzTask.getJobName(), endTimes);
        } catch (Exception e) {
            log.error("任务执行失败，任务名称：{}" + quartzTask.getJobName(), e);
            quartzTask.setIsPause(true);
            quartzTaskService.updateIsPause(quartzTask);
        }
    }
}
