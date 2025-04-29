
package model;

import java.io.Serializable;

/**
 * Abstract class representing a player in the Gomoku game.
 * <p>
 * Each player has a name, a color (0 = White, 1 = Black), and a number of
 * pieces they can place on the board. This class  must be extended by concrete
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
    // Initializes a new game for this player (ex:jeton num=60)
    // attribute should be more than 0 if not it will cause problem in other classes
    public void newGame(int startNumPieces) {
        this.pieceNum = startNumPieces;
    }

    /**
     * Player plays a piece on the board.
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
        pieceNum--; // Decreases pieces at hand after player places it
    }

    /**
     * Checks if the player wins after they placed their last piece(dev should put its coordinates in attributes).
     * And Checks it if there is a allignment(of n pieces | n = allignmentNum).
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
        return grid.nPiecesAlligned(last, allignmentNum);
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
     * Gets the color of the player. it is either 0(white) or 1(black)
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
     * @throws IllegalArgumentException if pieceNum is negative
     */
    public void setPieceNum(int pieceNum) {
        if (pieceNum < 0) {
            throw new IllegalArgumentException("Number of pieces cannot be negative.");}
        
        this.pieceNum = pieceNum;
    }

    /**
     * Returns a string with player info with his name, color  and
     * how many pieces she/he got left.
     * 
     * @return a formatted string describing the player
     */
    @Override
    public String toString() {
        return this.name + " [" + (this.playerColor == 0 ? "White" : "Black") + "] - Pieces left: " + pieceNum; // playerColor = 0 -> white // playerColor = 1 -> black
    }

}
