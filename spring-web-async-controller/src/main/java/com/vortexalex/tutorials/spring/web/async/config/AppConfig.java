package com.vortexalex.tutorials.spring.web.async.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
public class AppConfig implements SchedulingConfigurer {

    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(1, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.info("rejected" + r.toString());
            }
        });
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }


}
