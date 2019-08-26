package com.vortexalex.tutorials.spring.web.async.controller;

import com.vortexalex.tutorials.spring.web.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller which calls an asynchronous service.
 */
@RestController
@RequestMapping(value = "/service", produces = "application/json")
@Slf4j
public class BasicController {

    @Autowired
    private AsyncService asyncService;

    @PostMapping
    public ResponseEntity start() {
        log.info("start");

        asyncService.start();

        log.info("end");

        return ResponseEntity.ok().build();
    }
}
