package com.github.starship.dog.concurrency.threads.api.projects.food;

import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Food implements Goods {

    private final String type;
    private final double weight;

    @Override
    public String tag() {
        return "#food-" + type.toLowerCase();
    }

    @Override
    public double weight() {
        return weight;
    }
}
