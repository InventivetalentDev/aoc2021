package org.inventivetalent.aoc;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 extends Day {

    public Day3() {
        super();
    }


    @Override
    public Object run1(String input) {
        List<String> lines = asLines(input);

        int length = lines.get(0).length();
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (var i = 0; i < length; i++) {
            Map<Character, Integer> counts = new HashMap<>();
            for (var line : lines) {
                char c = line.charAt(i);
                counts.compute(c, (character, integer) -> integer == null ? 1 : integer + 1);
            }
            counts.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .ifPresentOrElse(e -> gamma.append(e.getKey()), () -> gamma.append("?"));
            counts.entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue))
                    .ifPresentOrElse(e -> epsilon.append(e.getKey()), () -> epsilon.append("?"));
        }
        System.out.println(gamma);
        System.out.println(epsilon);

        return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(), 2);
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day3();
    }

}
