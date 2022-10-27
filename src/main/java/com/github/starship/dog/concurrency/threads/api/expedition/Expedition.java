package com.github.starship.dog.concurrency.threads.api.expedition;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;

public interface Expedition {

    /**
     *
     *
     * @param goods
     */
    void equip(Package<? extends Goods> goods);
}
