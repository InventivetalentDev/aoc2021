package org.inventivetalent.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day4 extends Day {

    public Day4() {
        super();
    }

    BingoInput parseInput(String input) {
        List<String> lines = new ArrayList<>(asLines(input));
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
                currentBoard.rows = new BingoField[5][5];
                lineIndex = 0;
                continue;
            }

            System.out.println(line);
            BingoField[] row = Arrays
                    .stream(line.split(" "))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .map(i -> {
                        BingoField field = new BingoField();
                        field.value = Integer.parseInt(i);
                        return field;
                    })
                    .toArray(BingoField[]::new);
            System.out.println(Arrays.toString(row));
            assert currentBoard != null;
            currentBoard.rows[lineIndex] = row;
            lineIndex++;
        }

        return bingoInput;
    }

    BingoField[][] rowsToCols(BingoField[][] rows) {
        // thanks, copilot!
        BingoField[][] cols = new BingoField[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cols[j][i] = rows[i][j];
            }
        }
        return cols;
    }

    boolean check(BingoField[] arr, int n) {
        boolean r = true;
        for (BingoField i : arr) {
            if (i.value == n) i.marked = true;
            if (!i.marked) r = false;
        }
        return r;
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
        BingoField[] winnerRowOrCol = null;

        game:
        while (!numbers.isEmpty()) {
            int n = numbers.remove(0);
            lastDrawn = n;
            drawnNumbers.add(n);

            for (BingoBoard b : bingoInput.boards) {
                for (BingoField[] row : b.rows) {
                    if (check(row, n)) {
                        System.out.println("we have a winner row! " + Arrays.toString(row));
                        winnerBoard = b;
                        winnerRowOrCol = row;
                        break game;
                    }
                }
                for (BingoField[] col : b.cols) {
                    if (check(col, n)) {
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
            for (BingoField[] arr : winnerBoard.rows) {
                for (BingoField i : arr) {
                    if (i.marked) continue;
                    unmarkedNumbersOnBoard.add(i.value);
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
        BingoInput bingoInput = parseInput(input);
        System.out.println(bingoInput.input);
        bingoInput.boards.forEach(b -> {
            b.print();
        });

        List<Integer> numbers = new ArrayList<>(bingoInput.input);
        List<Integer> drawnNumbers = new ArrayList<>();
        int lastDrawn = -1;
        int lastWinnerDraw = -1;

        BingoBoard winnerBoard = null;
        BingoField[] winnerRowOrCol = null;

        game:
        while (!numbers.isEmpty()) {
            int n = numbers.remove(0);
            lastDrawn = n;
            drawnNumbers.add(n);


            boards:
            for (BingoBoard b : bingoInput.boards) {
                if (b.won) continue;
                for (BingoField[] row : b.rows) {
                    if (check(row, n)) {
                        System.out.println("we have a winner row! " + Arrays.toString(row));
                        winnerBoard = b;
                        winnerRowOrCol = row;
                        b.won = true;

                        lastWinnerDraw = lastDrawn;
                        continue boards;
                    }
                }
                for (BingoField[] col : b.cols) {
                    if (check(col, n)) {
                        System.out.println("we have a winner col! " + Arrays.toString(col));
                        winnerBoard = b;
                        winnerRowOrCol = col;
                        b.won = true;

                        lastWinnerDraw = lastDrawn;
                        continue boards;
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
            for (BingoField[] arr : winnerBoard.rows) {
                for (BingoField i : arr) {
                    if (i.marked) continue;
                    unmarkedNumbersOnBoard.add(i.value);
                }
            }
            int unmarkedSum = unmarkedNumbersOnBoard.stream().mapToInt(i -> i).sum();
            System.out.println("unmarked sum: " + unmarkedSum);
            System.out.println("last winner draw: " + lastWinnerDraw);

            return unmarkedSum * lastWinnerDraw;
        }

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

        BingoField[][] rows;
        BingoField[][] cols;
        boolean won;

        void print() {
            for (BingoField[] a : rows) {
                System.out.println(Arrays.toString(a));
            }
            System.out.println(" ");
            for (BingoField[] a : cols) {
                System.out.println(Arrays.toString(a));
            }
            System.out.println(" ");
            System.out.println(" ");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BingoBoard that = (BingoBoard) o;
            return Arrays.deepEquals(rows, that.rows) && Arrays.deepEquals(cols, that.cols);
        }

        @Override
        public int hashCode() {
            int result = Arrays.deepHashCode(rows);
            result = 31 * result + Arrays.deepHashCode(cols);
            return result;
        }

        @Override
        public String toString() {
            return "BingoBoard{" +
                    "board=" + Arrays.toString(rows) +
                    '}';
        }

    }

    class BingoField {

        int value;
        boolean marked;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BingoField that = (BingoField) o;
            return value == that.value && marked == that.marked;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, marked);
        }

        @Override
        public String toString() {
            return "[" + (marked ? "X" : " ") + "] " + value;
        }

    }

}
