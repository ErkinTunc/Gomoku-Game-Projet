
package app;

import java.util.Scanner;

import util.*;
import model.Grid;
import model.Piece;
import model.Player;
import save.SaveManager;

/**
 * <p>
 * Core class that manages the gameplay mechanics of the Gomoku game.
 * </p>
 * This class handles:
 * <ul>
 *   <li>Managing the players, the grid, and game state</li>
 *   <li>Starting new games and resuming loaded games</li>
 *   <li>Executing rounds of gameplay, including move validation and win checking</li>
 *   <li>Expanding the grid dynamically if enabled</li>
 *   <li>Supporting save and load operations via serialization</li>
 * </ul>
 * <p>
 * It also provides getters and setters to adjust game settings such as grid size,
 * win condition length, and initial piece counts.
 *</p>
 * @author Erkin Tunç Boya
 * @version 1.8
 * @since 2025-03-26
 */
public class GameEngine {

    // Main attributes
    /** The grid used in the game. */
    private Grid grid;

    /** The first player in the game. */
    private Player player1;

    /** The second player in the game. */
    private Player player2;

    // Config
    /** The number of pieces each player starts with. */
    private int playerPiece; 
    /** The number of pieces each player starts with. */
    private int winLength;
    /** The size of the grid (n x n). */
    private int gridSize; // gridSize represents "n".  n --> n*n
    
    /** Expandable grid flag */
    private boolean expendibleGrid = false; // Expendible grid 

    // Game state control
    /** The current player in the game. */
    private Player currentPlayer;
    /** Flag to indicate if the game is over. */
    private boolean gameOver = false;
    /** Scanner for user input. */
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for the GameEngine. 
     * @param gridSize size of the grid (must be odd)
     * @param playerPiece number of pieces each player starts with
     * @param winLength number of pieces needed to win
     * 
     * @throws IllegalArgumentException if gridSize is not odd, playerPiece is less than
     *                                   winLength, or winLength is less than 3.
     * @throws IllegalArgumentException if playerPiece is less than or equal to 0.
     * @throws IllegalArgumentException if winLength is less than or equal to 2.
     * @throws IllegalArgumentException if gridSize is less than winLength.
     */
    public GameEngine(int gridSize, int playerPiece, int winLength) {
        if (gridSize % 2 != 1) {
            throw new IllegalArgumentException("Grid size should be odd");
        }
        if (gridSize < winLength) {
            throw new IllegalArgumentException("Grid size must be equal to or larger than win length.");
        }
        if (playerPiece <= 0) {
            throw new IllegalArgumentException("Each players piece numbers should be more 0");
        }
        if (playerPiece < winLength) {
            throw new IllegalArgumentException("Each players piece numbers should be more than win Length");
        }
        if (winLength <= 2) {
            throw new IllegalArgumentException("Win Length should be more than 2");
        }
        this.gridSize = gridSize;
        this.playerPiece = playerPiece;
        this.winLength = winLength;
        this.grid = new Grid(gridSize);
    }

    /**
     * Default constructor for the Gomoku game. Initializes the game with default
     * values.
     */
    public GameEngine() {
        this(15, 60, 4); // default values
    }

    /**
     * Plays the very first round of the game by placing the initial piece at the center of the grid.
     * <p>
     * This method:
     * <ul>
     *   <li>Clears the console screen for a fresh start</li>
     *   <li>Calculates the center coordinates based on the current grid size</li>
     *   <li>Creates and places the first piece for Player 1 at the center</li>
     *   <li>Displays a message indicating the first move's placement</li>
     *   <li>Switches the turn to Player 2 after the first move</li>
     * </ul>
     * <p>
     * This ensures that Player 1 always starts in the center of the board.
     */
    public void playFirstRound() { // Always player1 starts first

        ColorInConsole.clearScreen();

        int gridSize = this.grid.getSize();
        int player1Color = player1.getPlayerColor();

        int centerLocation = (gridSize - 1) / 2;

        // Creating a Piece
        Piece piece = new Piece(player1Color, centerLocation, centerLocation);

        // Placing the first piece
        this.grid.placeTheFirstPiece(centerLocation, centerLocation, piece);

        // Playing the first piece
        System.out.println(ColorInConsole.BrightBlue + player1.getName() + " placed the first piece at (" + centerLocation + ", " + centerLocation + ")" +"automatically." +ColorInConsole.Reset);

        //Change the Current Player
        this.currentPlayer = player2;
    }

