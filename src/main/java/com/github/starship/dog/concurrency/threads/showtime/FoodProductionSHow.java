package com.github.starship.dog.concurrency.threads.showtime;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.food.Food;
import com.github.starship.dog.concurrency.threads.api.projects.food.FoodProduction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FoodProductionSHow {

    public static void main(String[] args) {

        final FoodProduction foodProduction = new FoodProduction(10);

        Package<Food> production = foodProduction.production();

        log.info("Коробочка для бурундуков: tag = {}, quantity = {}, weight= {}", production.tag(), production.quantity(), production.weight());
    }
}
