package com.github.starship.dog.concurrency.threads.towns.tiny;

import com.github.starship.dog.concurrency.threads.api.ExpeditionRequest;
import com.github.starship.dog.concurrency.threads.api.Package;
import com.github.starship.dog.concurrency.threads.api.expedition.BoardOfDirectors;
import com.github.starship.dog.concurrency.threads.api.expedition.Expedition;
import com.github.starship.dog.concurrency.threads.api.expedition.mars.GopherMarsExpedition;
import com.github.starship.dog.concurrency.threads.api.expedition.mars.MarsExpeditionBoardOfGophers;
import com.github.starship.dog.concurrency.threads.api.projects.Goods;
import com.github.starship.dog.concurrency.threads.api.projects.GoodsProduction;
import com.github.starship.dog.concurrency.threads.api.projects.drug.SuperProject;
import com.github.starship.dog.concurrency.threads.api.projects.unicorn.UnicornProject;
import com.github.starship.dog.concurrency.threads.towns.AnyGopherTown;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Крошечный город - в нем все течет не спешно! Все процессы тут еще не устаканены, а каждый бурундук не верит в этих,
 * так называемых, покорителей Марса...
 *
 * <p>
 * Задание: нам потребуется последовательно исполнять приходящие запросы с Марса
 */
@Slf4j
public class TinyGopherTown extends AnyGopherTown {

    // порт по которому город Гоферов готов принимать запросы
    private final static int TOWN_ACCEPT_PORT = 8080;

    // thread-safe
    // Снаряжаемая экспедиция
    private final Expedition expedition = new GopherMarsExpedition();

    // thread-safe
    // Совет директоров, который принимает решения и выдает проектные решения для новой экспедиции
    private final BoardOfDirectors boardOfDirectors = new MarsExpeditionBoardOfGophers(
            new SuperProject(), new UnicornProject());

    public synchronized void start() throws Exception {
        // thread-safe
        final ServerSocket socket = new ServerSocket(TOWN_ACCEPT_PORT);

        while (true) {
            // открываем канал связи
            final Socket connection = socket.accept();

            // создаем задачу для исполнения запроса и выполняем ее
            final EquipExpedition equipExpedition = new EquipExpedition(connection);
            //  Исполни задание
            equipExpedition.run();
        }
    }

    @RequiredArgsConstructor
    public class EquipExpedition implements Runnable {
        // сокет для чтения данных
        private final Socket connection;

        @Override
        public void run() {
            try {
                // 1. Обработаем запрос от экспедиции
                final ExpeditionRequest expeditionRequest = handleConnection(connection);

                // 2. Отправимся в совет директоров экспедиции и согласуем разработку и поставку нужд экспедиции
                GoodsProduction<Goods> goodsProduction = boardOfDirectors.acceptNeeds(expeditionRequest);

                // 3. Производство
                Package<Goods> packageOfGoods = production(goodsProduction);

                // 4. Снарядим экспедицию
                expedition.equip(packageOfGoods);
            } catch (Exception expeditionRequestException) {
                log.error("Не удалось создать и исполнить проект!", expeditionRequestException);
            }

        }

        private Package<Goods> production(GoodsProduction<Goods> goodsProduction) {
            // 1. Произведем сбор средств на разработку и производство
            goodsProduction.collectingInvestments();

            // 2. Произведем научную разработку
            goodsProduction.scientificDevelopment();

            // 3. Изготовим товары
            return goodsProduction.production();
        }
    }
}
