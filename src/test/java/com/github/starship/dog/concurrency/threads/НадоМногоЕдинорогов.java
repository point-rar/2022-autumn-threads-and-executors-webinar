package com.github.starship.dog.concurrency.threads;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class НадоМногоЕдинорогов extends Simulation {

    private final static String HOST = "http://0.0.0.0:8080";

    private final static int USERS = 10;
    private final static int REPEATS = 1000;
    private final static int PAUSE_SHIFT = 10;
    private final static int REQUEST_TIMEOUT = 180000;

    {
        final String requestBody = "{ \"projectName\" : \"unicorns\", \"justification\" : \"\", \"quantity\" : 100 }";

        final HttpRequestActionBuilder request = http("Request from Mars!").post(HOST)
                .header("Content-Type", "application/json")
                .header("Content-Length", String.valueOf(requestBody.length()))
                .requestTimeout(Duration.of(REQUEST_TIMEOUT, ChronoUnit.MILLIS))
                .body(StringBody(requestBody));

        final ScenarioBuilder scenario = scenario("Emulation of many towns requesting thousands of request")
                .repeat(REPEATS).on(exec(request).pause(Duration.of(PAUSE_SHIFT, ChronoUnit.MILLIS)));

        setUp(scenario.injectOpen(atOnceUsers(USERS)));
    }
}
