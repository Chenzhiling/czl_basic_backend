package com.czl.console.backend.system.quartz;


import com.czl.console.backend.system.entity.QuartzTask;
import com.czl.console.backend.system.service.QuartzTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/2/25
 * Description:
 */
@Component
@Slf4j
public class JobRunner implements ApplicationRunner {

    private final QuartzTaskService quartzService;

    private final QuartzManage quartzManage;

    public JobRunner(QuartzTaskService quartzService, QuartzManage quartzManage) {
        this.quartzService = quartzService;
        this.quartzManage = quartzManage;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("----------------Scheduled task add start--------------------");
        List<QuartzTask> quartzTasks = quartzService.findByIsPauseIsFalse();
        quartzTasks.forEach(quartzManage::addJob);
        log.info("----------------Scheduled task add end----------------------");

    }
}
