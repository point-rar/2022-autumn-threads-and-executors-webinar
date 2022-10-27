
package com.github.starship.dog.concurrency.threads.api.projects.drug;

import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProjectFactory;

public class SuperProject implements GoodsProjectFactory<SuperDrug> {

    @Override
    public GoodsProduction<SuperDrug> createProject(Long quantity) {
        return new SuperDrugProduction(quantity);
    }

    @Override
    public ProjectKey getKey() {
        return new ProjectKey("super-pill");
    }
}
