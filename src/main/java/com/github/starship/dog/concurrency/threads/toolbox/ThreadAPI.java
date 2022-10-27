package com.github.starship.dog.concurrency.threads.toolbox;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadAPI {

    /**
     * Метод позволяет вызвать sleep у потока не выбрасывая исключения, но сохраняя поток прерванным
     *
     * @param amount количество времени, на которое требуется заснуть
     * @param unit единицы времени
     */
    public static void sleep(long amount, TimeUnit unit) {
        try {
            unit.sleep(amount);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

}
