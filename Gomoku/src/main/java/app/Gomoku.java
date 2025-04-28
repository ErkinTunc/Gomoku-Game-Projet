/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package app;
import java.util.Scanner;

import ai.AIPlayer;
import model.Grid;
import model.Human;
import model.Player;
import save.SaveManager;
import util.*; 


// For Compiling in Windows Command Prompt
/* 
javac -d target/classes src/main/java/model/*.java src/main/java/util/*.java src/main/java/ai/*.java src/main/java/app/*.java
*/

// For Compiling in Linux/Mac Terminal
/*
javac -d target/classes \ 
src/main/java/model/*.java \
src/main/java/util/*.java \
src/main/java/ai/*.java \
src/main/java/app/*.java */

// For running the program
/*
java -cp target/classes app.Gomoku
*/

/**
 * <p>
 * Launcher class that controls the menu system and overall navigation of the Gomoku game.
 * </p>
 * This class is responsible for:
 * <ul>
 *   <li>Displaying the ASCII art logo and colorful main menu</li>
 *   <li>Handling user choices to start a new game, load a game, access settings, or exit</li>
 *   <li>Managing game settings through a dedicated settings menu</li>
 *   <li>Initializing a {@link app.GameEngine} instance to control gameplay</li>
 * </ul>
 * <p>
 * It acts as the main entry point to the application and manages high-level user interaction.
 * </p>
 * 
 * @author Erkin Tunç Boya
 * @version 1.7
 * @since 2025-03-26
 */
public class Gomoku {

    /**
     * Constructs a new Gomoku launcher instance.
     * <p>
     * Initializes the main menu system but does not start gameplay immediately.
     * The user must call {@link #showMainMenu()} to begin interacting with the application.
     */
    public Gomoku() {    
    }

    /**
     * Returns the ASCII art logo representing the Gomoku game title.
     * <p>
     * This logo is used primarily in the main menu and during other major screens 
     * to enhance the visual appearance of the terminal-based interface.
     * </p>
     *
     * @return a formatted String containing the Gomoku ASCII art logo
    */
    private String asciiArtLogo() {
        String asciiArtLogo
                = "   ▄██████▄   ▄██████▄    ▄▄▄▄███▄▄▄▄    ▄██████▄     ▄█   ▄█▄ ███    █▄  \n"
                + "  ███    ███ ███    ███ ▄██▀▀▀███▀▀▀██▄ ███    ███   ███ ▄███▀ ███    ███ \n"
                + "  ███    █▀  ███    ███ ███   ███   ███ ███    ███   ███▐██▀   ███    ███ \n"
                + " ▄███        ███    ███ ███   ███   ███ ███    ███  ▄█████▀    ███    ███ \n"
                + "▀▀███ ████▄  ███    ███ ███   ███   ███ ███    ███ ▀▀█████▄    ███    ███ \n"
                + "  ███    ███ ███    ███ ███   ███   ███ ███    ███   ███▐██▄   ███    ███ \n"
                + "  ███    ███ ███    ███ ███   ███   ███ ███    ███   ███ ▀███▄ ███    ███ \n"
                + "  ████████▀   ▀██████▀   ▀█   ███   █▀   ▀██████▀    ███   ▀█▀ ████████▀  \n"
                + "                                                     ▀                    ";

                return asciiArtLogo;
    }

