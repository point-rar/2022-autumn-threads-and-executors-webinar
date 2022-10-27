package com.github.starship.dog.concurrency.threads.api.projects.drug;

import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.Data;

import java.util.UUID;

@Data
public class SuperDrug implements Goods {

    private final static double WEIGHT_OF_PIECE = 0.15D;
    public final static long NUMBER_IN_BOTTLE = 5;

    private final String series = UUID.randomUUID().toString();

    @Override
    public String tag() {
        return "#super-drug-" + series;
    }

    @Override
    public double weight() {
        return WEIGHT_OF_PIECE * NUMBER_IN_BOTTLE;
    }

}
