package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 extends Day {

    public Day4() {
        super();
    }

    BingoInput parseInput(String input) {
        List<String> lines = new java.util.ArrayList<>(asLines(input));
        BingoInput bingoInput = new BingoInput();

        String firstLine = lines.remove(0);
        String[] firstSplit = firstLine.split(",");
        bingoInput.input = Arrays.stream(firstSplit).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());

        lines.add(""); // insert an empty line at the end so the last board gets added

        bingoInput.boards = new ArrayList<>();
        BingoBoard currentBoard = null;
        int lineIndex = 0;
        for (String line : lines) {
            if (line.isEmpty() || line.isBlank()) {
                // new board start; push current, create new
                if (currentBoard != null) {
                    currentBoard.cols = rowsToCols(currentBoard.rows);
                    bingoInput.boards.add(currentBoard);
                }

                currentBoard = new BingoBoard();
                currentBoard.rows = new int[5][5];
                lineIndex = 0;
                continue;
            }

            System.out.println(line);
            int[] row = Arrays.stream(line.split(" ")).map(String::trim).filter(s -> !s.isBlank()).mapToInt(Integer::parseInt).toArray();
            assert currentBoard != null;
            currentBoard.rows[lineIndex] = row;
            lineIndex++;
        }

        return bingoInput;
    }

    int[][] rowsToCols(int[][] rows) {
        // thanks, copilot
        int[][] cols = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cols[j][i] = rows[i][j];
            }
        }
        return cols;
    }

    boolean check(int[] arr, List<Integer> numbers) {
        for (int i : arr) {
            if (!numbers.contains(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run1(String input) {
        BingoInput bingoInput = parseInput(input);
        System.out.println(bingoInput.input);
        bingoInput.boards.forEach(b -> {
            b.print();
        });

        List<Integer> numbers = new ArrayList<>(bingoInput.input);
        List<Integer> drawnNumbers = new ArrayList<>();
        int lastDrawn = -1;

        BingoBoard winnerBoard = null;
        int[] winnerRowOrCol = null;

        game:
        while (!numbers.isEmpty()) {
            int n = numbers.remove(0);
            lastDrawn = n;
            drawnNumbers.add(n);

            for (BingoBoard b : bingoInput.boards) {
                for (int[] row : b.rows) {
                    if (check(row, drawnNumbers)) {
                        System.out.println("we have a winner row! " + Arrays.toString(row));
                        winnerBoard = b;
                        winnerRowOrCol = row;
                        break game;
                    }
                }
                for (int[] col : b.cols) {
                    if (check(col, drawnNumbers)) {
                        System.out.println("we have a winner col! " + Arrays.toString(col));
                        winnerBoard = b;
                        winnerRowOrCol = col;
                        break game;
                    }
                }
            }
        }

        if (winnerBoard == null) {
            System.out.println("no winner :(");
        } else {
            System.out.println("winner board");
            winnerBoard.print();

            List<Integer> unmarkedNumbersOnBoard = new ArrayList<>();
            for (int[] arr : winnerBoard.rows) {
                for (int i : arr) {
                    if (!drawnNumbers.contains(i)) {
                        unmarkedNumbersOnBoard.add(i);
                    }
                }
            }
            int unmarkedSum = unmarkedNumbersOnBoard.stream().mapToInt(i -> i).sum();
            System.out.println("unmarked sum: " + unmarkedSum);

            return unmarkedSum * lastDrawn;
        }


        return null;
    }

    @Override
    public Object run2(String input) {
        return null;
    }

    public static void main(String[] args) {
        new Day4();
    }

    class BingoInput {

        List<Integer> input;
        List<BingoBoard> boards;

        @Override
        public String toString() {
            return "BingoInput{" +
                    "input=" + input +
                    ", boards=" + boards +
                    '}';
        }

    }

    class BingoBoard {

        int[][] rows;
        int[][] cols;

        void print() {
            for (int[] a : rows) {
                System.out.println(Arrays.toString(a));
            }
            System.out.println(" ");
            for (int[] a : cols) {
                System.out.println(Arrays.toString(a));
            }
            System.out.println(" ");
            System.out.println(" ");
        }

        @Override
        public String toString() {
            return "BingoBoard{" +
                    "board=" + Arrays.toString(rows) +
                    '}';
        }

    }

}
