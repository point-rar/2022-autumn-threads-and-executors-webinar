package com.github.starship.dog.concurrency.threads.showtime;


import com.github.starship.dog.concurrency.threads.towns.GopherTown;
import com.github.starship.dog.concurrency.threads.towns.tiny.TinyGopherTown;

public class Main {

    public static void main(String[] args) throws Exception {
        final GopherTown gopherTown = new TinyGopherTown();

        gopherTown.start();
    }

}
