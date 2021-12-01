package org.inventivetalent.aoc;

import org.apache.commons.io.IOUtil;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Day {


    public Day() {
        Object out = run1(getInput());
        if (out != null) {
            System.out.println("PART 1");
            System.out.println("---------------------");
            System.out.println(out);
        }

        System.out.println(" ");

        out = run2(getInput());
        if (out != null) {
            System.out.println("PART 2");
            System.out.println("---------------------");
            System.out.println(out);
        }
    }

    public String getInput() {
        String name = "/" + getClass().getSimpleName().toLowerCase() + ".txt";
        try {
            return IOUtil.toString(getClass().getResourceAsStream(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> asLines(String input) {
        return List.of(input.split("\r\n"));
    }

    public static List<Integer> asIntLines(String input) {
        return asLines(input).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public abstract Object run1(String input);

    public abstract Object run2(String input);

}
