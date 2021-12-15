package org.inventivetalent.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 extends Day {

    public Day7() {
        super();
    }

    int costToAlignTo(int position, int targetPosition) {
        return Math.abs(targetPosition - position);
    }

    int costToAlignTo(List<Integer> positions, int targetPosition) {
        int totalCost = 0;
        for (int pos : positions) {
            totalCost += costToAlignTo(pos, targetPosition);
        }
        return totalCost;
    }


    @Override
    public Object run1(String input) {
        var positions = Arrays.stream(input.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        int bestCost = Integer.MAX_VALUE;
        int bestPosition = 0;
        for (int position : positions) {
            int cost = costToAlignTo(positions, position);
            if (cost < bestCost) {
                bestCost = cost;
                bestPosition = position;
            }
        }

        System.out.println("Best Position: " + bestPosition + " with cost: " + bestCost);

        return bestCost;
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day7();
    }

}
