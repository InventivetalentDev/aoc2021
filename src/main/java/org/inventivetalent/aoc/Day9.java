package org.inventivetalent.aoc;

public class Day9 extends Day {

    public Day9() {
        super();
    }

    void print(int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    boolean isLowPoint(int[][] grid, int x, int y) {
        int v = grid[y][x];
        if (x > 0) {
            if (grid[y][x - 1] <= v) return false;
        }
        if (x < grid[0].length - 1) {
            if (grid[y][x + 1] <= v) return false;
        }
        if (y > 0) {
            if (grid[y - 1][x] <= v) return false;
        }
        if (y < grid.length - 1) {
            if (grid[y + 1][x] <= v) return false;
        }
        return true;
    }

    @Override
    public Object run1(String input) {
        var lines = asLines(input);
        int[][] grid = new int[lines.size()][lines.get(0).length()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                grid[y][x] = Integer.parseInt("" + lines.get(y).charAt(x));
                if (isLowPoint(grid, x, y)) {
                    System.out.println("" + x + " " + y);
                }
            }
        }
        print(grid);

        int sum = 0;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (isLowPoint(grid, x, y)) {
                    System.out.println(grid[y][x] + "  " + x + " " + y);
                    sum += grid[y][x] + 1;
                }
            }
        }
        System.out.println(sum);
        return null;
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day9();
    }

    class Cell {
        int self;
        int up;
        int down;
        int left;
        int right;
    }

}
