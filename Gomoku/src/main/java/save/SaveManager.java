/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package save;

import java.io.*;
import model.Grid;
import model.Player;
import util.ColorInConsole;

/**
 * Handles the saving, loading, and listing of Gomoku game states.
 * <p>
 * This class provides static methods to serialize and deserialize game data, 
 * including the grid and players, to and from the file system. 
 * It ensures that saved games are stored within a dedicated {@code /data} directory,
 * creating it if necessary.
 * <p>
 * Main functionalities:
 * <ul>
 *   <li>Saving the current game state to a {@code .dat} file</li>
 *   <li>Loading a saved game from a {@code .dat} file</li>
 *   <li>Listing available saved games inside the {@code /data} directory</li>
 * </ul>
 * 
 * @author Erkin Tunç Boya
 * @version 1.2
 * @since 2025-04-26
 */
public class SaveManager {

    /**
     * <p>
     * Private constructor to prevent instantiation of the {@code SaveManager} utility class.
     * </p>
     * <p>
     * This class only contains static methods and should not be instantiated.
     * </p>
     */
    public SaveManager() {}

    /**
     * Saves the current game state to a file inside the {@code /data} directory.
     * <p>
     * The method serializes the grid and both players into a {@code .dat} file.
     * If the {@code /data} directory does not exist, it is created automatically.
     *
     * @param filename the name of the save file (e.g., "save1.dat")
     * @param grid the current game grid to save
     * @param player1 the first player
     * @param player2 the second player
     */
    public static void saveGame(String filename, Grid grid, Player player1, Player player2) {
        // Ensure the 'data' folder exists
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Save file inside 'data/' folder
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/" + filename))) {
            out.writeObject(grid);
            out.writeObject(player1);
            out.writeObject(player2);
            System.out.println(ColorInConsole.Green + "Game saved successfully." + ColorInConsole.Reset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a previously saved game state from the {@code /data} directory.
     * <p>
     * This method deserializes the grid and players from the specified file.
     * If an error occurs (file not found, incompatible classes, etc.), it returns {@code null}.
     *
     * @param filename the name of the file to load (e.g., "save1.dat")
     * @return an array containing the loaded grid, player1, and player2; or {@code null} if loading fails
     */
    public static Object[] loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/" + filename))) {
            Grid grid = (Grid) in.readObject();
            Player player1 = (Player) in.readObject();
            Player player2 = (Player) in.readObject();
            System.out.println(ColorInConsole.Green + "Game loaded successfully." + ColorInConsole.Reset);
            return new Object[]{grid, player1, player2};
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lists all saved game files available in the {@code /data} directory.
     * <p>
     * If no saved games are found, an appropriate message is displayed.
     */
    public static void listSavedGames() {
        File dir = new File("data");
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println(ColorInConsole.Red + "No saved games found. (data folder missing)" + ColorInConsole.Reset);
            return;
        }

        String[] saves = dir.list((d, name) -> name.endsWith(".dat"));

        if (saves == null || saves.length == 0) {
            System.out.println(ColorInConsole.Red + "No saved games found in /data/." + ColorInConsole.Reset);
        } else {
            System.out.println("\nAvailable saved games:");
            for (String save : saves) {
                System.out.println("- " + ColorInConsole.BrightBlue + save + ColorInConsole.Reset);
            }
            System.out.println();
        }
    }


}