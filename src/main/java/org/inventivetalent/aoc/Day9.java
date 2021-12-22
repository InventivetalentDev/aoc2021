package org.inventivetalent.aoc;

import java.util.*;

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

    void print(int[][] grid, boolean[][] fillGrid, List<Pos> basinCells) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (basinCells.contains(new Pos(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(grid[y][x]);
                }
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
        return sum;
    }

    @Override
    public Object run2(String input) {

        var lines = asLines(input);
        int[][] grid = new int[lines.size()][lines.get(0).length()];
        boolean[][] fillGrid = new boolean[lines.size()][lines.get(0).length()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                int v = Integer.parseInt("" + lines.get(y).charAt(x));
                grid[y][x] = v;
                fillGrid[y][x] = v >= 9;


            }
        }
        print(grid);

        var visitedCells = new ArrayList<>();
        List<List<Pos>> basins = new ArrayList<>();
        List<Pos> basinCells = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (isLowPoint(grid, x, y)) {
                    System.out.println(grid[y][x] + "  " + x + " " + y);
                    floodFill(grid, fillGrid, x, y, basinCells);
                    basins.add(new ArrayList<>(basinCells));
                    basinCells.clear();
                }
            }
        }
        System.out.println(basinCells);

        System.out.println(Arrays.deepToString(fillGrid));
        print(grid, fillGrid, basinCells);

        System.out.println();
        System.out.println();

        for (var l : basins) {
            print(grid, fillGrid, l);
            System.out.println();
        }


        return basins.stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce((a, b) -> a * b);

    }

    void floodFill(int[][] grid, boolean[][] fillGrid, int x, int y, List<Pos> basinCells) {
        if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length || fillGrid[y][x]) return;
        if (grid[y][x] < 9) {
            basinCells.add(new Pos(x, y));
            fillGrid[y][x] = true;

            floodFill(grid, fillGrid, x + 1, y, basinCells);
            floodFill(grid, fillGrid, x - 1, y, basinCells);
            floodFill(grid, fillGrid, x, y + 1, basinCells);
            floodFill(grid, fillGrid, x, y - 1, basinCells);
        }
    }

    public static void main(String[] args) {
        new Day9();
    }

    class Pos {
        int x;
        int y;

        Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    class Cell {
        int self;
        int up;
        int down;
        int left;
        int right;
    }

}
