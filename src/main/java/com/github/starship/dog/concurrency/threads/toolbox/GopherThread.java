package com.github.starship.dog.concurrency.threads.toolbox;

import java.util.concurrent.atomic.AtomicInteger;

public class GopherThread extends Thread {

    //
    private final static AtomicInteger counter = new AtomicInteger();

    // жирок на зиму
    private final byte[] fat = new byte[100000];

    // конструктор без аргументов
    public GopherThread() {}

    // конструктор с исполняемой задачей
    public GopherThread(Runnable task) {
        super(task);
        setName("gopher-thread-" + counter.incrementAndGet());
    }
}
