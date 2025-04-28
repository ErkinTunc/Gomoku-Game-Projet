/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Direction;
import model.Grid;
import model.Piece;
import model.Player;


/**
 * Represents an AI-controlled player in the Gomoku game.
 * <p>
 * This AI evaluates all valid moves using a scoring system that accounts for
 * open and semi-open sequences. It selects the move with the highest strategic value,
 * adapting dynamically to different win conditions.
 * </p>
 *
 * <h2>Core Features:</h2>
 * <ul>
 *   <li>Grid-based open and semi-open sequence detection</li>
 *   <li>Dynamic win condition support (configurable win length)</li>
 *   <li>Threat blocking and winning move detection</li>
 *   <li>Evaluation scores bounded for consistency</li>
 *   <li>Extension-ready: supports future Minimax or heuristic upgrades</li>
 * </ul>
 *
 * @author Erkin Tunc Boya
 * @version 1.3
 * @since 2025-04-20
 */
public class AIPlayer extends Player {

    /**
     * Random number generator for selecting moves when no optimal move is found.
     */
    private static final Random random = new Random();

    /** Number of pieces needed to win. */
    private int winLength;

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an AI player with default piece count.
     *
     * @param name the name of the player
     * @param playerColor the color of the player (0 for white, 1 for black)
     * @param winLength the number of pieces needed to win
     * @throws IllegalArgumentException if {@code winLength <= 0}
     */
    public AIPlayer(String name, int playerColor, int winLength) {
        super(name, playerColor);
        if (winLength <= 0) {
            throw new IllegalArgumentException("winLength must be positive.");
        }
        this.winLength = winLength;
    }

    /**
     * Constructs an AI player with custom piece count and win condition.
     *
     * @param name the name of the player
     * @param playerColor the color of the player (0 for white, 1 for black)
     * @param winLength the number of pieces needed to win
     * @param pieceNum the initial number of pieces
     * @throws IllegalArgumentException if {@code winLength <= 0}
     */
    public AIPlayer(String name, int playerColor, int winLength, int pieceNum) {
        super(name, playerColor, pieceNum);
        if (winLength <= 0) {
            throw new IllegalArgumentException("winLength must be positive.");
        }
        this.winLength = winLength;
    }

    

    /**
     * Overrides the abstract method from Player. Internally calls the smarter
     * choosePieceLocation method with stored winLength.
     *
     * @param grid the current game grid
     * @return the coordinates [row, col] of the selected move
     */
    @Override
    public int[] choosePieceLocation(Grid grid) {
        return choosePieceLocation(grid, this.winLength); // dynamique value
    }

