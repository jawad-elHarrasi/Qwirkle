package g60177.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Player class represents a player in the Qwirkle game.
 * It contains the player's name and the tiles they currently have in their hand.
 */
public class Player implements Serializable {
    private final String name;
    private int score;
    private final List<Tile> tiles;

    /**
     * Constructor for a player with a given name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.tiles = new ArrayList<>();
        refill();
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable list of tiles in the player's hand.
     *
     * @return the tiles in the player's hand
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(this.tiles);
    }

    /**
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Refills the player's hand with random tiles from the bag.
     */
    public void refill() {
        Bag bag = Bag.getInstance();
        if (bag.size() != 0) {
            int nbTiles = 6 - this.tiles.size();
            Collections.addAll(this.tiles, bag.getRandomTiles(nbTiles));
        }
    }


    /**
     * Increases the score of the player by the specified value.
     *
     * @param value the value to add to the player's score
     */
    public void addScore(int value) {
        this.score += value;
    }


    /**
     * Removes the specified tiles from the player's hand.
     *
     * @param ts the tiles to remove
     * @throws IllegalArgumentException if the number of tiles to remove is greater than the number of tiles in the player's hand
     * @throws QwirkleException         if a specified tile is not in the player's hand
     */
    public void remove(Tile... ts) {
        if (ts.length > tiles.size()) {
            throw new IllegalArgumentException("You try to remove more tiles than there are in the list 'tiles'");
        }
        for (Tile tile : ts) {
            if (!tiles.contains(tile)) {
                throw new QwirkleException("this tile " + tile + " is not in the list 'tiles' ");
            }
            this.tiles.remove(tile);
        }

    }
}
