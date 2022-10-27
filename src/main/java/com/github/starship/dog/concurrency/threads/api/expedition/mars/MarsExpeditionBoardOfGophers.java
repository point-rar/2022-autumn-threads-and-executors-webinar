package com.github.starship.dog.concurrency.threads.api.expedition.mars;

import com.github.starship.dog.concurrency.threads.api.ExpeditionRequest;
import com.github.starship.dog.concurrency.threads.api.ProjectNotFound;
import com.github.starship.dog.concurrency.threads.api.expedition.BoardOfDirectors;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProjectFactory;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.github.starship.dog.concurrency.threads.api.projects.GoodsProjectFactory.*;

public class MarsExpeditionBoardOfGophers implements BoardOfDirectors {

    private final Map<ProjectKey, GoodsProjectFactory<? extends Goods>> projects;

    @SafeVarargs
    public MarsExpeditionBoardOfGophers(GoodsProjectFactory<? extends Goods>... factories) {
        final Map<ProjectKey,GoodsProjectFactory<? extends Goods>> map = new HashMap<>();

        for (GoodsProjectFactory<? extends Goods> factory : factories) {
            map.put(factory.getKey(), factory);
        }

        this.projects = ImmutableMap.copyOf(map);
    }

    @Override
    public <T extends Goods> GoodsProduction<T> acceptNeeds(ExpeditionRequest needs) {
        final ProjectKey key = new ProjectKey(needs.getProjectName());

        if(!projects.containsKey(key))
            throw new ProjectNotFound("Проект с ключом <" + key + "> не найден!");

        //noinspection unchecked
        return (GoodsProduction<T>) projects.get(key).createProject(needs.getQuantity());
    }
}
