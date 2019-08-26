package com.vortexalex.tutorials.spring.web.async.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller with asynchronous method.
 */
@RestController
@RequestMapping(value = "/controller", produces = "application/json")
@Slf4j
public class AsyncController {

    @PostMapping
    @Async
    public ResponseEntity start() {
        log.info("start");

        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            log.warn("interrupted");
        }
        log.info("end");

        return ResponseEntity.badRequest().build();
    }
}



