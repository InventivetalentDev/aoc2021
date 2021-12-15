package org.inventivetalent.aoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day7 extends Day {

    public Day7() {
        super();
    }

    int costToAlignTo(int position, int targetPosition) {
        return Math.abs(targetPosition - position);
    }


    int addedCostToAlignTo(int position, int targetPosition) {
        int diff = Math.abs(targetPosition - position);
        // 1+2+3+4+...+n = n*(n+1)/2
        return (diff * (diff + 1)) / 2;
    }

    int costToAlignTo(List<Integer> positions, int targetPosition, BiFunction<Integer, Integer, Integer> costFunction) {
        int totalCost = 0;
        for (int pos : positions) {
            int c = costFunction.apply(pos, targetPosition);
//            System.out.println(pos + "->" + targetPosition + " = " + c);
            totalCost += c;
        }
        return totalCost;
    }

    int run(String input, BiFunction<Integer, Integer, Integer> costFunction) {
        var positions = Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        int minPosition = positions.stream().min(Comparator.naturalOrder()).orElseThrow();
        int maxPosition = positions.stream().max(Comparator.naturalOrder()).orElseThrow();

        int bestCost = Integer.MAX_VALUE;
        int bestPosition = 0;
        for (int position = minPosition; position <= maxPosition; position++) {
            int cost = costToAlignTo(positions, position, costFunction);
            if (cost < bestCost) {
                bestCost = cost;
                bestPosition = position;
            }
        }

        System.out.println("Best Position: " + bestPosition + " with cost: " + bestCost);

        return bestCost;
    }

    @Override
    public Object run1(String input) {
        return run(input, this::costToAlignTo);
    }

    @Override
    public Object run2(String input) {
        return run(input, this::addedCostToAlignTo);
    }

    public static void main(String[] args) {
        new Day7();
    }

}
