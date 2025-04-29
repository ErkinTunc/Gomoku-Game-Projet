
package util;

/**
 * Utility class that provides ANSI escape codes to print colored text and
 * backgrounds in the console. Useful for coloring the terminal
 *
 * Example usage:
 * <pre>{@code
 * System.out.println(ColorInConsole.Red + "Error message" + ColorInConsole.Reset);
 * }</pre>
 *
 * @author erkin
 */
public class ColorInConsole {

    /** Resets all text attributes (color, style) to default */
    public static final String Reset = "\u001b[0m";
    
    // Foreground colors
    /** Black text color */
    public static final String Black = "\u001b[30m";
    /** Red text color */
    public static final String Red = "\u001b[31m";
    /** Green text color */
    public static final String Green = "\u001b[32m";
    /** Yellow text color */
    public static final String Yellow = "\u001b[33m";
    /** Blue text color */
    public static final String Blue = "\u001b[34m";
    /** Magenta text color */
    public static final String Magenta = "\u001b[35m";
    /** Cyan text color */
    public static final String Cyan = "\u001b[36m";
    /** White text color */
    public static final String White = "\u001b[37m";

    // Bright foreground colors
    /** Bright black (gray) text color */
    public static final String BrightBlack = "\u001b[30;1m";
    /** Bright red text color */
    public static final String BrightRed = "\u001b[31;1m";
    /** Bright green text color */
    public static final String BrightGreen = "\u001b[32;1m";
    /** Bright yellow text color */
    public static final String BrightYellow = "\u001b[33;1m";
    /** Bright blue text color */
    public static final String BrightBlue = "\u001b[34;1m";
    /** Bright magenta text color */
    public static final String BrightMagenta = "\u001b[35;1m";
    /** Bright cyan text color */
    public static final String BrightCyan = "\u001b[36;1m";
    /** Bright white text color */
    public static final String BrightWhite = "\u001b[37;1m";

    // Background colors
    /** Black background color */
    public static final String BackgroundBlack = "\u001b[40m";
    /** Red background color */
    public static final String BackgroundRed = "\u001b[41m";
    /** Green background color */
    public static final String BackgroundGreen = "\u001b[42m";
    /** Yellow background color */
    public static final String BackgroundYellow = "\u001b[43m";
    /** Blue background color */
    public static final String BackgroundBlue = "\u001b[44m";
    /** Magenta background color */
    public static final String BackgroundMagenta = "\u001b[45m";
    /** Cyan background color */
    public static final String BackgroundCyan = "\u001b[46m";
    /** White background color */
    public static final String BackgroundWhite = "\u001b[47m";

    // Bright background colors
    /** Bright black (gray) background color */
    public static final String BackgroundBrightBlack = "\u001b[40;1m";
    /** Bright red background color */
    public static final String BackgroundBrightRed = "\u001b[41;1m";
    /** Bright green background color */
    public static final String BackgroundBrightGreen = "\u001b[42;1m";
    /** Bright yellow background color */
    public static final String BackgroundBrightYellow = "\u001b[43;1m";
    /** Bright blue background color */
    public static final String BackgroundBrightBlue = "\u001b[44;1m";
    /** Bright magenta background color */
    public static final String BackgroundBrightMagenta = "\u001b[45;1m";
    /** Bright cyan background color */
    public static final String BackgroundBrightCyan = "\u001b[46;1m";
    /** Bright white background color */
    public static final String BackgroundBrightWhite = "\u001b[47;1m";

    /**
     * Default constructor.
     */
    public ColorInConsole() { }

    /**
     * Clears the terminal and resets the cursor to the top-left.
     *
     * This sends a couple of special ANSI codes to the terminal:
     * one to clear the screen, and one to move the cursor back to the beginning.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
