/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Enum representing the 8 possible directions on the board (4 cardinal + 4
 * diagonal). Each direction holds a delta value (dx, dy) to indicate movement
 * in the grid.
 *
 * Example: - UP(-1, 0) means moving one row up - DOWN_RIGHT(1, 1) means moving
 * diagonally down and to the right
 *
 * @author Erkin Tunc Boya
 * @version 1.2
 * @since 2025-03-26
 */
public enum Direction {
    /** Upward direction (-1 row, 0 column). */
    UP(-1, 0),

    /** Downward direction (+1 row, 0 column). */
    DOWN(1, 0),

    /** Left direction (0 row, -1 column). */
    LEFT(0, -1),

    /** Right direction (0 row, +1 column). */
    RIGHT(0, 1),

    /** Up-left diagonal (-1 row, -1 column). */
    UP_LEFT(-1, -1),

    /** Up-right diagonal (-1 row, +1 column). */
    UP_RIGHT(-1, 1),

    /** Down-left diagonal (+1 row, -1 column). */
    DOWN_LEFT(1, -1),

    /** Down-right diagonal (+1 row, +1 column). */
    DOWN_RIGHT(1, 1);

    /** Row delta for the direction. */
    public final int dx;

    /** Column delta for the direction. */
    public final int dy;

    /**
     * Constructs a direction with the specified row and column deltas.
     *
     * @param dx change in row
     * @param dy change in column
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }


    /**
     * Returns the opposite direction of this direction.
     * 
     * @return the opposite Direction
     * @throws IllegalStateException if no opposite direction is defined
     *                                 for this direction
     */
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP_LEFT:
                return DOWN_RIGHT;
            case UP_RIGHT:
                return DOWN_LEFT;
            case DOWN_LEFT:
                return UP_RIGHT;
            case DOWN_RIGHT:
                return UP_LEFT;
        }
        throw new IllegalStateException("No opposite defined for direction: " + this);
    }

}
