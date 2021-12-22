package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends Day {

    public Day10() {
        super();
    }

    char matchingToken(char token) {
        switch (token) {
            case '(':
                return ')';
            case '{':
                return '}';
            case '[':
                return ']';
            case '<':
                return '>';
            case ')':
                return '(';
            case '}':
                return '{';
            case ']':
                return '[';
            case '>':
                return '<';
        }
        return 0;
    }

    Character processLine(String line) {
        int iterations = line.length();

        StringBuilder tempLine = new StringBuilder(line);
        for (int i1 = 0; i1 < iterations + 1; i1++) {
            boolean lastIteration = i1 == iterations;
            for (int i = 0; i < tempLine.length(); i++) {
                char token = tempLine.charAt(i);
                if (token == '(' || token == '{' || token == '[' || token == '<') {

                }
                if (token == ')' || token == '}' || token == ']' || token == '>') {
                    char matchingToken = matchingToken(token);
                    char leftToken = tempLine.charAt(i - 1);
                    if (leftToken == matchingToken) {
                        tempLine.deleteCharAt(i);
                        tempLine.deleteCharAt(i - 1);
                        System.out.println(tempLine);
                    } else if (lastIteration) {
                        System.out.println("Mismatch: " + token + " " + leftToken);
                        System.out.println("Expected " + matchingToken(leftToken) + ", but found " + token);
                        return token; // stop at first mismatch
                    }
                }
            }
        }
        return null;

        /*
        List<Character> open = new ArrayList<>();
        List<Character> close = new ArrayList<>();



        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '('||c == '{'||c == '[') {
                open.add(c);
            }
            if (c == ')'||c == '}'||c == ']') {
                close.add(c);

                if (!open.isEmpty()) {
                    char lastOpenToken = open.get(open.size()-1);
                    char matchToken = matchingToken(lastOpenToken);
                    if(matchToken == c) {
                        open.remove(open.size()-1);
                        close.remove(close.size()-1);

                    }
                }
            }

        }

         */
    }

    @Override
    public Object run1(String input) {
        var lines = asLines(input);
        List<Character> invalidTokens = new ArrayList<>();
        for (var line : lines) {
            Character invalidToken = processLine(line);
            if (invalidToken != null) {
                invalidTokens.add(invalidToken);
            }
            System.out.println();
        }

        int score = 0;
        for (var invalidToken : invalidTokens) {
            switch (invalidToken) {
                case ')':
                    score += 3;
                    break;
                case ']':
                    score += 57;
                    break;
                case '}':
                    score += 1197;
                    break;
                case '>':
                    score += 25137;
                    break;
            }
        }

        return score;
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day10();
    }


}
