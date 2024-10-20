package g60177.qwirkle.model;

import java.io.Serializable;

/**
 * Tile represent a tile of the game
 */

public record  Tile(Color color, Shape shape) implements Serializable {
}
