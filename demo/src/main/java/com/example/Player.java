package com.example;

/**
 * 
 * Defines the shared marker and move contract for a Tic-Tac-Toe player.
 */ 
public abstract class Player {
    protected final char marker;
    private final String name;

    /**
     * Creates a player with the given marker.
     *
     * @param marker the player's marker, either {@code X} or {@code O}
     * @throws IllegalArgumentException if the marker is not {@code X} or {@code O}
     */
    protected Player(char marker) {
        this(marker, "Player (" + marker + ")");
    }

    /**
     * Creates a player with the given marker and name.
     *
     * @param marker the player's marker, either {@code X} or {@code O}
     * @param name the player's name
     * @throws IllegalArgumentException if the marker is not {@code X} or {@code O}
     * @throws NullPointerException if {@code name} is null
     */
    protected Player(char marker, String name) {
        if (marker != 'X' && marker != 'O') {
            throw new IllegalArgumentException("A player marker must be X or O");
        }
        this.marker = marker;
        this.name = java.util.Objects.requireNonNull(name, "name");
    }

    /**
     * Returns this player's name.
     *
     * @return this player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns this player's marker.
     *
     * @return the player's marker
     */
    public char getMarker() {
        return marker;
    }

    /**
     * Makes a move on the supplied board.
     *
     * @param board board on which to make the move
     */
    public abstract void makeMove(Board board);
}