    /**
     * <p>
     * Displays the main menu and handles the user's navigation through the game options.
     * </p>
     * The available options include:
     * <ul>
     *   <li>Starting a new game between two human players</li>
     *   <li>Starting a game against the AI</li>
     *   <li>Loading a previously saved game</li>
     *   <li>Opening the settings menu to adjust game parameters</li>
     *   <li>Exiting the game</li>
     * </ul>
     * 
     * <p>
     * This method initializes a new {@link app.GameEngine} instance and manages the 
     * appropriate setup (such as player creation and grid initialization) based on user choices.
     * The menu is displayed inside a loop, allowing the user to return to the menu after completing
     * an action unless the program is exited.
     * </p>
     *
     * @see app.GameEngine#newGame()
     * @see app.GameEngine#playGame()
     * @see app.GameEngine#resumeGame()
     * @see app.Gomoku#settingsMenu(GameEngine)
     * @see save.SaveManager#listSavedGames()
     * @see save.SaveManager#loadGame(String)
     */
    public void showMainMenu() {

        Scanner scannerMain = new Scanner(System.in);

        GameEngine game = new GameEngine();

        // Convert the ASCII art logo to a Java string literal
        String asciiArtLogo = asciiArtLogo();
        
        // asciiArtLogo = convertToJavaStringLiteral.addTabToEachLine(asciiArtLogo);
        String mainMenu = "1. Start New Game (2 Players)\n"
                + "2. Play Against Computer\n"
                + "3. Load Game\n"
                + "4. Settings\n"
                + "5. Exit\n"
                + ColorInConsole.BrightBlack + "Choose an option: " + ColorInConsole.Reset;

        mainMenu = convertToJavaStringLiteral.addTabToEachLine(mainMenu);
        // asciiArtLogo asciiArtLogo = convertToJavaStringLiteral.addTabToEachLine(asciiArtLogo);

        while (true) {
            ColorInConsole.clearScreen(); // Clear the console

            System.out.println(asciiArtLogo);
            System.out.print(mainMenu);

            int choice = scannerMain.nextInt();
            scannerMain.nextLine(); // consume newline

            System.out.println("");

            switch (choice) {
                case 1:
                    game.newGame(); // multiplayer

                    ColorInConsole.clearScreen(); // Clear the console

                    System.out.println("Player 1 starts with black and plays the first move.");

                    // Get players names
                    System.out.print("Name Of Player1 : ");
                    String name1 = scannerMain.nextLine();

                    System.out.print("Name Of Player2 : ");
                    String name2 = scannerMain.nextLine();

                    // Create Players
                    game.setPlayer1(new Human(name1, 1, game.getPlayerPiece())); // 1 = Black
                    game.setPlayer2(new Human(name2, 0, game.getPlayerPiece())); // 0 = White

                    game.setCurrentPlayer(game.getPlayer1());

                    ColorInConsole.clearScreen(); // Clear the console

                    // Play the game
                    game.playGame();
                    break;
                case 2:
                    game.newGame(); // vs AI

                    ColorInConsole.clearScreen(); // Clear the console

                    // Get players names
                    System.out.print("Name Of Player1 : ");
                    String userName = scannerMain.nextLine();

                    // Create Players 
                    game.setPlayer1(new Human(userName, 1, game.getPlayerPiece())); // 1 = Black
                    game.setPlayer2(new AIPlayer("AI-Sam", 0, game.getWinLength(), game.getPlayerPiece())); // 0 = White

                    game.setCurrentPlayer(game.getPlayer1());

                    game.playGame();
                    break;
                case 3:
                    ColorInConsole.clearScreen(); // Clear the console

                    // Load Game
                    SaveManager.listSavedGames();

                    System.out.print("Enter the filename to load (example: save.dat)\nOr press <q> to return to main menu: ");
                    String filename = scannerMain.nextLine().trim();

                    if (filename.equalsIgnoreCase("q")) {
                        System.out.println("Returning to main menu...");
                        break;
                    }
                    
                    Object[] loadedData = SaveManager.loadGame(filename);


                    if (loadedData != null) {
                        game.setGrid((Grid) loadedData[0]);
                        game.setPlayer1((Player) loadedData[1]);
                        game.setPlayer2((Player) loadedData[2]);
                        game.setCurrentPlayer(game.getPlayer1());

                        // If player1 is a Human, reinitialize its Scanner
                        if (game.getPlayer1() instanceof Human) {
                            ((Human) game.getPlayer1()).resetScanner();
                        }

                        // If player2 is a Human, reinitialize its Scanner
                        if (game.getPlayer2() instanceof Human) {
                            ((Human) game.getPlayer2()).resetScanner();
                        }

                        // Display loaded game attributes
                        System.out.println("Grid Size: " + game.getGridSize());
                        System.out.println("Win Length: " + game.getWinLength());
                        System.out.println("Player 1: " + game.getPlayer1().getName());
                        System.out.println("Player 2: " + game.getPlayer2().getName());

                        game.resumeGame(); // Resume the game
                    } else {
                        System.out.println(ColorInConsole.Red + "Failed to load the game." + ColorInConsole.Reset);
                        pressToContinue(scannerMain);// press anything to continue
                    }
                    break;
                case 4:
                    ColorInConsole.clearScreen(); // Clear the console
                    settingsMenu(game);
                    break;
                case 5:
                    ColorInConsole.clearScreen(); // Clear the console
                    System.out.println(asciiArtLogo);
                    System.out.println(ColorInConsole.Yellow + "Thanks for playing!" + ColorInConsole.Reset);
                    System.out.println(ColorInConsole.Yellow + "Exiting the game..." + ColorInConsole.Reset);
                    scannerMain.close(); // Close the scanner
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println(ColorInConsole.Red + "Invalid option. Try again." + ColorInConsole.Reset);
                    pressToContinue(scannerMain);// press anything to continue
            }
        }
    }

