package com.github.starship.dog.concurrency.threads.api.projects;

import com.github.starship.dog.concurrency.threads.api.Package;

public interface GoodsProduction<T extends Goods> {

    /**
     *
     *
     */
    void collectingInvestments();

    /**
     *
     *
     */
    void scientificDevelopment();

    /**
     *
     *
     * @return
     */
    Package<T> production();
}
