/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Scanner;

/**
 * Represents a human player in the Gomoku game.
 * <p>
 * A human player chooses their moves via terminal input.
 * </p>
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
     * Constructs a human player with a given name and color. Assigns 60 pieces
     * by default.
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

                // Dinamik grid sınırı kontrolü
                if (row < 0 || row >= size || col < 0 || col >= size) {
                    System.out.println("Invalid position. Please enter values between 0 and " + (size - 1) + ".");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid numbers.");
            }
        }

        return new int[]{row, col};
    }


    /**
     * Resets the scanner for the human player.
     * <p>
     * This method reinitializes the scanner to ensure proper input handling
     * after loading a saved game, preventing potential resource leaks or
     * stale input references.
     */
    public void resetScanner() {
        scanner = new Scanner(System.in); // Reset the scanner to avoid resource leak
    }

}
