package com.github.starship.dog.concurrency.threads.api.projects;

public interface Goods {

    /**
     * У каждого товара есть свой тег (уникальное название товара)
     * @return тег товара
     */
    String tag();

    /**
     * Каждый товар имеет свой вес
     *
     * @return вес
     */
    double weight();
}
