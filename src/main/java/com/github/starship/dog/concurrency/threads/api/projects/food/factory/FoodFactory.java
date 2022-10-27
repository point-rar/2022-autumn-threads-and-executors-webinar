package com.github.starship.dog.concurrency.threads.api.projects.food.factory;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.food.Food;

public interface FoodFactory {

    Package<Food> cookAndPackage(long quantity);
}
