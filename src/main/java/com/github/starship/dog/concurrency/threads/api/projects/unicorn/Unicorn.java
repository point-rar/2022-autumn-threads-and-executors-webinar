package com.github.starship.dog.concurrency.threads.api.projects.unicorn;

import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Unicorn implements Goods {

    private final String name;
    private final double weight;
    private final byte[] fat;

    @Override
    public String tag() {
        return "#unicorn-" + name.toLowerCase();
    }

    @Override
    public double weight() {
        return weight;
    }

}