    /**
     * <p>
     * Displays the settings menu and allows the user to modify game configuration parameters.
     * </p>
     * The available settings include:
     * <ul>
     *   <li>Grid Size (must be an odd number greater than or equal to 3)</li>
     *   <li>Win Length (minimum 3)</li>
     *   <li>Initial Piece Count per player (must be positive)</li>
     *   <li>Enable or disable expandable grids</li>
     * </ul>
     * <p>
     * The menu displays the current game settings at the top and updates values based on user input.
     * Input validation is performed for each option to ensure reasonable values.
     * After each change, the user is prompted to press Enter before returning to the settings menu.
     * Exiting this menu returns the user to the main menu.
     * </p>
     *
     * @param game the {@link app.GameEngine} instance whose settings will be modified
     * @see app.GameEngine#setGridSize(int)
     * @see app.GameEngine#setWinLength(int)
     * @see app.GameEngine#setPlayerPiece(int)
     * @see app.GameEngine#setExpandableGrid(boolean)
     */
    public void settingsMenu(GameEngine game) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String curentSettings = "\n======== CURRENT SETTINGS =========\n" +
                                        ColorInConsole.Yellow + "Grid Size: " + game.getGridSize() + "\n"
                                        + "Win Length: " + game.getWinLength() + "\n"
                                        + "Player Piece Count: " + game.getPlayerPiece() + "\n"
                                        + "Expandable Grid: " + (game.isExpandableGrid() ? "Enabled" : "Disabled") + "\n" + ColorInConsole.Reset;

            String settingsMenu = "\n============ SETTINGS =============\n"
                                + "1. Set Grid Size (odd number)\n"
                                + "2. Set Win Length\n"
                                + "3. Set Player Piece Count\n"
                                + "4. expendible Grid \n"
                                + "5. Back to Main Menu\n"
                                + "=====================================\n"
                                + ColorInConsole.BrightBlack + "Choose an option: " + ColorInConsole.Reset;

            curentSettings = convertToJavaStringLiteral.addTabToEachLine(curentSettings);
            settingsMenu = convertToJavaStringLiteral.addTabToEachLine(settingsMenu);

            ColorInConsole.clearScreen(); // Clear the console

            System.out.println(curentSettings);
            System.out.print(settingsMenu);

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter new grid size (odd number >= 3): ");
                    int newSize = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (newSize >= 3 && newSize % 2 == 1) {
                        game.setGridSize(newSize);
                        System.out.println(ColorInConsole.Green + "Grid size set to " + newSize + ColorInConsole.Reset);

                        if (game.getWinLength() > newSize) {
                            System.out.println(ColorInConsole.Red + "Warning: Win length is larger than grid size." + ColorInConsole.Reset);
                            System.out.print("Do you want to change the win length to " + newSize + "? (yes/no): ");
                            String response = scanner.nextLine();
                            if (response.equalsIgnoreCase("yes")) {
                                game.setWinLength(newSize);
                                System.out.println(ColorInConsole.Green + "Win length changed to " + newSize + ColorInConsole.Reset);
                            } else {
                                System.out.println(ColorInConsole.Green + "Keeping the current win length of " + game.getWinLength() + ColorInConsole.Reset);
                            }
                        }
                    } else {
                        System.out.println(ColorInConsole.Red + "Invalid grid size. Must be an odd number >= 3." + ColorInConsole.Reset);
                    }
                    
