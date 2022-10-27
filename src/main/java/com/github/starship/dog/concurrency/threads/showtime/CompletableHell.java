package com.github.starship.dog.concurrency.threads.showtime;

import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j
public class CompletableHell {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        final CompletableFuture<String> sync = new CompletableFuture<>();

        sync
                .thenRun(() -> {
                    log.info("А здесь единороги { thread = {} }", Thread.currentThread().threadId());
                })
                .whenComplete((result, throwable) -> {
                    ThreadAPI.sleep(2, TimeUnit.SECONDS);
                    log.info("Единороги идут домой { thread = {} }", Thread.currentThread().threadId());

                    synchronized (lock) {
                        lock.notify();
                    }
                });

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            ThreadAPI.sleep(5, TimeUnit.SECONDS);
            log.info("Единороги цок-цок-цок { thread = {} }", Thread.currentThread().threadId());
            sync.complete("unicorns-attack!!");
        });

        synchronized (lock) {
            lock.wait();
        }

    }

    public static void main1(String[] args) {

        final Supplier<String> task = () -> {
            ThreadAPI.sleep(2, TimeUnit.SECONDS);
            log.info("Я исполняюсь в другом потоке!");

            return String.valueOf(Thread.currentThread().threadId());
        };

        CompletableFuture<String> future = CompletableFuture.supplyAsync(task);
        CompletableFuture<String> future2 = future.thenApply((threadId) -> "Thread ID: " + threadId);

        log.info("future result = {}", future2.join());
    }
}
