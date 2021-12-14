package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 extends Day {

    public Day6() {
        super();
    }

    // naive solution, just takes like a week to solve lol
    int run(String input, int days) {
        var timers = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Initial: " + timers);

        for (int day = 0; day < days; day++) {

            int size = timers.length;
            for (int i = 0; i < size; i++) {
                int timer = timers[i];
                if (timer == 0) {
                    timer = 6;

                    int[] newTimers = new int[timers.length + 1];
                    System.arraycopy(timers, 0, newTimers, 0, timers.length);
                    newTimers[newTimers.length - 1] = 8;
                    timers = newTimers;
                } else {
                    timer--;
                }
                timers[i] = timer;
            }

            System.out.println("After " + (day + 1) + " days: " + timers.length/*+" " + timers*/);

        }

        return timers.length;
    }

    // https://www.reddit.com/r/adventofcode/comments/r9z49j/2021_day_6_solutions/
    long runBuckets(String input, int days) {
        int[] timers = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        long[] counts = new long[10];
        for (int timer : timers) {
            counts[timer]++;
        }
        System.out.println(Arrays.toString(counts));

        for (int day = 0; day < days; day++) {
            counts[7] += counts[0];
            counts[9] = counts[0];
            for (int i = 0; i < 9; i++) {
                counts[i] = counts[i + 1];
            }
            counts[9] = 0;

            //System.out.println(Arrays.toString(counts));
        }

        System.out.println(Arrays.toString(counts));

        return Arrays.stream(counts).sum();
    }

    @Override
    public Object run1(String input) {
        return runBuckets(input, 80);
    }

    @Override
    public Object run2(String input) {
        return runBuckets(input, 256);
    }

    public static void main(String[] args) {
        new Day6();
    }

}
