import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.testng.annotations.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class FluxTest {

    /**
     * Reactor does not enforce a concurrency model, it leaves that to the developer.
     * By default the execution happens in the thread of the caller to subscribe().
     */
    @Test
    public void runOnMainThread() {
        Flux.range(0, 10)
                .subscribe(i -> log.info(i+""));
    }

    /**
     * Variant of the above method.
     */
    @Test
    public void runOnMainThreadV2() {
        Flux.range(0, 10)
                .doOnNext(i -> log.info(i+""))
                .subscribe();
    }

    /**
     * publishOn influences subsequent operators
     * map is executed in thread main while filter in one parallel thread
     * @throws Exception
     */
    @Test
    public void publishOn() throws Exception {
        Flux.range(0, 10)
                .map(this::map)
                .publishOn(Schedulers.parallel())
                .filter(this::filter)
                .subscribe(i -> log.info("subscriber on " + i));


    }


    /**
     * interval operator by default executes on Schedulers.parallel()
     * @throws Exception
     */
    @Test
    public void simpleInterval() throws Exception {
        Flux.interval(Duration.ofMillis(1000)).subscribe(i -> log.info("consumer output " + i));
        Thread.sleep(10000); // force main thread to sleep for some time letting other threads produce some output
    }


    /**
     * Two subscribers that run with separate timings.
     * @throws Exception
     */
    @Test
    public void multipleSubscribers() throws Exception {
        Flux<Long> source = Flux.interval(Duration.ofMillis(1000));

        Flux<Long> connectable = source.share();

        source.subscribe(i -> log(i, 1000));
        source.map(i -> {
                    log.info("map+100");
                    return 100+i;
                }).subscribe(i -> log(i, 5000));


        Thread.sleep(50000);
    }

    @Test
    public void webClient() {
        WebClient client = WebClient.create("http://localhost:5050");
        ClientResponse response = client.method(HttpMethod.POST)
                .uri("/api/v1/topics/messages")
                .header("Content-Type", "application/json")
                .bodyValue(jsonBody(1.00))
                .retrieve().block();
        log.info("response " + response.statusCode());
        //response.subscribe(r -> log.info("status code " +r.statusCode()));

    }


    private String jsonBody(Double pressure) {
        JSONObject root = new JSONObject();
        root.put("user_id", "MNCPNT75A13L219D");
        root.put("sender", "Comune di Messina");

        JSONObject push = new JSONObject();
        push.put("to", "pier");
        push.put("title", "PRESSIONE SOTTO SOGLIA");
        push.put("content", "pressione attuale: " + pressure);

        root.put("push", push);

        return root.toString();



    }

    private void log(long i, long pauseMs)  {
        log.info("mi fa suannu " + i);
        try {
            Thread.sleep(pauseMs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void subscriber2(int i) {
        log.info("subscriber 2 " +i);
    }

    private Integer map(int i) {
       log.info("mapping " + i);
       return i * 2;
    }

    private boolean filter(int i) {
        log.info("filtering " + i);
        if (i % 2 == 0)
            return true;
        return false;
    }



}
