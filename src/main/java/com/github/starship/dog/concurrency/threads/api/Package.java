package com.github.starship.dog.concurrency.threads.api;

import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

/**
 * Коробка [упаковка] с полезными товарами, которые отправятся вместе с космолетом на Марс!
 *
 * @param <T> тип полезных товаров
 */
@RequiredArgsConstructor
public class Package<T extends Goods> extends ArrayList<T> {

    // тег продукта, перевозимого в данной упаковке (этикетка на упаковке)
    private final String tag;
    // количество товара в упаковке
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
