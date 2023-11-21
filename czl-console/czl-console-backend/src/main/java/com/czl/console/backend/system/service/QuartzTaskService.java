package com.czl.console.backend.system.service;

import com.czl.console.backend.system.entity.QuartzTask;

import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/2/25
 * Description:
 */
public interface QuartzTaskService {


    /**
     * 查询启用的任务
     * @return List
     */
    List<QuartzTask> findByIsPauseIsFalse();



    /**
     * 更新任务
     */
    void updateIsPause(QuartzTask quartzTask);
}
