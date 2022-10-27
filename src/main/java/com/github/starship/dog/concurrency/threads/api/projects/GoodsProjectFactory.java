package com.github.starship.dog.concurrency.threads.api.projects;

public interface GoodsProjectFactory<T extends Goods> {

    /**
     * Фабрика создает отдельный инстанс план проекта, в зависимости от количество необходимых товаров, запрошеных
     * бурундуками с Марса
     *
     * @return план проект
     * @param quantity количество полезных товаров
     */
    GoodsProduction<T> createProject(Long quantity);

    /**
     * Уникальное название проекта, в рамках которого будут производиться товары
     *
     * @return ключ план проекта
     */
    ProjectKey getKey();

    record ProjectKey(String projectName) {}
}
