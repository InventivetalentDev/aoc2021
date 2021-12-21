package org.inventivetalent.aoc;

import java.util.Map;
import java.util.stream.Collectors;

public class Day8 extends Day {

    static final Map<Integer, Integer> LENGTHS_TO_NUMBER = Map.of(
            2, 1,
            3, 7,
            4, 4,
            7, 8
    );
    static final Map<String, Integer> PATTERNS_TO_NUMBER = Map.of(
            "abcefg", 0,
            "cf", 1,
            "acdeg", 2,
            "acdfg", 3,
            "bcdf", 4,
            "abdfg", 5,
            "abdefg", 6,
            "acf", 7,
            "abcdefg", 8,
            "abcdfg", 9
    );


    public Day8() {
        super();
    }

    @Override
    public Object run1(String input) {
        //TODO
        return null;
    }

    @Override
    public Object run2(String input) {
        //TODO
        return null;
    }

    public static void main(String[] args) {
        new Day8();
    }

    class Line {
        
    }

}
