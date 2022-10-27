package com.github.starship.dog.concurrency.threads.showtime;

import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class InterrruptedShow {


    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    log.info("Состояние потока: {}", Thread.currentThread().isInterrupted());
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }

                log.info("Текущее время: {}", new Date());
            }
        };

        final Thread thread = new Thread(runnable);
        thread.start();

        Runnable stop = () -> {
            ThreadAPI.sleep(5, TimeUnit.SECONDS);
            log.info("Сим заклинаю тебя остановиться: {}", thread.threadId());
            thread.interrupt();
        };

        Thread timeout = new Thread(stop);
        timeout.start();

        thread.join();
    }
}
