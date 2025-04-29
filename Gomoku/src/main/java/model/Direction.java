
package model;

/**
 * Enum representing the 8 possible directions (cardinal and diagonal).
 * each direction has an "x,y" to indicate which way they are headed.
 *
 * Example:
 * UP(-1, 0) means moving one row up, DOWN_RIGHT(1, 1) means moving
 * diagonally down and to the right
 *
 * @author Erkin Tunc Boya
 * @version 1.2
 * @since 2025-03-26
 */
public enum Direction {
    //TODO: Names could be changed to North, South , East, ... or their short forms like N, S, E, ...
    // But if we do that, we should also change those attributes in other classes

    /** Upward direction (-1 row, 0 column). */
    UP(-1, 0),

    /** Downward direction (+1 row, 0 column). */
    DOWN(1, 0),

    /** Left direction (0 row, -1 column). */
    LEFT(0, -1),

    /** Right direction (0 row, +1 column). */
    RIGHT(0, 1),

    /** Up-left diagonal (-1 row, -1 column). */
    UP_LEFT(-1, -1),

    /** Up-right diagonal (-1 row, +1 column). */
    UP_RIGHT(-1, 1),

    /** Down-left diagonal (+1 row, -1 column). */
    DOWN_LEFT(1, -1),

    /** Down-right diagonal (+1 row, +1 column). */
    DOWN_RIGHT(1, 1);

    /** Row for the direction. */
    private final int x;

    /** Column for the direction. */
    private final int y;

    /**
     * Creates a  new Direction for chosen way with x and y.
     *
     * @param x change in row
     * @param y change in column
     */
    Direction(int x, int y) {
        this.x = x; 
        this.y = y;
    }


    /**
     * Returns the opposite direction of this direction.
     * 
     * @return the opposite Direction
     * @throws IllegalStateException if no opposite direction is defined
     *                                 for this direction
     */
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP_LEFT:
                return DOWN_RIGHT;
            case UP_RIGHT:
                return DOWN_LEFT;
            case DOWN_LEFT:
                return UP_RIGHT;
            case DOWN_RIGHT:
                return UP_LEFT;
        }
        throw new IllegalStateException("No opposite defined for direction: " + this);
    }

    /** Gets the X of the direction 
     * @return x
    */
    public int getX() {
        return x;
    }

    /** Gets the Y of the direction 
     * @return y
    */
    public int getY() {
        return y;
    }

    

}
