package com.vortexalex.tutorials.spring.web.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledService {

    @Scheduled(initialDelay = 3000, fixedDelay=3000)
    public void trigger() {
        log.info("scheduled - start");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("scheduled - end");
    }
}
