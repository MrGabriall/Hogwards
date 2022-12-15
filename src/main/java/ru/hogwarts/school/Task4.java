package ru.hogwarts.school;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Task4 {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            System.out.println(sum() + "  " + parallel());
        }


    }

    public static long sum(){
        long time = System.currentTimeMillis();
        long sum = Stream.iterate(1L, a -> a +1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);

        return System.currentTimeMillis() - time;
    }

    public static long parallel(){
        long time = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(1L, 1_000_000L)
                .parallel()
                .reduce(0, Long::sum);
        return System.currentTimeMillis() - time;
    }
}
