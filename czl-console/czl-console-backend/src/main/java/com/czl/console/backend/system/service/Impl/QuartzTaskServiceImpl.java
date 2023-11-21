package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czl.console.backend.system.dao.QuartzTaskMapper;
import com.czl.console.backend.system.entity.QuartzTask;
import com.czl.console.backend.system.quartz.QuartzManage;
import com.czl.console.backend.system.service.QuartzTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2021/2/25
 * Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskServiceImpl extends BaseServiceImpl<QuartzTaskMapper, QuartzTask> implements QuartzTaskService {

    private final QuartzTaskMapper quartzTaskMapper;

    private final QuartzManage quartzManage;

    public QuartzTaskServiceImpl(QuartzTaskMapper quartzTaskMapper,
                                 QuartzManage quartzManage) {
        this.quartzTaskMapper = quartzTaskMapper;
        this.quartzManage = quartzManage;
    }

    @Override
    public List<QuartzTask> findByIsPauseIsFalse() {
        QueryWrapper<QuartzTask> wrapper = new QueryWrapper<>();
        wrapper.eq("is_pause",false);
        return quartzTaskMapper.selectList(wrapper);
    }

    @Override
    public void updateIsPause(QuartzTask quartzTask) {
        if (quartzTask.getIsPause()) {
            quartzManage.pauseJob(quartzTask);
        }
        try {
            this.updateById(quartzTask);
            log.info("update quartz task success");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
