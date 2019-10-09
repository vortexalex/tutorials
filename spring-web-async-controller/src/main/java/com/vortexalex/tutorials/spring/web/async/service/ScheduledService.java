package com.vortexalex.tutorials.spring.web.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledService {

    @Autowired
    private EndService endService;

    @Scheduled(cron = "${job.scheduling.cronexpression}")
    public void trigger() {
        log.info("scheduled - start");

        endService.call();

        log.info("scheduled - end");
    }
}
