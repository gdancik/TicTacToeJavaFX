package com.example;

/**
 * 
 * Represents a 3-by-3 Tic-Tac-Toe board and implements its rules. 
 */
public class Board {
    private static final int SIZE = 3;
    private static final char EMPTY = ' ';

    private final char[][] grid = new char[SIZE][SIZE];

    /** Creates an empty board. */
    public Board() {
        initialize();
    }

    /** Clears every cell on the board. */
    public void initialize() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                grid[row][column] = EMPTY;
            }
        }
    }

    /** Prints a formatted representation of the current board. */
    public void display() {
        System.out.println(format());
    }

    /**
     * Formats the board with 1-based row and column labels.
     *
     * @return the formatted board text
     */
    public String format() {
        StringBuilder text = new StringBuilder("  1   2   3\n");
        for (int row = 0; row < SIZE; row++) {
            text.append(row + 1).append(' ');
            for (int column = 0; column < SIZE; column++) {
                text.append(grid[row][column]);
                if (column < SIZE - 1) {
                    text.append(" | ");
                }
            }
            text.append('\n');
            if (row < SIZE - 1) {
                text.append("  -----------\n");
            }
        }
        return text.toString();
    }

    /**
     * Checks whether a position is in bounds and currently empty.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @return {@code true} if the cell is available
     */
    public boolean isValid(int row, int column) {
        return row >= 0 && row < SIZE && column >= 0 && column < SIZE
                 && grid[row][column] == EMPTY;
    }

    /**
     * Places a valid player marker in an available cell.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @param marker marker to place, either {@code X} or {@code O}
     * @return {@code true} if the marker was placed
     */
    public boolean place(int row, int column, char marker) {
        if (!isValid(row, column) || (marker != 'X' && marker != 'O')) {
            return false;
        }
        grid[row][column] = marker;
        return true;
    }

    /**
     * Checks whether every cell contains a marker.
     *
     * @return {@code true} if the board has no empty cells
     */
    public boolean isFull() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether any row, column, or diagonal contains three matching markers.
     *
     * @return {@code true} if a player has won
     */
    public boolean checkWin() {
        for (int index = 0; index < SIZE; index++) {
            if (sameNonEmpty(grid[index][0], grid[index][1], grid[index][2])
                    || sameNonEmpty(grid[0][index], grid[1][index], grid[2][index])) {
                return true;
            }
        }
        return sameNonEmpty(grid[0][0], grid[1][1], grid[2][2])
                || sameNonEmpty(grid[0][2], grid[1][1], grid[2][0]);
    }

    /**
     * Returns the marker in a cell.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @return the cell marker, or a space if the cell is empty
     * @throws IndexOutOfBoundsException if either coordinate is outside the board
     */
    public char getCell(int row, int column) {
        if (row < 0 || row >= SIZE || column < 0 || column >= SIZE) {
            throw new IndexOutOfBoundsException("Board coordinates must be between 0 and 2");
        }
        return grid[row][column];
    }

    /** Checks whether three markers are equal and not empty. */
    private boolean sameNonEmpty(char first, char second, char third) {
        return first != EMPTY && first == second && second == third;
    }
}