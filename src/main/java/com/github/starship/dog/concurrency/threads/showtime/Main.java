package com.github.starship.dog.concurrency.threads.showtime;


import com.github.starship.dog.concurrency.threads.towns.GopherTown;
import com.github.starship.dog.concurrency.threads.towns.large.LargeGopherTown;
import com.github.starship.dog.concurrency.threads.towns.little.ExperimentalLittleGopherTown;
import com.github.starship.dog.concurrency.threads.towns.little.LittleGopherTown;
import com.github.starship.dog.concurrency.threads.towns.small.SmallGopherTown;
import com.github.starship.dog.concurrency.threads.towns.tiny.TinyGopherTown;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        try (final GopherTown gopherTown = new LargeGopherTown(10, TimeUnit.MINUTES)) {
            gopherTown.start();

        }
    }

}
