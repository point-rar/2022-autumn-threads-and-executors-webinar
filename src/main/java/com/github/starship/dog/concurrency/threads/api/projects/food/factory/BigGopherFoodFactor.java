package com.github.starship.dog.concurrency.threads.api.projects.food.factory;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.food.Food;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BigGopherFoodFactor implements FoodFactory {

    private final static String[] DISHES = {
            "гречиха", "кукуруза", "лен", "подсолнух", "горох", "огурцы", "сливы", "абрикосы"
    };

    private final static String FACTORY_TAG = "#factory("+ UUID.randomUUID() +")";

    @Override
    public Package<Food> cookAndPackage(long quantity) {
        final int localRequestKPI = ThreadLocalRandom.current().nextInt(3);

        if(localRequestKPI <= 1) {
            log.error("KPI фабрики {} слишком низкий для производства еды - все равно не успеем :(", FACTORY_TAG);
            throw new RuntimeException();
        }

        final Package<Food> foodPackage = new Package<>(FACTORY_TAG,quantity);

        for (long i = 0; i < quantity; i++) {
            final String product = DISHES[ThreadLocalRandom.current().nextInt(DISHES.length)];

            final Food dish = cooking(localRequestKPI, product);

            foodPackage.add(dish);
        }

        return foodPackage;
    }

    private Food cooking(int localRequestKPI, String product) {
        int weight = product.length();

        // cooking
        ThreadAPI.sleep(weight * localRequestKPI * 10L, TimeUnit.MILLISECONDS);

        return new Food(product, weight);
    }
}
