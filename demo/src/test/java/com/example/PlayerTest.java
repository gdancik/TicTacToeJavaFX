package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Unit tests for human and CPU player move behavior. */
class PlayerTest {
    /** Verifies a human can place a marker at valid coordinates. */
    @Test
    void humanCanPlaceAMoveFromSelectedCoordinates() {
        Board board = new Board();
        HumanPlayer player = new HumanPlayer('X', new Scanner(""));

        assertTrue(player.makeMove(board, 2, 1));
        assertEquals('X', board.getCell(2, 1));
        assertFalse(player.makeMove(board, 2, 1));
        assertFalse(player.makeMove(board, -1, 0));
    }

    /** Verifies the human player retries malformed and occupied coordinates. */
    @Test
    void humanRetriesMalformedAndOccupiedPositions() {
        Board board = new Board();
        board.place(0, 0, 'O');
        HumanPlayer player = new HumanPlayer('X', new Scanner("bad input\n1 1\n2 3\n"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            player.makeMove(board);
        } finally {
            System.setOut(originalOut);
        }
        assertEquals('X', board.getCell(1, 2));
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("invalid or occupied"));
    }

    /** Verifies the CPU chooses an empty cell and rejects a full board. */
    @Test
    void cpuSelectsAnEmptyCellAndCannotPlayOnAFullBoard() {
        Board board = new Board();
        board.place(0, 0, 'X');
        CpuPlayer cpu = new CpuPlayer('O', new Random(7));
        cpu.makeMove(board);
        assertEquals(1, countMarker(board, 'O'));
        assertEquals('X', board.getCell(0, 0));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (board.isValid(row, column)) {
                    board.place(row, column, 'X');
                }
            }
        }
        assertThrows(IllegalStateException.class, () -> cpu.makeMove(board));
    }

    /** Verifies the player names are valid. */
    @Test
    void testIsValidName() {
        assertEquals("Player #1", HumanPlayer.getName());
        assertEquals("CPU #1", CpuPlayer.getName());
        assertThrows(IllegalArgumentException.class, () -> Player.setName(null));
        assertThrows(IllegalArgumentException.class, () -> Player.setName("   "));
        assertThrows(IllegalArgumentException.class, () -> Player.setName(""));
    }

    /** Verifies that two human players cannot have the same name. */
    @Test
    void testTwoHumanPlayersWithSameName() {
        HumanPlayer player1 = new HumanPlayer('X', new Scanner(""));
        HumanPlayer player2 = new HumanPlayer('O', new Scanner(""));
        
        assertEquals("Player #1", player1.getName());
        assertEquals("Player #2", player2.getName());
    }

    /** Verifies that two CPU players cannot have the same name. */
    @Test
    void testTwoCpuPlayersWithSameName() {
        CpuPlayer cpu1 = new CpuPlayer('X', new Random());
        CpuPlayer cpu2 = new CpuPlayer('O', new Random());
        
        assertEquals("CPU #1", cpu1.getName());
        assertEquals("CPU #2", cpu2.getName());
    }

    /** Counts the occurrences of a marker on the supplied board. */
    private int countMarker(Board board, char marker) {
        int count = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (board.getCell(row, column) == marker) {
                    count++;
                }
            }
        }
        return count;
    }
}