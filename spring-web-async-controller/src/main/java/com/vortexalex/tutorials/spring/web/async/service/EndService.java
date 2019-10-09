package com.vortexalex.tutorials.spring.web.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EndService {

    public void call() {
        log.info("end service - start");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("end service - end");

    }
}
