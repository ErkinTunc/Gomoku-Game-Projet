/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import util.ColorInConsole;

/**
 * Represents the 2D board (grid) for the Gomoku game. Manages the placement of
 * pieces, neighbor connections, and alignment checks.
 *
 * The grid is initialized as a square matrix (default 15x15). After each move,
 * the grid updates directional neighbor links between pieces.
 *
 * Provides alignment-checking logic for win conditions.
 *
 * @author Erkin Tunc Boya
 * @version 1.2
 * @since 2025-03-26
 */
public class Grid implements Serializable{

    /**
     * The 2D array representing the game grid. Each cell can hold a Piece or be
     * null if empty.
     */
    private Piece[][] grid;
    /**
     * The size of the grid (number of rows and columns). The grid is square.
     */
    private int size; // ex: 15x15 // carre // size should always be odd, if not we can not place first peace at the center.

    /**
     * Serial version UID for serialization compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a default 15x15 grid.
     */
    public Grid() {
        this.size = 15; // we assume that size is 15 in default.
        this.grid = new Piece[size][size];
    }

    /**
     * Constructs a square grid of the given size.
     *
     * @param size the number of rows and columns (square)
     * @throws IllegalArgumentException if the size is not odd
     * @throws IllegalArgumentException if the size is not positive
     */
    public Grid(int size) {
        if (size % 2 != 1) {
            throw new IllegalArgumentException("Grid size should be odd");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Grid size should be more than 0");
        }
        this.size = size;
        this.grid = new Piece[size][size];
    }

    /**
     * Places the first piece on the board. Should be placed at the center of
     * the grid.
     *
     * @param i the row index (should be the center row)
     * @param j the column index (should be the center column)
     * @param piece the piece to place
     */
    public void placeTheFirstPiece(int i, int j, Piece piece) {
        // First piece should be place in the center
        this.grid[i][j] = piece;
        modifyNeighbours(piece);
    }

    /**
     * Places a new piece at the given position and links its neighbors.
     *
     * @param piece the piece to be placed
     * @param row the row index
     * @param col the column index
     * @throws IllegalArgumentException if the cell is already occupied
     */
    public void placePiece(Piece piece, int row, int col) {
        // The piece you are placing should be next to another Piece which is on the board
        if (grid[row][col] != null) {
            throw new IllegalArgumentException("Cell already occupied.");
        }
        if (!isAdjacentToAnotherPiece(row, col)) {
            throw new IllegalArgumentException("You must place your piece adjacent to an existing one.");
        }

        this.grid[row][col] = piece;

        modifyNeighbours(piece); //Modify naihbours of the piece
        // fivePiecesAlligned() // checks if the new piece creates an 5 piece alligned
    }

