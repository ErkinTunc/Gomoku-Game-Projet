/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 * Abstract base class representing a player in the Gomoku game.
 * <p>
 * Each player has a name, a color (0 = White, 1 = Black), and a number of
 * pieces they can place on the board. This class defines the common behaviors
 * for all types of players (e.g., human or AI) and must be extended by concrete
 * subclasses.
 * </p>
 *
 * @author Erkin Tunc Boya
 * @version 1.3
 * @since 2025-03-26
 */
public abstract class Player implements Serializable {

    /** Player's name. */
    protected String name;
    /** Player's color (0 = White, 1 = Black). */
    protected int playerColor;
    /** Number of pieces the player has left to place on the board. */
    protected int pieceNum;

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a player with a given name and color. Assigns 60 pieces by
     * default.
     *
     * @param name the player's name
     * @param playerColor 0 for white, 1 for black
     * @throws IllegalArgumentException if playerColor is not 0 or 1
     */
    public Player(String name, int playerColor) {
        if (playerColor != 0 && playerColor != 1) {
            throw new IllegalArgumentException("Player color should be 0 or 1.");
        }
        this.name = name;
        this.playerColor = playerColor;
        this.pieceNum = 60;
    }

    /**
     * Constructs a player with custom piece count.
     *
     * @param name the player's name
     * @param playerColor 0 for white, 1 for black
     * @param pieceNum the number of pieces to start with
     */
    public Player(String name, int playerColor, int pieceNum) {
        this.name = name;
        this.playerColor = playerColor;
        this.pieceNum = pieceNum;
    }

    /**
     * Resets the number of pieces for a new game.
     *
     * @param startNumPieces the number of pieces to assign at the start
     */
    // Initializes a new game for this player (jeton num=60)
    public void newGame(int startNumPieces) {
        this.pieceNum = startNumPieces;
    }

    /**
     * Plays a piece at the specified location on the grid. Decreases the
     * player's remaining piece count by 1.
     *
     * @param grid the game grid
     * @param row the row index to place the piece
     * @param col the column index to place the piece
     * @throws IllegalStateException if the player has no pieces left
     */
    public void play(Grid grid, int row, int col) {
        if (pieceNum <= 0) {
            throw new IllegalStateException("No more pieces.");
        }
        Piece piece = new Piece(this.playerColor, row, col);
        grid.placePiece(piece, row, col);
        pieceNum--;
    }

    /**
     * Checks if the player has won the game by placing a piece at the given
     * location.
     *
     * @param grid the game grid
     * @param row the row index of the last placed piece
     * @param col the column index of the last placed piece
     * @param allignmentNum number of pieces required to win (typically 5)
     * @return true if the player has achieved the winning condition, false
     * otherwise
     */
    public boolean hasWon(Grid grid, int row, int col, int allignmentNum) {
        Piece last = grid.getPiece(row, col);
        return grid.fivePiecesAlligned(last, allignmentNum);
    }

    /**
     * Abstract method for selecting the piece location to play. This method
     * must be implemented by subclasses (e.g., HumanPlayer, AIPlayer).
     *
     * @param grid the game grid
     * @return an array of two integers representing row and column indices
     */
    public abstract int[] choosePieceLocation(Grid grid);

    /**
     * Gets the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the color of the player.
     *
     * @return 0 for white, 1 for black
     */
    public int getPlayerColor() {
        return playerColor;
    }

    /**
     * Gets the number of remaining pieces the player has.
     *
     * @return number of pieces left
     */
    public int getPieceNum() {
        return pieceNum;
    }

    /**
     * Sets the number of pieces the player has left.
     *
     * @param pieceNum the new number of pieces left
     */
    public void setPieceNum(int pieceNum) {
        this.pieceNum = pieceNum;
    }

    /**
     * Returns a string representation of the player. Includes player name,
     * colour, and number of pieces remaining.
     *
     * @return a formatted string describing the player
     */
    @Override
    public String toString() {
        return this.name + " [" + (this.playerColor == 0 ? "White" : "Black") + "] - Pieces left: " + pieceNum; // playerColor = 0 -> white // playerColor = 1 -> black
    }

}
