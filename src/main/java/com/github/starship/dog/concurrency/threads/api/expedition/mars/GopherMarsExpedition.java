package com.github.starship.dog.concurrency.threads.api.expedition.mars;

import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.expedition.Expedition;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GopherMarsExpedition implements Expedition {

    @Override
    public void equip(Package<? extends Goods> goods) {
        log.info("Экспедиция снаряжена дополнительным грузом { название = {}, кол-во = {}, вес = {} }", goods.tag(), goods.quantity(), goods.weight());
    }
}
