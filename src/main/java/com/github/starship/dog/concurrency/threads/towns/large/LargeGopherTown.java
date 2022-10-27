package com.github.starship.dog.concurrency.threads.towns.large;

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
import com.github.starship.dog.concurrency.threads.toolbox.GopherThread;
import com.github.starship.dog.concurrency.threads.toolbox.ThreadAPI;
import com.github.starship.dog.concurrency.threads.towns.AnyGopherTown;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.*;

/**
 * Средний город - тут бурундуки ценят свое время и знают уже какой-то толк в тайм-менеджменте!
 *
 * <p>
 * Задание: нам нужно ограничить время работы приемной комиссии определенным сроком исполнения!
 */
@Slf4j
public class LargeGopherTown extends AnyGopherTown implements AutoCloseable {

    // порт по которому город Гоферов готов принимать запросы
    private final static int TOWN_ACCEPT_PORT = 8080;

    // thread-safe
    // Снаряжаемая экспедиция
    private final Expedition expedition = new GopherMarsExpedition();

    // thread-safe
    // Совет директоров, который принимает решения и выдает проектные решения для новой экспедиции
    private final BoardOfDirectors boardOfDirectors = new MarsExpeditionBoardOfGophers(
            new SuperProject(), new UnicornProject());

    // пул потоков, в котором мы будем исполнять наши долгие запросы
    private final ExecutorService requestHandleExecutor
            = Executors.newFixedThreadPool(10, GopherThread::new);

    // пул который следит за временем работы сервиса
    private final ExecutorService workingHoursExecutor
            = Executors.newSingleThreadExecutor();

    // пул мониторинга длины очереди
    private final ScheduledExecutorService monitoringScheduler
            = Executors.newScheduledThreadPool(1);

    // время работы городка
    private final long workingTime;
    private final TimeUnit workingTimeUnit;

    public LargeGopherTown(long workingTime, TimeUnit workingTimeUnit) {
        this.workingTime = workingTime;
        this.workingTimeUnit = workingTimeUnit;

        initMonitoring();
    }

    private void initMonitoring() {
        final Runnable monitoringTask = () -> {
            final ThreadPoolExecutor executor = (ThreadPoolExecutor) requestHandleExecutor;
            long activeTaskCount = executor.getTaskCount() - executor.getCompletedTaskCount();

            if (activeTaskCount < 10) {
                log.info("Запросов не очень много, бурунлуки могут спать спокойно { active task count = {} }", activeTaskCount);
            } else if (activeTaskCount < 100) {
                log.info("Обычный рабочий день { active task count = {} }", activeTaskCount);
            } else {
                log.info("Авраааааал! Куча задач ничего не успеваем!! { active task count = {} }", activeTaskCount);
            }
        };

        monitoringScheduler.scheduleAtFixedRate(monitoringTask, 0, 1, TimeUnit.MINUTES);
    }

    public synchronized void start() throws Exception {
        workingHours();

        try (
                final ServerSocketChannel channel = ServerSocketChannel.open();
                final ServerSocket socket = channel.bind(new InetSocketAddress(TOWN_ACCEPT_PORT)).socket();
                final ExecutorService service = Executors.newSingleThreadExecutor()
        ) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // открываем канал связи
                    final Socket connection = socket.accept();

                    service.submit(() -> {
                        final ExpeditionRequest expeditionRequest = handleConnection(connection);

                        // создаем задачу для исполнения запроса и выполняем ее
                        EquipExpedition expedition = new EquipExpedition(expeditionRequest);
                        requestHandleExecutor.execute(expedition);
                    });
                } catch (Throwable equipExpeditionException) {
                    log.error("Не удалось создать и исполнить проект!", equipExpeditionException);
                }
            }
        }
    }

    private void workingHours() {
        // время начала работы
        final long start = System.nanoTime();

        // рабочее время
        final long workingHours = workingTimeUnit.toNanos(workingTime);

        // родительский поток
        final Thread acceptanceThread = Thread.currentThread();

        workingHoursExecutor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                final long current = System.nanoTime();

                if (current >= start + workingHours) {
                    acceptanceThread.interrupt();

                    log.info("Приемная комиссия завершает свою работу!");
                    break;
                }

                log.info("Приемная комиссия продолжает свою работу, до закрытия: " + (start + workingHours - current) / Math.pow(10, 9));

                final int delay = ThreadLocalRandom.current().nextInt(500, 1000);
                ThreadAPI.sleep(delay, TimeUnit.MILLISECONDS);
            }
        });
    }

    @Override
    public void close() throws Exception {
        requestHandleExecutor.shutdown();
        workingHoursExecutor.shutdown();
        monitoringScheduler.shutdown();
    }

    @RequiredArgsConstructor
    public class EquipExpedition implements Runnable {
        // сокет для чтения данных
        private final ExpeditionRequest expeditionRequest;

        @Override
        public void run() {
            try {
                // 1. Отправимся в совет директоров экспедиции и согласуем разработку и поставку нужд экспедиции
                GoodsProduction<Goods> goodsProduction = boardOfDirectors.acceptNeeds(expeditionRequest);

                // 2. Производство
                Package<Goods> packageOfGoods = production(goodsProduction);

                // 3. Снарядим экспедицию
                expedition.equip(packageOfGoods);
            } catch (Throwable expeditionRequestException) {
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
