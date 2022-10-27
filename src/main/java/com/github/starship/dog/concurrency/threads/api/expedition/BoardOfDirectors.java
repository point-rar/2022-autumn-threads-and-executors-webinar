package com.github.starship.dog.concurrency.threads.api.expedition;

import com.github.starship.dog.concurrency.threads.api.ExpeditionRequest;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;

public interface BoardOfDirectors {

    /**
     *
     *
     * @param needs
     * @param <T>
     * @return
     */
    <T extends Goods> GoodsProduction<T> acceptNeeds(ExpeditionRequest needs);
}
