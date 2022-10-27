package com.github.starship.dog.concurrency.threads.api.expedition;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;

public interface Expedition {

    /**
     * Космический корабль был бы не нужен, если бы мы его не снаряжали всем необходимым для экспедиции!
     * <p>
     * Данный метод отвечает за снаряжение корабля полезными товарами, которые производятся по запросам бурундуков с Марса
     *
     * @param goods упаковка с полезными товарами
     */
    void equip(Package<? extends Goods> goods);
}
