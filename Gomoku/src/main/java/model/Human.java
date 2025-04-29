
package model;

import java.util.Scanner;

import util.ColorInConsole;

/**
 * Represents a human player in the Gomoku game.
 * A human player chooses their moves with a scanner input.
 *
 * @author Erkin Tunc Boya
 * @version 1.2
 * @since 2025-04-03
 */
public class Human extends Player {

    /** Scanner for reading user input. */
    private transient Scanner scanner = new Scanner(System.in); // Scanner is transient to avoid serialization issues

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a Human Player with a name and color (his pieces are given by default)
     * 
     * @param name the player's name
     * @param playerColor 0 for white, 1 for black
     */
    public Human(String name, int playerColor) {
        super(name, playerColor);
    }

    /**
     * Constructs a human player with custom piece count.
     *
     * @param name the player's name
     * @param playerColor 0 for white, 1 for black
     * @param pieceNum the number of pieces to start with
     */
    public Human(String name, int playerColor, int pieceNum) {
        super(name, playerColor, pieceNum);
    }

    /**
     * Asks the player to input row and column for their move. Validates input
     * to ensure it's a number.
     *
     * @param grid the current game grid used to determine valid input range
     * @return an int array {row, col} for the desired move
     */
    @Override
    public int[] choosePieceLocation(Grid grid) {
        System.out.println("\n===== Your Turn, " + name + " =====");

        int row = -1;
        int col = -1;
        int size = grid.getSize(); // dynamique size

        while (true) {
            try {
                System.out.print("Enter row number (0-" + (size - 1) + "): ");
                row = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter column number (0-" + (size - 1) + "): ");
                col = Integer.parseInt(scanner.nextLine());

                // Dynamique Grill Controle
                if (row < 0 || row >= size || col < 0 || col >= size) {
                    System.out.println("Invalid position. Please enter values between 0 and " + (size - 1) + ".");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println(ColorInConsole.Red + "Invalid input. Please enter valid numbers." + ColorInConsole.Reset);
            }
        }

        int[] chosenLocation = {row, col};

        return chosenLocation;
    }


    /**
     * Resets the scanner for the human player.
     * <p>
     * This method reinitializes the scanner to ensure proper input handling
     * after loading a saved game, preventing load problems while save loading the game.
     * </p>
     */
    public void resetScanner() {
        scanner = new Scanner(System.in); // Reset the scanner to avoid resource leak
    }

}
