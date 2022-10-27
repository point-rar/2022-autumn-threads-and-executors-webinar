package com.github.starship.dog.concurrency.threads.towns.little;

import com.github.starship.dog.concurrency.threads.toolbox.GopherThread;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class GopherThreadPool implements Executor {

    private final BlockingQueue<Runnable> queue;

    public GopherThreadPool(int capacity) {
        this.queue = new LinkedBlockingQueue<>();
        initThreads(new QueueThread[capacity]);
    }

    private void initThreads(QueueThread[] queueThreads) {
        for (int i = 0; i < queueThreads.length; i++) {
            queueThreads[i] = new QueueThread();
            queueThreads[i].start();
        }
    }

    public void execute(Runnable command) {
        queue.add(command);
    }

    private class QueueThread extends GopherThread {

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    final Runnable command = queue.take();
                    command.run();
                }
            } catch (InterruptedException exception) {
                // не понятно что это такое
                Thread.currentThread().interrupt();
            }
        }
    }
}