                    pressToContinue(scanner); // press anything to continue
                    break;

                case 2:
                    System.out.print("Enter new win length (>= 3): ");
                    int newWinLength = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                
                    if (newWinLength >= 3) {
                        game.setWinLength(newWinLength);
                        System.out.println(ColorInConsole.Green + "Win length set to " + newWinLength + ColorInConsole.Reset);
                
                        if (newWinLength > game.getGridSize()) {
                            System.out.println(ColorInConsole.Red + "Warning: Win length is larger than grid size." + ColorInConsole.Reset);
                            System.out.print("Do you want to change the grid size to " + newWinLength + "? (yes/no): ");
                            String response = scanner.nextLine();
                            if (response.equalsIgnoreCase("yes")) {
                                game.setGridSize(newWinLength);
                                System.out.println(ColorInConsole.Green + "Grid size changed to " + newWinLength + ColorInConsole.Reset);
                            } else {
                                System.out.println("Keeping the current grid size of " + game.getGridSize());
                            }
                        }
                    } else {
                        System.out.println(ColorInConsole.Red + "Invalid win length." + ColorInConsole.Reset);
                    }
                
                    pressToContinue(scanner); // press anything to continue
                    break;

                case 3:
                    System.out.print("Enter number of pieces each player starts with (> 0): ");
                    int newPieceCount = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (newPieceCount > 0) {
                        game.setPlayerPiece(newPieceCount);
                        System.out.println(ColorInConsole.Green +"Each player will now start with " + newPieceCount + " pieces." + ColorInConsole.Reset);
                    } else {
                        System.out.println(ColorInConsole.Red + "Invalid piece count." + ColorInConsole.Reset);
                    }
                    pressToContinue(scanner);// press anything to continue
                    break;

                case 4:
                    System.out.print("Do you want to have expandable grids: yes/no? ");
                    String expandable = scanner.nextLine();
                    if (expandable.equalsIgnoreCase("yes")) {
                        game.setExpandableGrid(true);
                        System.out.println(ColorInConsole.Green + "Expandable grids are enabled." + ColorInConsole.Reset);

                    } else if (expandable.equalsIgnoreCase("no")) {
                        game.setExpandableGrid(false);
                        System.out.println(ColorInConsole.Green + "Expandable grids are disabled." + ColorInConsole.Reset);
                        
                    } else {
                        System.out.println(ColorInConsole.Red + "Invalid input. Please enter 'yes' or 'no'." + ColorInConsole.Reset);
                    }
                    pressToContinue(scanner);// press anything to continue
                    break;
                case 5:
                    System.out.println("\n \t Returning to main menu... \n \n");
                    return;

                default:
                    System.out.println(ColorInConsole.Red + "Invalid option. Try again." + ColorInConsole.Reset);
                    pressToContinue(scanner);// press anything to continue
            }
        }
    }


    /**
     * Displays a "Press anything to continue..." message
     * and waits for the user to press Enter.
     *
     * @param scanner the Scanner object to read input
     */
    public static void pressToContinue(Scanner scanner) {
        System.out.println(ColorInConsole.Yellow + "\n\tPress anything to continue..." + ColorInConsole.Reset);
        scanner.nextLine();
    }

    /**
     * Entry point of the Gomoku application.
    * <p>
    * This method creates an instance of {@link app.Gomoku} and launches the main menu,
    * allowing the user to start playing the game, load a saved game, or access settings.
    * </p>
    * @param args command-line arguments (not used)
    *
    * @see app.Gomoku#showMainMenu()
    */
    public static void main(String[] args) {
        Gomoku launcher = new Gomoku();
        launcher.showMainMenu();
    }
}
