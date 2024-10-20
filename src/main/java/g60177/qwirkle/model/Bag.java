package g60177.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class representing a bag of tiles used in a game.
 * The bag contains a list of tiles which can be randomly selected.
 */
public class Bag implements Serializable {

    private static Bag instanceBag = new Bag();
    private final List<Tile> tiles;

    /**
     * Creates a new Bag object with a set of 27 Tile objects.
     * Each Tile object has one of each possible combination of color and shape.
     */
    private Bag() {
        this.tiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                Tile tile = new Tile(color, shape);
                for (int i = 0; i < 3; i++) {
                    tiles.add(tile);
                }
            }
        }
        Collections.shuffle(this.tiles);
    }

    /**
     * Returns the singleton instance of the Bag class.
     *
     * @return the instance of the Bag class.
     */
    public static Bag getInstance() {
        return instanceBag;
    }

    /**
     * Sets the instance bag with a new bag.
     *
     * @param newBag the new bag to be set as the instance bag
     */
    public static void setInstanceBag(Bag newBag) {
        instanceBag = newBag;
    }

    /**
     * Returns a list of n random tiles from the bag.
     * If the bag is empty, null is returned.
     *
     * @param n the number of random tiles to select.
     * @return a list of n random tiles.
     */
    public Tile[] getRandomTiles(int n) {
        if (tiles.size() == 0) {
            return null;
        }
        if (n > tiles.size()) {
            n = tiles.size();
        }
        Tile[] randomTiles = new Tile[n];
        for (int i = 0; i < n; i++) {
            randomTiles[i] = tiles.get(tiles.size() - 1);
            tiles.remove(tiles.size() - 1);
        }
        return randomTiles;
    }

    /**
     * @return the size of the bag of tiles
     */
    public int size() {
        return tiles.size();
    }
}
