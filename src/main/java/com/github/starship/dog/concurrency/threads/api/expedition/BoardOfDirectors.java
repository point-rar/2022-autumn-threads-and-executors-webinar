package com.github.starship.dog.concurrency.threads.api.expedition;

import com.github.starship.dog.concurrency.threads.api.ExpeditionRequest;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;

public interface BoardOfDirectors {

    /**
     * Совет директоров гордков Гоферов согласует и выдает планы проектов для дальнейшей реализации и отправки их на Марс
     * Без плана проекта ничего не попадет на ракету!
     *
     * @param needs запрос с Марса
     * @param <T> тип полезных товаров, на которые выдается план проект
     * @return план проект
     */
    <T extends Goods> GoodsProduction<T> acceptNeeds(ExpeditionRequest needs);
}
