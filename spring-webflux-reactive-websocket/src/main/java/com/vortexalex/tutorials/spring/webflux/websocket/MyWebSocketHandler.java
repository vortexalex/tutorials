package com.vortexalex.tutorials.spring.webflux.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortexalex.tutorials.spring.webflux.httpclient.MyReactiveHttpClient;
import com.vortexalex.tutorials.spring.webflux.publisher.MyItem;
import com.vortexalex.tutorials.spring.webflux.publisher.MyPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {

    private static final ObjectMapper json = new ObjectMapper();

    @Autowired
    MyPublisher publisher;

    @Autowired
    MyReactiveHttpClient httpClient;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        log.info("connection established");
        try {
            Flux<MyItem> multicastingPublisher = publisher.events().share();
            Flux.from(multicastingPublisher).subscribe(item -> httpClient.send(item).subscribe());
            return webSocketSession.send(Flux.from(multicastingPublisher).map(this::toJson).map(webSocketSession::textMessage));
        } catch (Exception e) {
            log.error("error in websocket handler", e);
            return Mono.error(e);
        }
    }

    private String toJson(MyItem item) {
        try {
            return json.writeValueAsString(item);
        } catch (JsonProcessingException jpe) {
            log.error("event to JSON parsing error", jpe);
            return "";
        }
    }
}
