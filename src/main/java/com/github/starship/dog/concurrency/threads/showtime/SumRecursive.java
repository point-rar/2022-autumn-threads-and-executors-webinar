package com.github.starship.dog.concurrency.threads.showtime;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

@Slf4j
public class SumRecursive {

    public static void main(String[] args) {

        final long[] sumMe = new long[100000000];

        for (int i = 0; i < 100000000; i++) {
            sumMe[i] = i + 1;
        }

        try (ForkJoinPool pool = new ForkJoinPool(8)) {

            SumRecursiveTask task = new SumRecursiveTask(sumMe, 0, 100000000);
            Long invokedInPool = task.invoke();

            Long sum = 100000000L * (100000000L + 1) / 2;

            log.info("pool = {}, formula = {}", invokedInPool, sum);
        }


    }


    public static class SumRecursiveTask extends RecursiveTask<Long> {

        private final static int THRESHOLD = 1000;

        private final long[] numbers;

        private final int leftBound;
        private final int rightBound;

        public SumRecursiveTask(long[] numbers, int leftBound, int rightBound) {
            this.numbers = numbers;
            this.leftBound = leftBound;
            this.rightBound = rightBound;
        }

        @Override
        protected Long compute() {

            if(rightBound - leftBound <= THRESHOLD)
                return solve();

            int mid = (leftBound + rightBound) >>> 1;

            final SumRecursiveTask task1 = new SumRecursiveTask(numbers, leftBound, mid);
            final SumRecursiveTask task2 = new SumRecursiveTask(numbers, mid, rightBound);

            task1.fork();
            task2.fork();

            long result = 0;

            result += task2.join();
            result += task1.join();

            log.info("left bound = {}, right bound = {}, result = {}", leftBound, rightBound, result);
            return result;
        }

        private Long solve() {
            long sum = 0;

            for (int i = leftBound; i < rightBound; i++) {
                sum = sum + numbers[i];
            }

            return sum;
        }

    }

}
