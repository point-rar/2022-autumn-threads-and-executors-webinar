package com.github.starship.dog.concurrency.threads.api.projects.drug;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SuperDrugProduction implements GoodsProduction<SuperDrug> {

    private final long quantity;

    @Override
    public void collectingInvestments() {
        log.info("Собираем деньги на таблетки....");
    }

    @Override
    public void scientificDevelopment() {
        log.info("Выращиваем ингредиенты...");
    }

    @Override
    public Package<SuperDrug> production() {
        long bottles = quantity / SuperDrug.NUMBER_IN_BOTTLE;

        if(quantity % SuperDrug.NUMBER_IN_BOTTLE != 0)
            bottles = bottles + 1;

        final Package<SuperDrug> drugPackage = new Package<>("#super-drugs("+ UUID.randomUUID() +")", bottles * SuperDrug.NUMBER_IN_BOTTLE);

        for (int i = 0; i < bottles; i++) {
            drugPackage.add(new SuperDrug());
        }

        return drugPackage;
    }
}
