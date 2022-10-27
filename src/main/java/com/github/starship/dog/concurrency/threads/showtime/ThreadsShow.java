package com.github.starship.dog.concurrency.threads.showtime;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ThreadsShow {

    public static void main(String[] args) {
        Runnable command = () -> {
            log.info("Единороги атакуют!");
        };

        CountingCommand countingCommand = new CountingCommand();

        Thread thread = new Thread(countingCommand, "unicorns-attack");
        Thread thread1 = new Thread(countingCommand, "unicorns-attack1");

        thread1.start();
        thread.start();
    }

    public static class CountingCommand implements Runnable {

        int count = 0;

        @Override
        public void run() {

            for (int i = 0; i < 1000; i++) {
                count = ThreadLocalRandom.current().nextInt();
            }

            log.info("Единороги атакуют через {}!", count);
        }

    }
}
