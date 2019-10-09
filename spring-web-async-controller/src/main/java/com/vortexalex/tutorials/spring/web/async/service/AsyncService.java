package com.vortexalex.tutorials.spring.web.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

    @Autowired
    private EndService endService;

    @Async
    public void start() {
        log.info("async - start");

        endService.call();

        log.info("async - end")  ;
    }
}
