package g60177.qwirkle.model;

import java.io.Serializable;

/**
 * The `GridView` class represents a view of a `Grid` in a Qwirkle game. It allows retrieving tiles from specific positions in the grid, and checking if the grid is empty.
 */
public class GridView implements Serializable {
    private final Grid grid;

    /**
     * Constructs a new  GridView with the specified  Grid.
     *
     * @param grid the grid to create the view from
     */
    public GridView(Grid grid) {
        this.grid = grid;
    }

    /**
     * Returns the tile at the specified position in the grid.
     *
     * @param row the row of the position to retrieve
     * @param col the column of the position to retrieve
     * @return the tile at the specified position
     */
    public Tile get(int row, int col) {
        return this.grid.get(row, col);
    }

    /**
     * Returns whether the grid is empty or not.
     *
     * @return true if the grid is empty, false otherwise
     */
    public boolean isEmpty() {
        return grid.isEmpty();
    }

}
