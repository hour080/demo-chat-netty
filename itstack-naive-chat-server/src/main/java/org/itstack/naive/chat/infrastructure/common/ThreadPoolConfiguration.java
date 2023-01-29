package org.itstack.naive.chat.infrastructure.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/29 10:02
 */
@Configuration
public class ThreadPoolConfiguration {
    //创建一个拒绝策略
    @Bean
    public RejectedExecutionHandler rejectedExecutionHandler(){
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }
    @Bean
    public ThreadPoolTaskExecutor taskExecutor(RejectedExecutionHandler rejectedExecutionHandler){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50); //有40个救急线程
        taskExecutor.setKeepAliveSeconds(60); //救急线程的最大存活时间
        taskExecutor.setQueueCapacity(500); //如果queueCapacity大于0，则是LinkedBlockingQueue
        taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        return taskExecutor;
    }
}
