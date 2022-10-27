package com.github.starship.dog.concurrency.threads.showtime;

import com.github.starship.dog.concurrency.threads.toolbox.GopherThread;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolExecutorShow {

    public static void main(String[] args) {
        Runnable command = () -> {
            ThreadAPI.sleep(2, TimeUnit.SECONDS);
            log.info("Единороги атакуют!");
        };

        ThreadPoolExecutor executor
                = new ThreadPoolExecutor(
                3,
                3,
                10,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                GopherThread::new
        );

        executor.execute(command);
        executor.execute(command);
        executor.execute(command);
        executor.execute(command);

    }


}
