package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 extends Day {

    public Day6() {
        super();
    }


    @Override
    public Object run1(String input) {
        var originalTimers = Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        var timers = new ArrayList<>(originalTimers);

        System.out.println("Initial: " + timers);

        for (int day = 0; day < 80; day++) {

            int size = timers.size();
            for (int i = 0; i < size; i++) {
                int timer = timers.get(i);
                if (timer == 0) {
                    timer = 6;
                    timers.add(8);
                } else {
                    timer--;
                }
                timers.set(i, timer);
            }

            System.out.println("After " + (day + 1) + " days: " + timers.size()/*+" " + timers*/);

        }

        return timers.size();
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day6();
    }

}
