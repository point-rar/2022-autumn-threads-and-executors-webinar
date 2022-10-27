package com.github.starship.dog.concurrency.threads.api.projects.food;

import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProjectFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FoodProject implements GoodsProjectFactory<Food> {

    @Override
    public GoodsProduction<Food> createProject(Long quantity) {
        return new FoodProduction(quantity);
    }

    @Override
    public ProjectKey getKey() {
        return new ProjectKey("food");
    }
}
