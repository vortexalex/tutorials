package com.vortexalex.tutorials.spring.webflux.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Locale;
import java.util.Random;

@Component
public class MyPublisher {

    private RandomNumberGenerator generator = new RandomNumberGenerator(0, 10);

    public Flux<MyItem> events() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(v -> new MyItem(generator.get()));
    }

    @RequiredArgsConstructor
    private static class RandomNumberGenerator {
        private final int lower;

        private final int upper;

        private int get() {
            return (int)(lower + new Random().nextDouble() * (upper - lower));
        }
    }

}