    /**
     * Checks whether the given cell has any adjacent non-null neighbors.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if at least one neighbor is occupied; false otherwise
     */
    public boolean isAdjacentToAnotherPiece(int row, int col) {
        for (Direction dir : Direction.values()) {
            int newRow = row + dir.dx;
            int newCol = col + dir.dy;

            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                if (grid[newRow][newCol] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the specified piece completes a winning alignment.
     * <p>
     * It verifies if the piece is part of a straight line (horizontal,
     * vertical, or diagonal) containing at least {@code alligningNum}
     * consecutive pieces of the same color. The method searches in 4 direction
     * pairs (LEFT+RIGHT, UP+DOWN, and both diagonals), counting how many pieces
     * are connected in both directions starting from the given piece.
     * </p>
     *
     * @param piece the newly placed piece to check alignment from
     * @param alligningNum the required number of consecutive pieces to win
     * @return {@code true} if the alignment condition is met, {@code false}
     * otherwise
     */
    public boolean fivePiecesAlligned(Piece piece, int alligningNum) {
        // Define 4 direction pairs to cover horizontal, vertical, and both diagonals
        Direction[][] directionPairs = {
            {Direction.LEFT, Direction.RIGHT},
            {Direction.UP, Direction.DOWN},
            {Direction.UP_LEFT, Direction.DOWN_RIGHT},
            {Direction.UP_RIGHT, Direction.DOWN_LEFT}
        };

        // Iterate over each direction pair
        for (Direction[] pair : directionPairs) {
            int count = 1; // start with the current piece

            // Count same-colored pieces in both directions
            count += countInDirection(piece, pair[0]);
            count += countInDirection(piece, pair[1]);

            if (count >= alligningNum) {
                return true; // alignment of 5 found
            }
        }

        return false;
    }

    /**
     * Counts how many consecutive pieces of the same color are connected from
     * the given piece in the specified direction.
     * <p>
     * Traverses the chain of neighbors in that direction using
     * {@link Piece#getNeighbor(Direction)} until it reaches a piece of a
     * different color or a null neighbor.
     * </p>
     *
     * @param piece the starting piece
     * @param dir the direction to count toward
     * @return the number of consecutive same-colored neighbors in that
     * direction
     */
    public static int countInDirection(Piece piece, Direction dir) {
        int count = 0;

        // Start with the immediate neighbor in the given direction
        Piece current = piece.getNeighbor(dir);

        // Continue as long as neighbors exist and are the same color
        while (current != null && current.getColor() == piece.getColor()) {
            count++;
            current = current.getNeighbor(dir);
        }

        return count;
    }

    /**
     * Counts how many consecutive same-colored pieces exist from (row, col) in
     * the specified direction, scanning the grid directly without relying on
     * neighbor links.
     * <p>
     * This method is intended for use by the AIPlayer to evaluate potential
     * moves. It does not require the piece to be placed or linked into the
     * neighbor structure, making it safe for hypothetical simulations.
     * </p>
     *
     * @param grid the game board
     * @param row the starting row (position of simulated piece)
     * @param col the starting column (position of simulated piece)
     * @param color the color of the simulated piece
     * @param dir the direction in which to count
     * @return number of consecutive same-colored pieces in the given direction
     */
    public static int countSameColorInDirection(Grid grid, int row, int col, int color, Direction dir) {
        int count = 0;
        int r = row + dir.dx;
        int c = col + dir.dy;

        while (r >= 0 && r < grid.getSize() && c >= 0 && c < grid.getSize()) {
            Piece piece = grid.getPiece(r, c);
            if (piece != null && piece.getColor() == color) {
                count++;
                r += dir.dx;
                c += dir.dy;
            } else {
                break;
            }
        }

        return count;
    }

    /**
     * Chooses the next move for the AI player based on the current game state.
     * This method delegates to the dynamic version using the player's configured winLength.
     *
     * @param row the row to simulate
     * @param col the column to simulate
     * @param color the color to simulate (0 or 1)
     * @param winLength the number of aligned pieces required to win
     * @return {@code true} if the simulated move would result in a win, otherwise {@code false}
     */
    public boolean wouldAlignWith(int row, int col, int color, int winLength) {
        if (grid[row][col] != null) {
            return false; // The cell is already occupied
        }

        for (Direction[] pair : new Direction[][]{
            {Direction.LEFT, Direction.RIGHT},
            {Direction.UP, Direction.DOWN},
            {Direction.UP_LEFT, Direction.DOWN_RIGHT},
            {Direction.UP_RIGHT, Direction.DOWN_LEFT}}) {

            int aligned = 1 + countSameColorInDirection(this, row, col, color, pair[0])
                    + countSameColorInDirection(this, row, col, color, pair[1]);

            if (aligned >= winLength) {
                return true; // Simulated placement results in alignment
            }
        }

        return false;
    }

    /**
     * Retrieves the piece at the specified row and column.
     *
     * @param row the row index
     * @param col the column index
     * @return the piece at the given position, or null if the cell is empty or
     * out of bounds
     */
    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return null; // Out of bounds
        }
        return grid[row][col];
    }

    /**
     * Checks whether the grid is full (no empty cells).
     *
     * @return true if full, false otherwise
     */
    public boolean isGridFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == null) {
                    return false; // Boş bir hücre varsa grid dolu değil
                }
            }
        }
        return true; // If there are no empty cells, the grid is completely full
    }

    /**
     * Returns the current size of the square grid.
     *
     * @return the number of rows (and columns)
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks whether the specified row and column coordinates are within the
     * boundaries of the game grid.
     *
     * @param r the row index to check
     * @param c the column index to check
     * @return {@code true} if (r, c) lies within the grid bounds; {@code false}
     * otherwise
     */
    public boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    /**
     * Creates a new grid of a larger size, copying the existing pieces to the
     * new grid while maintaining their relative positions.
     *
     * @param newSize the new size for the grid (must be greater than current size)
     * @return a new Grid object with the specified size and copied pieces
     * @throws IllegalArgumentException if the new size is not greater than the current size or not odd
     */
    public Grid expandGrid(int newSize) {
        if (newSize <= size) {
            throw new IllegalArgumentException("New size must be greater than the current size.");
        }
        
        if (newSize % 2 != 1) {
            throw new IllegalArgumentException("New size must be odd.");
        }
        
        Grid newGrid = new Grid(newSize);
        
        // Calculate the offset to center the old grid in the new grid
        int offset = (newSize - size) / 2;
        
        // Copy pieces from old grid to new grid with offset
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.grid[i][j] != null) {
                    // Create a new piece at the offset position
                    Piece oldPiece = this.grid[i][j];
                    Piece newPiece = new Piece(oldPiece.getColor(), i + offset, j + offset);
                    
                    // Place the piece directly in the grid array
                    newGrid.grid[i + offset][j + offset] = newPiece;
                }
            }
        }
        
        // Rebuild all neighbor relationships
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                if (newGrid.grid[i][j] != null) {
                    newGrid.modifyNeighbours(newGrid.grid[i][j]);
                }
            }
        }
        
        return newGrid;
    }


    /**
     * Returns a colored, well-formatted, and coordinate-labeled string
     * representation of the game grid.
     * <p>
     * This method visually renders the entire board using ANSI-colored ASCII
     * symbols:
     * <ul>
     * <li><b>X</b>: Black stone (Player 1)</li>
     * <li><b>O</b>: White stone (Player 2)</li>
     * </ul>
     * <p>
     * The grid is surrounded by row and column numbers on all sides for player
     * orientation. ANSI escape codes are used to color:
     * <ul>
     * <li>Row and column numbers: {@code Red} for columns, {@code Blue} for
     * rows</li>
     * <li>Player pieces: {@code BrightBlack} for Player 1, {@code BrightWhite}
     * for Player 2</li>
     * </ul>
     * <p>
     * Alignment is dynamically maintained for single- and double-digit
     * coordinates. Works best in terminals that support ANSI escape sequences.
     *
     * @return a string representing the current grid state with borders,
     * coordinates, and colored pieces
     */
    @Override
    public String toString() {
        System.out.println("");
        StringBuilder sb = new StringBuilder();
        final int cellWidth = 3; // Width per cell (symbol + space)

        // ==== Print top column numbers ====
        sb.append("\t   ");
        for (int j = 0; j < size; j++) {
            sb.append(ColorInConsole.Red);
            sb.append(String.format("%" + cellWidth + "d", j));
            sb.append(ColorInConsole.Reset);
        }
        sb.append("\n");

        // ==== Build row separator ====
        String rowSeparator = "\t   " + "-".repeat(cellWidth * size) + "\n";

        for (int i = 0; i < size; i++) {
            sb.append(rowSeparator);

            // ==== Left row number ====
            sb.append(ColorInConsole.Blue);
            sb.append(String.format("\t%2d ", i));
            sb.append(ColorInConsole.Reset);

            // ==== Grid content ====
            for (int j = 0; j < size; j++) {
                Piece piece = grid[i][j];
                sb.append("|");
                if (piece == null) {
                    sb.append("  ");
                } else if (piece.getColor() == 0) {
                    sb.append(ColorInConsole.BrightWhite + piece.toString() + ColorInConsole.Reset + " ");
                } else {
                    sb.append(ColorInConsole.BrightBlack + piece.toString() + ColorInConsole.Reset + " ");
                }
            }

            // ==== Right row number ====
            sb.append("| ");
            sb.append(ColorInConsole.Blue);
            sb.append(String.format("%2d", i));
            sb.append(ColorInConsole.Reset);
            sb.append("\n");
        }

        // ==== Final bottom border ====
        sb.append(rowSeparator);

        // ==== Print bottom column numbers ====
        sb.append("\t   ");
        for (int j = 0; j < size; j++) {
            sb.append(ColorInConsole.Red);
            sb.append(String.format("%" + cellWidth + "d", j));
            sb.append(ColorInConsole.Reset);
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Updates the neighbor references of the given piece in all 8 directions.
     * <p>
     * For each direction, if there is an adjacent piece on the board, this
     * method links the given piece to it using {@code setNeighbor}, and vice
     * versa. It ensures bidirectional connectivity between adjacent pieces.
     * </p>
     *
     * @param piece the piece whose neighbors are to be updated
     */
    private void modifyNeighbours(Piece piece) {
        // Also think of the corners

        // getting the location of our new piece
        int row = piece.getRow();
        int col = piece.getCol();

        // we go to every neighbor
        for (Direction dir : Direction.values()) {
            int newRow = row + dir.dx;
            int newCol = col + dir.dy;

            // checks if our new location is in the grid
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                Piece neighbor = grid[newRow][newCol];
                // we make a link between the neighbors
                if (neighbor != null) {
                    piece.setNeighbor(dir, neighbor);
                    neighbor.setNeighbor(dir.getOpposite(), piece);
                }
            }
        }
    }

}
