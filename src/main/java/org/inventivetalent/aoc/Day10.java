package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    Character processLine(String line, AtomicReference<String> finalLine) {
        int iterations = line.length();

        Character invalidToken = null;

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
                        if (invalidToken == null) {
                            invalidToken = token;
                        }
                    }
                }
            }
        }
        finalLine.set(tempLine.toString());
        return invalidToken;

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
            Character invalidToken = processLine(line, new AtomicReference<>());
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
        var lines = asLines(input);
        List<String> uncorruptedLines = new ArrayList<>();

        List<Long> scores = new ArrayList<>();
        for (var line : lines) {
            long lineScore = 0;

            AtomicReference<String> finalLine = new AtomicReference<>();
            Character invalidToken = processLine(line, finalLine);
            if (invalidToken == null) {
                uncorruptedLines.add(line);

                StringBuilder completion = new StringBuilder();
                for (int i = finalLine.get().length(); i > 0; i--) {
                    char c = finalLine.get().charAt(i - 1);
                    char match = matchingToken(c);
                    completion.append(match);

                    lineScore *= 5;
                    switch (match) {
                        case ')':
                            lineScore += 1;
                            break;
                        case ']':
                            lineScore += 2;
                            break;
                        case '}':
                            lineScore += 3;
                            break;
                        case '>':
                            lineScore += 4;
                            break;
                    }
                }
                System.out.println();
                System.out.println("line:" + line);
                System.out.println("finalLine:" + finalLine.get());
                System.out.println("completion:" + completion);
                System.out.println("line score:" + lineScore);
                scores.add(lineScore);
            }


        }


        scores.sort(Comparator.naturalOrder());
        System.out.println(scores);
        return scores.get(scores.size() / 2);
    }

    public static void main(String[] args) {
        new Day10();
    }


}
