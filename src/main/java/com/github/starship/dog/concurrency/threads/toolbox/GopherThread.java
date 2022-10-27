package com.github.starship.dog.concurrency.threads.toolbox;

public class GopherThread extends Thread {
    // жирок на зиму
    private final byte[] fat = new byte[100000];

    // конструктор без аргументов
    public GopherThread() {}

    // конструктор с исполняемой задачей
    public GopherThread(Runnable task) {
        super(task);
    }
}