    /**
     * Executes a single round of gameplay for the current player.
     * <p>
     * This method handles:
     * <ul>
     *   <li>Displaying the current player's status (name, remaining pieces, win length)</li>
     *   <li>Presenting in-game options: play a move, save the game, or exit to the main menu</li>
     *   <li>Managing the player's chosen action (move, save, or exit)</li>
     *   <li>Validating the move and checking for win conditions or a draw</li>
     *   <li>Switching turns between players if the game continues</li>
     * </ul>
     * 
     *
     * @see save.SaveManager#saveGame(String, model.Grid, model.Player, model.Player)
     */
    public void playRound() {

        String currentInfoString = ColorInConsole.Yellow + currentPlayer.getName() + "'s turn:" + "\n"
                                    + "You have " + currentPlayer.getPieceNum() + " pieces left." + "\n"
                                    + "Win length: " + winLength + ColorInConsole.Reset ;

        currentInfoString = ConvertToJavaStringLiteral.addTabToEachLine(currentInfoString);
        System.out.println(currentInfoString);
    
        // Display mid-turn options
        String midTurmOptionsString = "1. Play move" + "\n"
                                    + "2. Save game" + "\n"
                                    + "3. Exit to main menu" + "\n"
                                    + ColorInConsole.BrightBlack + "Choose an option: " + ColorInConsole.Reset;

        midTurmOptionsString = ConvertToJavaStringLiteral.addTabToEachLine(midTurmOptionsString);
        System.out.print(midTurmOptionsString);                        
        
        String option = scanner.nextLine().trim();
    
        switch (option) {
            case "1":
                // Normal gameplay: player plays a move
                int[] coords = currentPlayer.choosePieceLocation(this.grid);
                int row = coords[0];
                int col = coords[1];
    
                try {
                    currentPlayer.play(grid, row, col);
                } catch (IllegalArgumentException e) {
                    System.out.println(ColorInConsole.Red + "Invalid move: " + e.getMessage() + ColorInConsole.Reset);
                    return; // Same player retries
                }

    
                // Check for win
                if (currentPlayer.hasWon(grid, row, col, winLength)) {
                    System.out.println(currentPlayer.getName() + " wins!");
                    gameOver = true;
                    Gomoku.pressToContinue(scanner);
                    return;
                }
    
                // Check for draw
                if (grid.isGridFull()) {
                    if (!expendibleGrid) {
                        System.out.println(ColorInConsole.Yellow + "Draw! The board is full." + ColorInConsole.Reset);
                        gameOver = true;
                        Gomoku.pressToContinue(scanner);
                        return;
                    } else {
                        System.out.println("Grid is full, will be expanded soon.");
                    }
                }
    
                // Switch turns
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
                break;
    
            case "2":
                // Save Game
                System.out.print("Enter filename to save (example: save.dat): ");
                String filename = scanner.nextLine().trim();
                SaveManager.saveGame(filename, this.grid, this.player1, this.player2);
                System.out.println("Game saved successfully.");
                break;
    
            case "3":
                // Exit to Main Menu
                System.out.println("Exiting to Main Menu...");
                gameOver = true;
                break;
    
            default:
                System.out.println(ColorInConsole.Red + "Invalid option. Please select 1, 2, or 3." + ColorInConsole.Reset);
                Gomoku.pressToContinue(scanner);
                break;
        }
    }

