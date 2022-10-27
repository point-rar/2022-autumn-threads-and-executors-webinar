package com.github.starship.dog.concurrency.threads.api.projects.food;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.food.factory.BigGopherFoodFactor;
import com.github.starship.dog.concurrency.threads.api.projects.food.factory.FoodFactory;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
public class FoodProduction implements GoodsProduction<Food> {

    // Пул потоков для одновременного исполнения заказов
    private final static ExecutorService executorService
            = Executors.newCachedThreadPool();

    // Фабрика Продуктов №1
    private final static FoodFactory FOOD_FACTORY_NUMBER_ONE = new BigGopherFoodFactor();

    // Фабрика продуктов "Товарищество Бурундуков"
    private final static FoodFactory FOOD_FACTORY_OF_GOPHER_PARTNERSHIP = new BigGopherFoodFactor();


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
            // newTaskFor & FutureTask & BlockingQueue
            // тут можно показать completion service
            final Future<Package<Food>> requestCooking1 = requestCooking(FOOD_FACTORY_NUMBER_ONE);
            final Future<Package<Food>> requestCooking2 = requestCooking(FOOD_FACTORY_OF_GOPHER_PARTNERSHIP);

            while (!requestCooking1.isDone() || !requestCooking2.isDone()) {
                // компилятор пожалуйста не оптимизируй этот кусок кода, молю тебя
                // здесь можно реализовать сложный механизм с backoff, но мы надеeмся что наши повора быстро справятся
                // табличка с тактами процессора на слип
            }

            // тут же показать логику "если прошел таймаут - берем со склада"
            return requestCooking1.isDone() && requestCooking1.state() == Future.State.SUCCESS  ? requestCooking1.get() : requestCooking2.get();
        } catch (InterruptedException | ExecutionException exception) {
            log.error("В этот раз без еды :(");
            return new Package<>("#nofood", 0L);
        }
    }

    private Future<Package<Food>> requestCooking(FoodFactory foodFactory) {
        Future<Package<Food>> task = executorService.submit(() -> foodFactory.cookAndPackage(quantity));

        return task;
    }
}
