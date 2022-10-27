package com.github.starship.dog.concurrency.threads.showtime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadStartShow {

    public static void main(String[] args) {
        final Runnable command = () -> {
            log.info("Единороги атакуют!");
        };

        Thread thread = new Thread(command, "unicorns-attack");
        thread.start();
    }

}
