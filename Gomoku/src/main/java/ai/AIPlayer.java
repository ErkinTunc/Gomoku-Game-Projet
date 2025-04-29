
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
 * <h2>Future Developments:</h2>
 * <ul>
 *  <li>might add Minimax algorithm for smarter AI</li>
 *  <li>might add Alpha-Beta pruning for optimization</li>
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
    // TODO: for the future PRNG might be used to track future choices.
    private static final Random random = new Random();

    /** Number of pieces needed to win. */
    private int winLength;

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an AI player with default piece count.
     *
     * @param name the name of the player
     * @param playerColor the color of the player
     * @param winLength the number of pieces needed to win
     * @throws IllegalArgumentException if winlength lesser than 0
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
     * @param playerColor the color of the player
     * @param winLength the number of pieces needed to win
     * @param pieceNum starting pieces
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
     * Overrides the abstract method from Player. Uses the simple AI logic.
     * 
     * TODO: Make the algo much stronger with Minimax and ALpha-Beta pruning(for optimization, for not choosing every dumb route).
     *
     * @param grid the current game grid
     * @return the coordinates [row, col] of the selected move
     */
    @Override
    public int[] choosePieceLocation(Grid grid) {
        return choosePieceLocation(grid, this.winLength); // dynamique value
    }

    /**
     * Gives the best AI choice for the next move . Also this function does it with score system.
     * This function simulates moves, evaluates them and selects the highest-scoring one.
     *
     * @param grid the current game grid
     * @param winLength the number of aligned pieces needed to win
     * @return an array[2] which is the "bestmove" ai can ever make
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
                if (grid.getPiece(row, col) == null && grid.hasNeighbor(row, col)) {
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
     * Considers both options for offensive and defensive .
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
     * @param grid the game board.
     * @param nextPiece the piece to evaluate
     * @param winLength the required number of aligned pieces to win
     * @return an integer score which is the best duo to scroing logic
     *
     * @throws NullPointerException if {@code grid} or {@code nextPiece} is null
     * @throws IllegalArgumentException if {@code winLength <= 0}
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
     * Checks whether placing a hypothetical piece(in same color) of the given color at the specified
     * grid location would form an open sequence of the given length in the specified direction.
     *
     * @param grid the game board
     * @param row the row index of the simulated move
     * @param col the column index of the simulated move
     * @param color the color of the simulated piece
     * @param dir the direction to evaluate
     * @param length the desired sequence length
     * @return {@code true} if there is an OpenSequence, {@code false} otherwise
     *
     */
    private boolean isOpenSequenceAt(Grid grid, int row, int col, int color, Direction dir, int length) {
        return isSequenceOfType(grid, row, col, color, dir, length, SequenceType.OPEN);
    }
    
    /**
     * Checks whether placing a hypothetical piece(in same color) of the given color at the specified
     * grid location would form a semi-open sequence of the given length in the specified direction.
     *
     * @param grid the game board
     * @param row the row index of the simulated move
     * @param col the column index of the simulated move
     * @param color the color of the simulated piece
     * @param dir the direction to evaluate
     * @param length the desired sequence length
     * @return {@code true} if there is an semiOpenSequence, {@code false} otherwise
     */
    private boolean isSemiOpenSequenceAt(Grid grid, int row, int col, int color, Direction dir, int length) {
        return isSequenceOfType(grid, row, col, color, dir, length, SequenceType.SEMI_OPEN);
    }

    /**
     * Evaluates whether a simulated move at the given coordinates would result in either an open/semi-open sequence 
     * of a specified length, depending on the chosen {@link SequenceType}.
     * <p>
     * A sequence is defined as a consecutive line of same-colored pieces in the specified direction.
     * An <b>open</b> sequence requires both ends of the line to be unoccupied (null),
     * while a <b>semi-open</b> sequence requires at least one open end.
     * </p>
     *
     * @param grid the game board
     * @param row the row index of the simulated move
     * @param col the column index of the simulated move
     * @param color the color of the simulated piece
     * @param dir the direction to evaluate
     * @param length the desired sequence length
     * @param type the sequence type to test (OPEN or SEMI_OPEN)
     * @return {@code true} if the corresponding sequence is formed, {@code false} otherwise
     * 
     * @throws NullPointerException if {@code grid} is null "direction" is null or "type" is null 
     * @throws IndexOutOfBoundsException if the specified position is outside the grid bounds
     * @throws IllegalArgumentException if {@code length <= 0}, {@code color} is invalid,
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
            throw new NullPointerException("Direction cannot be null.");
        }
        if (type == null || (type != SequenceType.OPEN && type != SequenceType.SEMI_OPEN)) {
            throw new NullPointerException("Sequence type cannot be null.");
        }

        if (grid.getPiece(row, col) != null) return false;

        int forward = Grid.countSameColorInDirection(grid, row, col, color, dir);
        int backward = Grid.countSameColorInDirection(grid, row, col, color, dir.getOpposite());
        int count = 1 + forward + backward;

        if (count != length) return false;

        int fr = row + dir.getX() * forward + dir.getX(); //Forward Row
        int fc = col + dir.getY() * forward + dir.getY(); //Forward Column
        int br = row + dir.getOpposite().getX() * backward + dir.getOpposite().getX(); //Backward Row
        int bc = col + dir.getOpposite().getX() * backward + dir.getOpposite().getY(); //Backward Column

        boolean forwardOpen = grid.inBounds(fr, fc) && grid.getPiece(fr, fc) == null;
        boolean backwardOpen = grid.inBounds(br, bc) && grid.getPiece(br, bc) == null;

        return switch (type) {
            case OPEN -> forwardOpen && backwardOpen;
            case SEMI_OPEN -> forwardOpen || backwardOpen;
        };
    }

    /**
     * Enum representing the types of sequences: OPEN or SEMI_OPEN.
     */
    private enum SequenceType {
        /** An open sequence where both ends are empty.*/
        OPEN,
        /** A semi-open sequence where at least one end is empty.*/
        SEMI_OPEN
    }

}
