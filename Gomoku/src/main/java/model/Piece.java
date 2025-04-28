/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.EnumMap;

/**
 * Represents a single piece on the Gomoku board. Each piece knows its position,
 * color, and its neighbors in 8 directions.
 *
 * Color codes: - 0: White (O) - 1: Black (X)
 *
 * Neighbor links enable directional traversal of the board.
 *
 * @author Erkin Tunc Boya
 * @version 1.1
 * @since 2025-03-26
 */
public class Piece implements Serializable {

    /** Color of the piece which is white(0) or black(1) */
    private final int color; // 0/1 -> white/black
    /**
     * EnumMap to store neighboring pieces in 8 directions (UP, DOWN, LEFT, RIGHT,
     * UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT).
     */
    private EnumMap<Direction, Piece> neighbors;

    /** Row index of the piece on the board.*/
    private final int row;
    /** Column index of the piece on the board.*/
    private final int col;

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new piece with a given color and position on the board.
     *
     * @param color the color of the piece (0 = white, 1 = black)
     * @param row the row index (must be ≥ 0)
     * @param col the column index (must be ≥ 0)
     * @throws IllegalArgumentException if position is negative or color is
     * invalid
     */
    public Piece(int color, int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Piece's location (rows and collumns) should be 0 or positif.");
        }
        if (color != 0 && color != 1) {
            throw new IllegalArgumentException("Piece color must be 0 (white) or 1 (black).");
        }

        this.color = color;
        this.neighbors = new EnumMap<>(Direction.class);
        this.row = row;
        this.col = col; // collone
    }

    /**
     * Sets a neighboring piece in the specified direction.
     *
     * @param direction the direction of the neighbor
     * @param neighbor the neighboring piece
     */
    public void setNeighbor(Direction direction, Piece neighbor) {
        neighbors.put(direction, neighbor);
    }

    /**
     * Gets the neighboring piece in the specified direction.
     *
     * @param direction the direction to look in
     * @return the neighboring piece, or null if none exists
     */
    public Piece getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    /**
     * Returns the map of all neighbors for this piece.
     *
     * @return EnumMap of Direction to Piece
     */
    public EnumMap<Direction, Piece> getNeighbors() {
        return neighbors;
    }

    /**
     * Returns true if there is a neighboring piece in the given direction.
     *
     * @param direction the direction to check
     * @return true if a neighbor exists, false otherwise
     */
    public boolean hasNeighbor(Direction direction) {
        return neighbors.containsKey(direction);
    }

    /**
     * Returns the color of the piece.
     *
     * @return 0 for white, 1 for black
     */
    public int getColor() {
        return color;
    }

    /**
     * Returns the row index of this piece on the board.
     *
     * @return row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of this piece on the board.
     *
     * @return column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns a string representation of the piece. "O" for white (0), "X" for
     * black (1).
     *
     * @return "O" or "X" based on the color
     */
    @Override
    public String toString() {
        return (color == 0) ? "O" : "X";
    }
}
