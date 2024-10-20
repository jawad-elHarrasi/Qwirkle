package g60177.qwirkle.model;

/**
 * Represents a tile placed at a specific position on the game board.
 */
public record TileAtPosition(int row, int col, Tile tile) {
}
