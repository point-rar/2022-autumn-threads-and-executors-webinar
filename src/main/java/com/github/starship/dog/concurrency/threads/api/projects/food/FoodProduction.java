package com.github.starship.dog.concurrency.threads.api.projects.food;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.food.factory.BigGopherFoodFactor;
import com.github.starship.dog.concurrency.threads.api.projects.food.factory.FoodFactory;
import com.github.starship.dog.concurrency.threads.toolbox.GopherException;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
public class FoodProduction implements GoodsProduction<Food> {

    // Пул потоков для одновременного исполнения заказов
    private final static ExecutorService executorService
            = Executors.newFixedThreadPool(10);

    // Фабрика Продуктов №1
    private final static FoodFactory FOOD_FACTORY_NUMBER_ONE = new BigGopherFoodFactor(true);

    // Фабрика продуктов "Товарищество Бурундуков"
    private final static FoodFactory FOOD_FACTORY_OF_GOPHER_PARTNERSHIP = new BigGopherFoodFactor(false);


    private final long quantity;

    @Override
    public void collectingInvestments() {
        ThreadAPI.sleep(500, TimeUnit.MILLISECONDS);
        log.info("Собираем деньги на еду для братьев с Марса....");
    }

    @Override
    public void scientificDevelopment() {
        log.info("Ничего изучать не требуется, уж огурцы то мы вырастить способны!");
    }

    public Package<Food> production() {
        try {
            CompletionService<Package<Food>> service = new ExecutorCompletionService<>(executorService);

            service.submit(new FoodProductionCallable("numberOne", FOOD_FACTORY_NUMBER_ONE, quantity));
            service.submit(new FoodProductionCallable("gopherAndCo", FOOD_FACTORY_OF_GOPHER_PARTNERSHIP, quantity));

            return pollSuccess(2, service);
        } catch (Exception exception) {
            // ну не повезло :(
            return new Package<>("#empty", quantity);
        }
    }

    private Package<Food> pollSuccess(int taskCount, CompletionService<Package<Food>> service) {
        for (int i = 0; i < taskCount; i++) {
            try {
                return service.take().get();
            } catch (Exception exception) {
                // log
            }
        }

        throw new GopherException("не удалось получить заказ ни из одной фабрики!");
    }

    @RequiredArgsConstructor
    private static class FoodProductionCallable implements Callable<Package<Food>> {

        private final String factoryName;

        private final FoodFactory factory;
        private final long quantity;

        @Override
        public Package<Food> call() throws Exception {
            log.info("Фабрика приняла заказ к исполнению: {}", factoryName);
            Package<Food> foods = factory.cookAndPackage(quantity);
            log.info("Фабрика исполнила заказ : {}", factoryName);
            return foods;
        }

    }



































}
