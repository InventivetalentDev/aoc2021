package org.inventivetalent.aoc;

import org.apache.commons.io.IOUtil;

import java.util.List;

public abstract class Day {


    public Day() {
        Object out = run(getInput());
        System.out.println("---------------------");
        System.out.println(out);
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

    public abstract Object run(String input);

}
