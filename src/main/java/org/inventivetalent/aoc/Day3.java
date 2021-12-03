package org.inventivetalent.aoc;

import java.util.*;

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
        List<String> lines = asLines(input);

        int length = lines.get(0).length();
        List<String> o2Lines = new ArrayList<>(lines);
        List<String> co2Lines = new ArrayList<>(lines);
        for (var i = 0; i < length; i++) {
            System.out.println(i);
            int finalI = i;
            {
                Map<Character, Integer> o2Counts = new HashMap<>();
                for (var line : o2Lines) {
                    char c = line.charAt(i);
                    o2Counts.compute(c, (character, integer) -> integer == null ? 1 : integer + 1);
                }
                if (!o2Lines.isEmpty()) {
                    var key = findMaxOrMin(o2Counts.entrySet(), true, '1');
                    System.out.println(key);
                    o2Lines.removeIf(s -> s.charAt(finalI) != key);
                }
            }

            {
                Map<Character, Integer> co2Counts = new HashMap<>();
                for (var line : co2Lines) {
                    char c = line.charAt(i);
                    co2Counts.compute(c, (character, integer) -> integer == null ? 1 : integer + 1);
                }
//                co2Counts.entrySet().stream()
//                        .min(Comparator.comparingInt(Map.Entry::getValue))
//                        .ifPresent(e -> {
//                            if (co2Lines.size() <= 1) return;
//                            var key = e.getKey();
//                            co2Lines.removeIf(s -> s.charAt(finalI) != key);
//                        });
                if (!co2Lines.isEmpty()) {
                    var key = findMaxOrMin(co2Counts.entrySet(), false, '0');
                    System.out.println(key);
                    co2Lines.removeIf(s -> s.charAt(finalI) != key);
                }
            }

            System.out.println(o2Lines);
            System.out.println(co2Lines);
            System.out.println(" ");
        }

        String o2 = o2Lines.get(0);
        String co2 = co2Lines.get(0);

        System.out.println(o2);
        System.out.println(co2);

        return Integer.parseInt(o2, 2) * Integer.parseInt(co2, 2);
    }

    char findMaxOrMin(Set<Map.Entry<Character, Integer>> set, boolean maxOrMin, char fallbackIfMultiple) {
        List<Map.Entry<Character, Integer>> sorted = new ArrayList<>(set);
        sorted.sort(Comparator.comparingInt(e -> ((Map.Entry<Character, Integer>) e).getValue()).reversed());
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (var entry : sorted) {
            if (!maxOrMin && entry.getValue() < min) {
                min = entry.getValue();
            }
            if (maxOrMin && entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        int finalMin = min;
        int finalMax = max;
        if (maxOrMin) {
            sorted.removeIf(e -> e.getValue() < finalMax);
        } else {
            sorted.removeIf(e -> e.getValue() > finalMin);
        }
        if (sorted.size() > 1) {
            return fallbackIfMultiple;
        }
        return sorted.get(0).getKey();
                /*set.stream()
                .sorted(Comparator.comparingInt(e -> ((Map.Entry<Character, Integer>) e).getValue()))
                .collect(Collectors.toList());*/
//        int min = Integer.MAX_VALUE;
//        for(var entry : set) {
//            if(entry.getValue()<)
//        }
    }

    public static void main(String[] args) {
        new Day3();
    }

}
