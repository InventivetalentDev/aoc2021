package org.inventivetalent.aoc;

public class Day1 extends Day {

    public Day1() {
        super();
    }

    @Override
    public Object run(String input) {
        var lines = asLines(input);
        System.out.println(lines);

        var increments = 0;
        var last = -1;
        for (var line : lines) {
            var current = Integer.parseInt(line);
            var increased = last != -1 && current > last;
            if (increased) increments++;
            last = current;
        }

        return increments;
    }

    public static void main(String[] args) {
        new Day1();
    }

}
