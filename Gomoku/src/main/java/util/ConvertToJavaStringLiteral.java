
package util;

/**
 * Utility class for formatting and converting ASCII art or multi-line strings
 * into Java-compatible source code string declarations.
 * 
 * <p>
 * Mainly used for make it easier to show ASCII art in the console.
 * Which is not used every part of the project, but it is used in the main menu.
 * </p>
 * 
 * @author Erkin Tunc Boya
 * @version 1.3
 */
public class ConvertToJavaStringLiteral {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * All methods are static and can be accessed directly via the class name.
     */
    ConvertToJavaStringLiteral() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a multi-line ASCII string into a Java-style string declaration.
     * Each line is quoted, ends with '\\n', and lines are concatenated using
     * '+'.
     *
     * @param input The original ASCII block string.
     * @param variableName The desired Java variable name for the declaration.
     * @return A formatted string that can be used as a Java source declaration.
     */
    public static String toJavaAsciiString(String input, String variableName) {
        String[] lines = input.split("\n");
        StringBuilder result = new StringBuilder();

        result.append("String ").append(variableName).append(" = ");

        for (int i = 0; i < lines.length; i++) {
            result.append("\"").append(lines[i].replace("\\", "\\\\")).append("\\n\"");
            if (i < lines.length - 1) {
                result.append(" +\n                ");
            } else {
                result.append(";");
            }
        }

        return result.toString();
    }

    /**
     * Adds a tab character at the beginning of each line of the given string.
     *
     * @param input The original multi-line string.
     * @return The formatted string with a tab at the beginning of each line.
     */
    public static String addTabToEachLine(String input) {
        String[] lines = input.split("\n");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            result.append("\t").append(lines[i]);
            if (i < lines.length - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

}
