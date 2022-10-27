package com.github.starship.dog.concurrency.threads.api;

import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Package<T extends Goods> extends ArrayList<T> {

    private final String tag;
    private final Long quantity;

    public String tag(){
        return tag;
    }

    public Double weight() {
        return this.stream().mapToDouble(Goods::weight).sum();
    }

    public Long quantity() {
        return quantity;
    }


}
