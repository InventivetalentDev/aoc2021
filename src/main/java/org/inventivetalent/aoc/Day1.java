package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends Day {

    public Day1() {
        super();
    }

    int countIncrements(List<Integer> lines) {
        var increments = 0;
        var last = -1;
        for (var line : lines) {
            var increased = last != -1 && line > last;
            if (increased) increments++;
            last = line;
        }

        return increments;
    }

    @Override
    public Object run1(String input) {
        var lines = asIntLines(input);
        System.out.println(lines);

        return countIncrements(lines);
    }

    @Override
    public Object run2(String input) {
        var lines = asIntLines(input);
        System.out.println(lines);

        List<Integer> sums = new ArrayList<>();
        for (var i = 0; i < lines.size(); i++) {
            if (i + 2 >= lines.size()) break;
            var sum = lines.get(i) + lines.get(i + 1) + lines.get(i + 2);
            sums.add(sum);
        }
        System.out.println(sums);

        return countIncrements(sums);
    }

    public static void main(String[] args) {
        new Day1();
    }

}
