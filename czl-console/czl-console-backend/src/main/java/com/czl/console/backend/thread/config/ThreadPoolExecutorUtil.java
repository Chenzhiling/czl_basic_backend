package com.czl.console.backend.thread.config;

import com.czl.console.backend.utils.SpringContextHolder;

import java.util.concurrent.*;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/4
 * Description:
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getPoll(String threadName){
        AsyncTaskProperties properties = SpringContextHolder.getBean(AsyncTaskProperties.class);
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new TheadFactoryName(threadName),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }


    public static ScheduledExecutorService getScheduledThreadPool() {
        return new ScheduledThreadPoolExecutor(1,new TheadFactoryName("alert-pool"));
    }
}