    /**
     * Runs the main gameplay loop after the first move.
     * <p>
     * This method manages the overall flow of the game:
     * <ul>
     *   <li>Handles player turns and checks if a player has run out of pieces</li>
     *   <li>Detects when the grid is full and dynamically expands it if expandable mode is enabled</li>
     *   <li>Ends the game when a player wins or when both players have no pieces left (draw)</li>
     * </ul>
     * <p>
     * The game continues in a loop until a win condition or draw is detected.
     */
    public void playGame() {
        playFirstRound();

        while (!gameOver) {
            ColorInConsole.clearScreen(); // Clear the console
            System.out.println(grid); // Display the current grid
            
            // Check if both players are out of pieces
            if (player1.getPieceNum() <= 0 && player2.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + "Both players are out of pieces. Game over! It's a draw!" + ColorInConsole.Reset);
                break;
            }
            
            // Skip player's turn if they're out of pieces
            if (currentPlayer == player1 && player1.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + currentPlayer.getName() + " has no pieces left. Skipping turn." + ColorInConsole.Reset);
                setCurrentPlayer(player2);
                
            }
            if (currentPlayer == player2 && player2.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + currentPlayer.getName() + " has no pieces left. Skipping turn." + ColorInConsole.Reset);
                setCurrentPlayer(player1);
                
            }
            // Play normal round
            playRound();

            expandGridIfNeeded(); // Check if we need to expand the grid
        }
    }

    /**
     * Resumes an already started game, typically after loading a saved state.
     *
     * <p>This method displays the current grid and re-enters the game loop, managing:
     * <ul>
     *   <li>Player turns (Human vs Human or Human vs AI)</li>
     *   <li>Piece exhaustion: skips players who have no remaining pieces</li>
     *   <li>Victory conditions and grid expansion if enabled</li>
     * </ul>
     *
     * <p>If the grid becomes full and expandable mode is active, the grid is expanded dynamically
     * (size becomes 2n-1 x 2n-1) and each player receives additional pieces.
     *
     * <p>The game continues until a player wins, the grid is completely filled without expansion, 
     * or both players run out of pieces (draw).
     *
     */
    public void resumeGame() {
        this.gameOver = false;
        System.out.println(grid); // Display the current grid

        while (!gameOver) {
            
            System.out.println(currentPlayer.getName() + "'s turn:"); // Display current player's name

            if (player1.getPieceNum() <= 0 && player2.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + "Both players are out of pieces. Game over! It's a draw!" + ColorInConsole.Reset);
                break;
            }
    
            if (currentPlayer == player1 && player1.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + currentPlayer.getName() + " has no pieces left. Skipping turn." + ColorInConsole.Reset);
                setCurrentPlayer(player2);
            }
            if (currentPlayer == player2 && player2.getPieceNum() <= 0) {
                System.out.println(ColorInConsole.Yellow + currentPlayer.getName() + " has no pieces left. Skipping turn." + ColorInConsole.Reset);
                setCurrentPlayer(player1);
            }
    
            playRound();
    
            expandGridIfNeeded(); // Check if we need to expand the grid
        }
    }

   /**
     * Starts a new game by resetting the grid and game state.
     */
    public void newGame() {
        this.grid = new Grid(gridSize);
        this.gameOver = false;
        this.currentPlayer = player1; // Player 1 always starts first
    }

    /**
     * Gets the game over status.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }


    /**
     * Expands the grid size dynamically if expansion is enabled and the current grid is full.
     * 
     * <p>
     * This method checks if the grid is full and whether expandable mode is active.
     * If both conditions are satisfied:
     * </p>
     * 
     *  <ul>
     *   <li>The grid size is doubled (following the formula 2n-1 to keep it odd).</li>
     *   <li>The existing grid data is transferred into a new, larger grid.</li>
     *   <li>Both players are granted additional pieces based on the starting piece count.</li>
     *   <li>A success message is displayed to indicate the grid expansion and new pieces awarded.</li>
     *  </ul>
     * 
     * <p>
     * If the grid is not full or expandable mode is disabled, this method does nothing.
     * </p>
     * 
     * @see model.Grid#expandGrid(int)
     */
    private void expandGridIfNeeded(){
        if (expendibleGrid && grid.isGridFull()) {
            int newSize = grid.getSize() * 2 - 1; // Make sure it stays odd
            grid = grid.expandGrid(newSize);
            System.out.println(ColorInConsole.Green +"Grid has been expanded to " + newSize + "x" + newSize + ColorInConsole.Reset);
            
            // Give players more pieces when the grid expands
            int additionalPieces = playerPiece;//startingPieces / 2;
            player1.setPieceNum( player1.getPieceNum() + additionalPieces);
            player2.setPieceNum( player2.getPieceNum() + additionalPieces);
            System.out.println(ColorInConsole.Green + "Each player received " + additionalPieces + " additional pieces." + ColorInConsole.Reset);
        }
    }


    // ============== SETTERS ===============

    /**
     * Sets the grid for the game.
     *
     * @param grid the grid to set
     * @throws IllegalArgumentException if the grid is null
     */
    public void setGrid(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null.");
        }
        this.grid = grid;
    }

    /**
     * Sets the first player for the game.
     *
     * @param player1 the first player to set
     * @throws IllegalArgumentException if the player is null
     */
    public void setPlayer1(Player player1) {
        if (player1 == null) {
            throw new IllegalArgumentException("Player1 cannot be null.");
        }
        this.player1 = player1;
    }

    /**
     * Sets the second player for the game.
     *
     * @param player2 the second player to set
     * @throws IllegalArgumentException if the player is null
     */
    public void setPlayer2(Player player2) {
        if (player2 == null) {
            throw new IllegalArgumentException("Player2 cannot be null.");
        }
        this.player2 = player2;
    }

    /**
     * Sets the number of pieces each player starts with.
     *
     * @param playerPiece the number of pieces to set
     * @throws IllegalArgumentException if the number of pieces is less than 0
     */
    public void setPlayerPiece(int playerPiece) {
        if (playerPiece < 0) {
            throw new IllegalArgumentException("Player pieces must be more than 0.");
        }
        this.playerPiece = playerPiece;
    }

    /**
     * Sets the length of the winning condition.
     *
     * @param winLength the length to set
     * @throws IllegalArgumentException if the win length is not more than 2
     */
    public void setWinLength(int winLength) {
        if (winLength <= 2) {
            throw new IllegalArgumentException("Win length must be more than 2.");
        }
        this.winLength = winLength;
    }

    /**
     * Sets the size of the grid.
     *
     * @param gridSize the size to set
     * @throws IllegalArgumentException if the grid size is not odd
     */
    public void setGridSize(int gridSize) {
        if (gridSize % 2 != 1) {
            throw new IllegalArgumentException("Grid size should be odd.");
        }
        this.gridSize = gridSize;
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer the current player to set
     * @throws IllegalArgumentException if the current player is null
     */
    public void setCurrentPlayer(Player currentPlayer) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        this.currentPlayer = currentPlayer;
    }

    /**
     * Sets the game over status.
     *
     * @param gameOver the game over status to set
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Sets whether the grid can be expanded during play
     * @param expandable true if the grid should be expandable, false otherwise
     */
    public void setExpandableGrid(boolean expandable) {
        this.expendibleGrid = expandable;
    }

    // ============= GETTERS ==================

    /**
     * Gets the grid of the game.
     *
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Gets the first player of the game.
     *
     * @return the first player
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets the second player of the game.
     *
     * @return the second player
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Gets the number of pieces each player starts with.
     *
     * @return the number of pieces
     */
    public int getPlayerPiece() {
        return playerPiece;
    }

    /**
     * Gets the length of the winning condition.
     *
     * @return the win length
     */
    public int getWinLength() {
        return winLength;
    }

    /**
     * Gets the size of the grid.
     *
     * @return the grid size
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Gets the current player of the game.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks if the grid is expandable
     * @return true if the grid can be expanded, false otherwise
     */
    public boolean isExpandableGrid() {
        return expendibleGrid;
    }

}
