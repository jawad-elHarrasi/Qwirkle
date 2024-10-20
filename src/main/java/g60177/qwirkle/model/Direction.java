package g60177.qwirkle.model;


/**
 * This enum represents the four possible directions on a 2D grid: right, left, up,
 * and down.
 */
public enum Direction {
    RIGHT(0, +1), LEFT(0, -1), UP(-1, 0), DOWN(+1, 0);

    private final int deltaRow;
    private final int deltaCol;

    /**
     * Constructs a Direction enum with the given row and column deltas.     *
     *
     * @param row the row delta
     * @param col the column delta
     */
    Direction(int row, int col) {
        this.deltaRow = row;
        this.deltaCol = col;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Returns the opposite direction of this direction
     *
     * @return the opposite direction
     */
    Direction opposite() {
        return switch (this) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case RIGHT -> Direction.LEFT;
            case LEFT -> Direction.RIGHT;
        };

    }
}
