package com.github.starship.dog.concurrency.threads.towns.little;

import com.github.starship.dog.concurrency.threads.toolbox.GopherThread;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class PokerFacePool implements Executor {

    private final BlockingQueue<Runnable> queue;

    public PokerFacePool(int capacity) {
        initThread(new GopherThread[capacity]);

        this.queue = new LinkedBlockingQueue<>();
    }

    private void initThread(Thread[] threads) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new QueueThread();
            threads[i].start();
        }
    }

    public void execute(@NotNull Runnable command) {
        queue.add(command);
    }

    private class QueueThread extends GopherThread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final Runnable task = queue.take();

                    task.run();
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                } catch (Exception exception) {
                    // SAD - так делать не надо, это для примера
                }
            }
        }
    }

}
