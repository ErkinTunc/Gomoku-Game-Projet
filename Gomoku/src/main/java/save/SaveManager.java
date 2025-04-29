
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
 * It saves int o "\data" folder or brings saves from there
 * creating it if necessary.
 * </p>
 * 
 * <p>
 * While I was coding this class, I was inspired by a video on youtube. Which lead me to construct this class in this way.
 * I will provide the link to the video here:
 * </p>
 * @see <a href="https://www.youtube.com/watch?v=xudKOLX_DAk&t=71s">Programming a Java Text Adventure: Saving Games</a>
 * 
 * @author Erkin Tunç Boya
 * @version 1.3
 * @since 2025-04-26
 */
public class SaveManager {

    /** Basic constructer */
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
        try{
            FileOutputStream fos = new FileOutputStream("data/" + filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            // Serialize the objects
            out.writeObject(grid);
            out.writeObject(player1);
            out.writeObject(player2);

            out.flush(); // write out any buffered bytes
            out.close();

            System.out.println(ColorInConsole.Green + "Game saved successfully." + ColorInConsole.Reset);
        } catch (IOException e) {
            System.out.println(ColorInConsole.Red + "Serialiation Error! Can't save data. \n " 
                            + e.getClass() + ": " + e.getMessage() + ColorInConsole.Reset);
        }
    }

    /**
     * Loads a previously saved game state from the {@code /data} directory.
     * <p>
     * This method deserializes the grid and players from the data folder
     *
     * @param filename the name of the file to load (e.g., "save1.dat")
     * @return an array containing the loaded grid, player1, and player2; or {@code null} if loading fails
     */
    public static Object[] loadGame(String filename) {

        try{
            FileInputStream fis = new FileInputStream("data/" + filename);  
            ObjectInputStream in = new ObjectInputStream(fis);

            // Deserialize the objects  
            Grid grid = (Grid) in.readObject();
            Player player1 = (Player) in.readObject(); //TODO: if there will be more players it should be modifird |idea : use a for loop for a list of players.
            Player player2 = (Player) in.readObject();

            System.out.println(ColorInConsole.Green + "\n---- Game loaded ----\n" + ColorInConsole.Reset);
            
            return new Object[]{grid, player1, player2};

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ColorInConsole.Red + "Serialiation Error! Can't save data. \n " 
                            + e.getClass() + ": " + e.getMessage() + ColorInConsole.Reset);
            return null;
        }
    }

    /**
     * Lists all saved game files available in the {@code /data} directory.
     * <p>
     * If no saved games are found, an appropriate message is displayed.
     */
    public static void listSavedGames() {
        File dir = new File("data"); // Where we strore our data
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println(ColorInConsole.Red + "No saved games found. (or data folder missing)" + ColorInConsole.Reset);
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