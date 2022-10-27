package com.github.starship.dog.concurrency.threads.api.projects.unicorn;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class UnicornProduction implements GoodsProduction<Unicorn> {

    private final static String[] NAMES = {
            "Билл", "Мэйбл", "Стэнфорд", "Вэнди", "Стэнли", "Диппер", "Сус", "Пухля"
    };

    private final long quantity;

    @Override
    public void collectingInvestments() {
        ThreadAPI.sleep(1000, TimeUnit.MILLISECONDS);
        log.info("Собираем деньги на единорогов....");
    }

    @Override
    public void scientificDevelopment() {
        ThreadAPI.sleep(2000, TimeUnit.MILLISECONDS);
        log.info("Выводим и выращиваем коняшек с рогами...");
    }

    @Override
    public Package<Unicorn> production() {
        ThreadAPI.sleep(3000, TimeUnit.MILLISECONDS);
        final Package<Unicorn> unicornPackage = new Package<>("#unicorns("+ UUID.randomUUID() +")", quantity);

        for (long i = 0; i < quantity; i++) {
            final String name = NAMES[ThreadLocalRandom.current().nextInt(NAMES.length)];
            final double weight = 1 + ThreadLocalRandom.current().nextDouble(100);

            unicornPackage.add(new Unicorn(name,weight,new byte[1000]));
        }

        return unicornPackage;
    }
}
