package com.github.starship.dog.concurrency.threads.api;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class ExpeditionRequest {

    /**
     * Название 'разработки' которую необходимо разработать и произвести для целей дальнейшей успешной колонизации
     * Марса почтенными Гоферами
     */
    private String projectName;

    /**
     * Описание от гоферов-работяг почему им нужен данный проект
     */
    private @Nullable String justification;

    /**
     * Кол-во товаров которое должно быть произведено для целей экспедиции
     */
    private Long quantity;
}
