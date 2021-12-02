package org.inventivetalent.aoc;

import java.util.List;

public class Day2 extends Day {

    public Day2() {
        super();
    }


    @Override
    public Object run1(String input) {
        List<String> lines = asLines(input);

        int x = 0;
        int y = 0;
        for (var line : lines) {
            var split = line.split(" ");
            var dir = split[0];
            var amount = Integer.parseInt(split[1]);

            switch (dir) {
                case "forward" -> x += amount;
                case "down" -> y += amount;
                case "up" -> y -= amount;
            }
        }

        System.out.println(x + " " + y);
        return x * y;
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day2();
    }

}
