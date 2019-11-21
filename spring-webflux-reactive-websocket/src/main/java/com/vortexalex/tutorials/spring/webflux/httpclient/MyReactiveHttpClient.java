package com.vortexalex.tutorials.spring.webflux.httpclient;

import com.vortexalex.tutorials.spring.webflux.publisher.MyItem;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyReactiveHttpClient {

    @Value("${notifier.baseUrl}")
    private String notifierUrl;

    @Value("${notifier.endpoint}")
    private String notifierEndpoint;

    public Mono send(MyItem item) {

        return Mono.just(item)
                .filter(this::myCondition)
                .flatMap(i -> request(i))
                .doOnNext(resp -> log.info("status code for {}: {}", item.getValue(), resp.statusCode()));
        //.then()
        //.subscribeOn(Schedulers.boundedElastic());

    }

    private boolean myCondition(MyItem item) {
        return item.getValue() < 2;
    }

    private Mono<ClientResponse> request(MyItem item) {
        WebClient client = WebClient.create(notifierUrl);
        return client.method(HttpMethod.POST)
                .uri(notifierEndpoint)
                .header("Content-Type", "application/json")
                .bodyValue(jsonFrom(item))
                .exchange();
    }

    private String jsonFrom(MyItem item) {
        JSONObject root = new JSONObject();
        root.put("value", item.getValue());
        return root.toString();
    }

}
