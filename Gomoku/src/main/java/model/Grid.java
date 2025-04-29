
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
 * <p>
 * ToString method is the key method for showing all the grid on the console.
 * </p>
 * 
 * <h2>Design Choices:</h2>
 * 
 * <h3>Why 2D Array?</h3>
 * <p>
 * Chosed 2D array to represent grid, because it was much easier to use insteaad of ADTs. 
 * And as developer I told to myself "I should admire simplicity not complexity".
 * In further developments ı just thought it would be much more harder to apply OOP principles, 
 * if I do not comprehend how it works 100%.
 * </p>
 * <p>
 * If ıt needs to be changed in the future, A linked double linked list might 
 * be used but the developer should also think how to show 8-directional neighbors.
 * </p>
 * 
 * <h3>Expendible Grid</h3>
 * <p>
 * As expected thr grid grows but it uses a simple algorithm to grow the grid. As ı saw the method 
 * in my Algorithm class many times I choose to go with it.
 * </p>
 * <p>
 * This is how it works :
 * when it is full , it will create a new 2D array and copy the pieces to the new one.
 * <b>Note:</b> The grid is not expandable in the middle of the game. It can only be expanded when it is full.
 * </p> 
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
     * Places the first peace at the center of the grid but developer should tell 
     * the centers location explicitly.
     *
     * @param piece The piece to place.
     * @param i Row index.
     * @param j Column index.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public void placeTheFirstPiece(int i, int j, Piece piece) {
        // First piece should be place in the center
        // TODO: automaticcly place the first piece at the cdenter without parameters (maybe you can use Grid size as parameter)
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
        if (!hasNeighbor(row, col)) {
            throw new IllegalArgumentException("You must place your piece adjacent to an existing one.");
        }

        this.grid[row][col] = piece;

        modifyNeighbours(piece); //Modify naihbours of the piece
        // fivePiecesAlligned() // checks if the new piece creates an 5 piece alligned
    }

    /**
     * Checks if chosen cell(piece) has any neighbors in any direction.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if at least one neighbor is occupied; false otherwise
     */
    public boolean hasNeighbor(int row, int col) {
        for (Direction dir : Direction.values()) {
            int newRow = row + dir.getX();
            int newCol = col + dir.getY();

            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                if (grid[newRow][newCol] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This methos checks if piece is aligned with n(alligningNum) pieces in every direction.
     * <p>
     * It verifies if the piece is part of a straight line (horizontal,
     * vertical, or diagonal) containing at least {@code alligningNum}
     * consecutive pieces of the same color. 
     * </p>
     *
     * @param piece the newly placed piece to check alignment from
     * @param alligningNum the required number of consecutive pieces to win
     * @return {@code true} if the alignment condition is met, {@code false}
     * otherwise
     */
    public boolean nPiecesAlligned(Piece piece, int alligningNum) {
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
     *
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
        int newRow = row + dir.getX();
        int newCol = col + dir.getY();

        while (newRow >= 0 && newRow < grid.getSize() && newCol >= 0 && newCol < grid.getSize()) {
            Piece piece = grid.getPiece(newRow, newCol);
            if (piece != null && piece.getColor() == color) {
                count++;
                newRow += dir.getX();
                newCol += dir.getY();
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
                    return false; // when there is a empty cell in the grid
                }
            }
        }
        return true; // when there is no empty cell
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
     * Checks if the chosen location is in the grid bounds.
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
     * This function creates a bigger grid which is almost 2 times bigger ("2 times - 1" becuse it should be odd).
     * After that it will copy the pieces from the old grid and place them in the new grid bur it's referance point is the center.
     * Soo it isnot a nromal array copy, because placements are different.
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
        int extraPos = (newSize - size) / 2; // extraPos is the the the differnce between old one divided by 2 because we think like it will be centered.
        
        // Copy pieces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.grid[i][j] != null) {
                    // Create a new piece at the offset position
                    Piece oldPiece = this.grid[i][j];
                    Piece newPiece = new Piece(oldPiece.getColor(), i + extraPos, j + extraPos);
                    
                    // Place the piece 
                    newGrid.grid[i + extraPos][j + extraPos] = newPiece;
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
     * Returns a colored, well-formatted, string
     * representation of the game grid.
     * <p>
     * This method visually renders the entire board using ANSI-colored ASCII
     * symbols:
     * <ul>
     * <li><b>X</b>: Black stone (Player 1)</li>
     * <li><b>O</b>: White stone (Player 2)</li>
     * </ul>
     *
     * @return a string representing the current grid state with borders,
     * coordinates, and colored pieces
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final int cellWidth = 4;

        // Top column enumareter (red)
        sb.append("   "); // padding fdor red collumn ligne
        for (int col = 0; col < size; col++) {
            sb.append(ColorInConsole.Red);
            sb.append(String.format("%" + cellWidth + "d", col));
            sb.append(ColorInConsole.Reset);
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) { // Lignes

            // Left row number
            sb.append(ColorInConsole.Blue);
            sb.append(String.format("%" + cellWidth + "d", row));
            sb.append(ColorInConsole.Reset);
            sb.append("  "); // padding for left border

            // Pieces and null places(no pieces)
            for (int col = 0; col < size; col++) {
                Piece piece = grid[row][col];
                String rawSymbol = ".";

                if (piece != null) {
                    rawSymbol = piece.toString(); // typically "X" or "O"
                }

                String paddedSymbol = String.format("%-" + cellWidth + "s", rawSymbol); // "%" -> Starts the format specifier. | "-" -> Left-align the content. | "4" -> cellWitdh total width of printed string | s -> represents a string  |
                //         |-->used for seeing "dot + 3 spaces"

                if (piece != null) {
                    if (piece.getColor() == 0) {
                        sb.append(ColorInConsole.BrightWhite).append(paddedSymbol).append(ColorInConsole.Reset);
                    } else {
                        sb.append(ColorInConsole.BrightBlack).append(paddedSymbol).append(ColorInConsole.Reset);
                    }
                } else {
                    if (col == size - 1) { // for the last column before blue column(1...n)
                        paddedSymbol = String.format("%-" + 2 + "s", rawSymbol); 
                        sb.append(paddedSymbol);
                    } else {
                       sb.append(paddedSymbol);  
                    }
                    
                }
            }

            // Right row number
            sb.append(ColorInConsole.Blue);
            sb.append(String.format("%" + 3 + "d", row));
            sb.append(ColorInConsole.Reset);
            sb.append("\n");

            
        }

        // Bottom column enumerater (red)
        sb.append("\n");
        sb.append("   "); // padding for red collumn ligne
        for (int col = 0; col < size; col++) {
            sb.append(ColorInConsole.Red);
            sb.append(String.format("%" + cellWidth + "d", col));
            sb.append(ColorInConsole.Reset);
        }
        sb.append("\n");

        return sb.toString();
    }


    /**
     * A function which gives number of digits in an integer
     *
     * @param num the number which will be cheked
     * @return the grid array
     * @throws IllegalArgumentException if the number is negative
     */
    private int numberOfDigits(int num){ // ex: 1234 -> 4  | 199 -> 3 | 10 -> 2
        if (num<0) {
            throw new IllegalArgumentException("Number should be positive.");
            
        }
        if (num == 0) {
            return 0;
        }
        int digits = 1;
        while (num > 0) {
            num /= 10;
            digits++;
        }
        return digits;
    }

    /**
     * Updates the neighbor references of the given piece in all 8 directions.
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
            int newRow = row + dir.getX();
            int newCol = col + dir.getY();

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
