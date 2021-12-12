package org.inventivetalent.aoc;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Day5 extends Day {

    public Day5() {
        super();
    }

    List<Line> parseInput(String input) {
        return asLines(input).stream()
                .map(line -> line.split(" -> "))
                .map(split -> {
                    var splitL = Arrays.stream(split[0].split(",")).mapToInt(Integer::parseInt).toArray();
                    var splitR = Arrays.stream(split[1].split(",")).mapToInt(Integer::parseInt).toArray();

                    Point left = new Point();
                    left.x = splitL[0];
                    left.y = splitL[1];

                    Point right = new Point();
                    right.x = splitR[0];
                    right.y = splitR[1];

                    Line line = new Line();
                    line.start = left;
                    line.end = right;

                    return line;
                })
                .collect(Collectors.toList());
    }

    void print(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + "");
            }
            System.out.println();
        }
    }

    int[][] makeGrid(List<Line> lines) {
        int maxX = 0;
        int maxY = 0;
        for (Line line : lines) {
            if (line.start.x > maxX) maxX = line.start.x;
            if (line.end.x > maxX) maxX = line.end.x;
            if (line.start.y > maxY) maxY = line.start.y;
            if (line.end.y > maxY) maxY = line.end.y;
        }
        int max = Math.max(maxX, maxY);
        int[][] grid = new int[max + 1][max + 1];
        System.out.println(maxX + " " + maxY);

        return grid;
    }

    @Override
    public Object run1(String input) {
        var lines = parseInput(input);
        lines.removeIf(line -> line.start.x != line.end.x && line.start.y != line.end.y); // horizontal/vertical only
        System.out.println(lines);
        var grid = makeGrid(lines);


        for (var line : lines) {
            for (var point : line) {
                grid[point.y][point.x] += 1;
            }
        }

        System.out.println();
        print(grid);


        int overlaps = 0;
        // iterate grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] >= 2) {
                    overlaps++;
                }
            }
        }

        return overlaps;
    }

    @Override
    public Object run2(String input) {
        var lines = parseInput(input);
        System.out.println(lines);
        var grid = makeGrid(lines);


        for (var line : lines) {
            for (var point : line) {
                grid[point.y][point.x] += 1;
            }
        }

        System.out.println();
        print(grid);


        int overlaps = 0;
        // iterate grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] >= 2) {
                    overlaps++;
                }
            }
        }

        return overlaps;
    }

    public static void main(String[] args) {
        new Day5();
    }


    class Point {
        int x;
        int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    class Line implements Iterable<Point> {
        Point start;
        Point end;

        boolean contains(Point point) {
            if (point == start || point == end) return true;
            if (start.equals(point) || end.equals(point)) return true;
            if (start.x == point.x || end.x == point.x) {
                if (point.y >= start.y && point.y <= end.y) return true;
            }
            if (start.y == point.y || end.y == point.y) {
                if (point.x >= start.x && point.x <= end.x) return true;
            }
            return false;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Objects.equals(start, line.start) && Objects.equals(end, line.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }

        @Override
        public Iterator<Point> iterator() {
            return new Iterator<>() {

                int x = start.x;
                int y = start.y;
                final int dx = Math.abs(end.x - start.x);
                final int dy = Math.abs(end.y - start.y);
                final int stepX = Integer.compare(end.x, start.x);
                final int stepY = Integer.compare(end.y, start.y);
                boolean done = false;


                @Override
                public boolean hasNext() {
                    return !done;
                }

                @Override
                public Point next() {
                    Point point = new Point();
                    point.x = x;
                    point.y = y;
                    if (point.equals(end)) done = true;
                    //System.out.println(x+" "+y);
                    x += stepX;
                    y += stepY;
                    return point;
                }
            };
        }
    }

}
