package com.github.starship.dog.concurrency.threads.api.projects;

import com.github.starship.dog.concurrency.threads.api.Package;

public interface GoodsProduction<T extends Goods> {

    /**
     * План проект разделен на три части. Первая из них это сбор инвестиций!
     * Как мы знаем никакой удачный проект не может быть создан за бесплатно, везед рулят орехи (валюта гоферов)!
     */
    void collectingInvestments();

    /**
     * План проект разделен на три части. Вторая - это научные исследования.
     * Иногда приходится изобретать, что-то новое, ведь эти бурунудки с Марса так и наровят залезть в каждую нору!
     */
    void scientificDevelopment();

    /**
     * И наконец когда мы собрали орехи и разработали новые технологии мы можем произвести и упаковать товар для наших
     * друзей с Марса
     *
     * @return упаковка полезных товаров, реализованных в рамках текущего план проекта
     */
    Package<T> production();
}