    /**
     * Determines the best move for the AI based on board evaluation.
     * This method considers all adjacent valid empty positions on the board,
     * simulates moves, evaluates them with a heuristic, and selects the highest-scoring one.
     *
     * @param grid the current game grid; must not be {@code null}
     * @param winLength the number of aligned pieces needed to win; must be {@code >= 1}
     * @return a 2-element array {@code [row, col]} indicating the AI's selected move
     * @throws NullPointerException if {@code grid} is {@code null}
     * @throws IllegalArgumentException if {@code winLength <= 0}
     */
    public int[] choosePieceLocation(Grid grid, int winLength) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null.");
            
        }
        if (winLength <= 0) {
            throw new IllegalArgumentException("winLength must be positive.");
        }
        List<int[]> validMoves = new ArrayList<>();
        int size = grid.getSize();
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid.getPiece(row, col) == null && grid.isAdjacentToAnotherPiece(row, col)) {
                    validMoves.add(new int[]{row, col});

                    // Simulate the move
                    Piece simulatedPiece = new Piece(this.playerColor, row, col);
                    int score = gomokuEvaluater(grid, simulatedPiece, winLength); // dynamique winLength

                    //Testing
                    // System.out.println("Simulating move at (" + row + "," + col + ") = " + score + " pts");

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }

        if (bestMove == null) {
            // Fallback: If there is no good option it will play randomly
            bestMove = validMoves.get(random.nextInt(validMoves.size()));
        }

        System.out
                .println(name + " (AI) played at (" + bestMove[0] + ", " + bestMove[1] + ") with score = " + bestScore);
        return bestMove;
    }


    /**
     * Updates the number of pieces required to win for the AI logic.
     *
     * @param winLength the new win condition
     * @throws IllegalArgumentException if {@code winLength <= 0}
     */
    public void setWinLength(int winLength) {
        if (winLength <= 0) {
            throw new IllegalArgumentException("winLength must be positive.");
        }
        this.winLength = winLength;
    }

    /**
     * Returns the current number of pieces required to win.
     *
     * @return the win condition for this AI
     */
    public int getWinLength() {
        return winLength;
    }

    /**
     * Evaluates the potential score of placing a given piece at a location.
     * Considers both offensive (AI's own sequences) and defensive (opponent's threats).
     * Winning moves return {@link Integer#MAX_VALUE}, and threats are prioritized.
     *
     * Scoring tiers:
     * <ul>
     *   <li>Open (n-1): +50</li>
     *   <li>Open (n-2): +20</li>
     *   <li>Semi-open (n-1): +30</li>
     *   <li>Semi-open (n-2): +10</li>
     *   <li>Block opponent win: +{@code Integer.MAX_VALUE / 2}</li>
     * </ul>
     *
     * @param grid the game board to simulate on; must not be {@code null}
     * @param nextPiece the piece to evaluate; must not be {@code null}, and its position must be empty on the grid
     * @param winLength the required number of aligned pieces to win; must be positive
     * @return an integer score representing the move's potential value; higher is better
     *
     * @throws NullPointerException if {@code grid} or {@code nextPiece} is {@code null}
     * @throws IndexOutOfBoundsException if {@code nextPiece}'s position is outside the board
     * @throws IllegalArgumentException if {@code winLength <= 0} or {@code nextPiece.getColor()} is not 0 or 1
     */
    private int gomokuEvaluater(Grid grid, Piece nextPiece, int winLength) {
        if (grid == null) {
            throw new NullPointerException("Grid cannot be null.");
        }
        
        if (nextPiece == null) {
            throw new NullPointerException("Next piece cannot be null.");
        }
        
        if (winLength <= 0) {
            throw new IllegalArgumentException("Win length must be greater than zero.");
        }

        int row = nextPiece.getRow();
        int col = nextPiece.getCol();
        int color = nextPiece.getColor();
        int score = 0;

        // === Winning move? ===
        if (grid.wouldAlignWith(row, col, color, winLength)) {
            return Integer.MAX_VALUE;
        }

        // === AI's own pattern scoring ===
        for (Direction dir : Direction.values()) {
            if (isOpenSequenceAt(grid, row, col, color, dir, winLength - 1)) {
                score += 50;
            } else if (isOpenSequenceAt(grid, row, col, color, dir, winLength - 2)) {
                score += 20;
            } else if (isSemiOpenSequenceAt(grid, row, col, color, dir, winLength - 1)) {
                score += 30;
            } else if (isSemiOpenSequenceAt(grid, row, col, color, dir, winLength - 2)) {
                score += 10;
            }
        }

        // === Opponent winning threat? ===
        int enemyColor = (color == 0) ? 1 : 0;

        if (grid.wouldAlignWith(row, col, enemyColor, winLength)) {
            score += Integer.MAX_VALUE / 2;
        }

        for (Direction dir : Direction.values()) {
            if (isOpenSequenceAt(grid, row, col, enemyColor, dir, winLength - 1)) {
                score += 40;
            } else if (isSemiOpenSequenceAt(grid, row, col, enemyColor, dir, winLength - 1)) {
                score += 15;
            }
        }

        return score;
    }



    /**
     * Checks whether placing a hypothetical piece of the given color at the specified
     * grid location would form an open sequence of the given length in the specified direction.
     * <p>
     * An open sequence is a contiguous group of aligned same-colored pieces that is exactly
     * {@code length} long and has both ends unoccupied.
     * </p>
     *
     * @param grid the game board; must not be {@code null}
     * @param row the row index of the simulated move; must be within grid bounds
     * @param col the column index of the simulated move; must be within grid bounds
     * @param color the color of the simulated piece; must be {@code 0} (white) or {@code 1} (black)
     * @param dir the direction to evaluate; must not be {@code null}
     * @param length the desired sequence length; must be positive
     * @return {@code true} if an open sequence is formed, {@code false} otherwise
     *
     * @throws NullPointerException if {@code grid} or {@code dir} is {@code null}
     * @throws IndexOutOfBoundsException if {@code row} or {@code col} is out of bounds
     * @throws IllegalArgumentException if {@code length <= 0} or {@code color} is invalid
     */
    private boolean isOpenSequenceAt(Grid grid, int row, int col, int color, Direction dir, int length) {
        return isSequenceOfType(grid, row, col, color, dir, length, SequenceType.OPEN);
    }
    
    /**
     * Checks whether placing a hypothetical piece of the given color at the specified
     * grid location would form a semi-open sequence of the given length in the specified direction.
     * <p>
     * A semi-open sequence is a contiguous group of aligned same-colored pieces that is exactly
     * {@code length} long and has at least one end unoccupied.
     * </p>
     *
     * @param grid the game board
     * @param row the row index of the simulated move
     * @param col the column index of the simulated move
     * @param color the color of the simulated piece (e.g., 0 for white, 1 for black)
     * @param dir the direction in which to evaluate alignment
     * @param length the number of aligned pieces to consider
     * @return {@code true} if a semi-open sequence would be formed, {@code false} otherwise
     * @throws IndexOutOfBoundsException if the specified position is outside the board
     */
    private boolean isSemiOpenSequenceAt(Grid grid, int row, int col, int color, Direction dir, int length) {
        return isSequenceOfType(grid, row, col, color, dir, length, SequenceType.SEMI_OPEN);
    }

    /**
     * Evaluates whether a simulated move at the given coordinates would result in either an open or
     * semi-open sequence of a specified length, depending on the chosen {@link SequenceType}.
     * <p>
     * A sequence is defined as a consecutive line of same-colored pieces in the specified direction.
     * An <b>open</b> sequence requires both ends of the line to be unoccupied (null),
     * while a <b>semi-open</b> sequence requires at least one open end.
     * </p>
     *
     * <b>Validation &amp; Safety:</b>
     * <ul>
     *   <li>Throws {@code NullPointerException} if the grid is null</li>
     *   <li>Throws {@code IndexOutOfBoundsException} if the coordinates are outside the board</li>
     *   <li>Throws {@code IllegalArgumentException} if length is non-positive, color is invalid, or direction/type is null</li>
     * </ul>
     *
     * @param grid the game board
     * @param row the row index of the simulated move
     * @param col the column index of the simulated move
     * @param color the color of the simulated piece (must be 0 or 1)
     * @param dir the direction to check for alignment (must not be null)
     * @param length the exact sequence length to check (must be positive)
     * @param type the sequence type to test (OPEN or SEMI_OPEN)
     * @return {@code true} if the corresponding sequence is formed, {@code false} otherwise
     * @throws NullPointerException if {@code grid} is null
     * @throws IndexOutOfBoundsException if the specified position is outside the grid bounds
     * @throws IllegalArgumentException if {@code length <= 0}, {@code color} is invalid,
     *         or if {@code dir} or {@code type} is null
     */
    private boolean isSequenceOfType(Grid grid, int row, int col, int color, Direction dir, int length, SequenceType type) {
        if (grid == null) {
            throw new NullPointerException("Grid cannot be null.");
        }
        if (!grid.inBounds(row, col)) {
            throw new IndexOutOfBoundsException("Coordinates out of grid bounds.");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive.");
        }
        if (color != 0 && color != 1) {
            throw new IllegalArgumentException("Color must be 0 (white) or 1 (black).");
        }
        if (dir == null) {
            throw new IllegalArgumentException("Direction cannot be null.");
        }
        if (type == null || (type != SequenceType.OPEN && type != SequenceType.SEMI_OPEN)) {
            throw new IllegalArgumentException("Sequence type cannot be null.");
        }

        if (grid.getPiece(row, col) != null) return false;

        int forward = Grid.countSameColorInDirection(grid, row, col, color, dir);
        int backward = Grid.countSameColorInDirection(grid, row, col, color, dir.getOpposite());
        int count = 1 + forward + backward;

        if (count != length) return false;

        int fr = row + dir.dx * forward + dir.dx; //Forward Row
        int fc = col + dir.dy * forward + dir.dy; //Forward Column
        int br = row + dir.getOpposite().dx * backward + dir.getOpposite().dx; //Backward Row
        int bc = col + dir.getOpposite().dy * backward + dir.getOpposite().dy; //Backward Column

        boolean forwardOpen = grid.inBounds(fr, fc) && grid.getPiece(fr, fc) == null;
        boolean backwardOpen = grid.inBounds(br, bc) && grid.getPiece(br, bc) == null;

        return switch (type) {
            case OPEN -> forwardOpen && backwardOpen;
            case SEMI_OPEN -> forwardOpen || backwardOpen;
        };
    }

    /**
     * Enum representing the types of sequences: OPEN or SEMI_OPEN.
     *
     * <p>
     * OPEN: Both ends of the sequence are unoccupied.
     * SEMI_OPEN: At least one end of the sequence is unoccupied.
     * </p>
     */
    private enum SequenceType {
        /**
         * Represents an open sequence where both ends are unoccupied.
         */
        OPEN,
        /**
         * Represents a semi-open sequence where at least one end is unoccupied.
         */
        SEMI_OPEN
    }

}
