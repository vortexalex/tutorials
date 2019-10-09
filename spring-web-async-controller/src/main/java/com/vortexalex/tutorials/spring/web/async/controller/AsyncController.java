package com.vortexalex.tutorials.spring.web.async.controller;

import com.vortexalex.tutorials.spring.web.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * REST controller with asynchronous method.
 */
@RestController
@RequestMapping(value = "/controller", produces = "application/json")
@Slf4j
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    @Qualifier("taskExecutor")
    private Executor executor;

    @PostMapping
    public ResponseEntity<String> start() {
        log.info("http - start");

        ScheduledThreadPoolExecutor ex = (ScheduledThreadPoolExecutor)executor;
        log.info("getActiveCount count " + ex.getActiveCount());

        if (ex.getActiveCount() > 0) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS.value()).body("Job already running");
        }

        asyncService.start();

        log.info("http - end");

        return ResponseEntity.ok().build();
    }
}



