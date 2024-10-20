package g60177.qwirkle.model;

import java.io.*;
import java.util.List;

/**
 * The Game class represents a game of Qwirkle. It manages the players, the grid, and the turns of the game.
 */
public class Game implements Serializable {
    private final Bag bag;
    private final GridView gridView;
    private final Grid grid;
    private final Player[] players;
    private int currentPlayer;

    /**
     * Constructs a new Game object with the given list of player names.
     *
     * @param playersName a list of player names
     */
    public Game(List<String> playersName) {

        this.players = new Player[playersName.size()];
        for (int i = 0; i < playersName.size(); i++) {
            this.players[i] = new Player(playersName.get(i));
        }
        this.grid = new Grid();
        this.gridView = new GridView(grid);
        this.currentPlayer = 0;
        this.bag = Bag.getInstance();
    }

    /**
     * Retrieves a Game object from a file.
     *
     * @param fileName the name of the file to retrieve the Game object from
     * @return the Game object read from the file
     * @throws QwirkleException if an error occurs while retrieving the Game object
     */
    public static Game getFromFile(String fileName) throws QwirkleException {
        String folderName = "sauvegarde";
        String filePath = folderName + "/" + fileName;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Game game = (Game) inputStream.readObject();
            Bag.setInstanceBag(game.bag);
            return game;

        } catch (Exception e) {
            throw new QwirkleException("error : can't get this backup " + e.getMessage());
        }

    }

    /**
     * Gets the hand of the current player.
     *
     * @return a list of Tile objects representing the hand of the current player
     */
    public List<Tile> getCurrentPlayersHand() {
        return this.players[this.currentPlayer].getHand();
    }

    /**
     * Gets the name of the current player.
     *
     * @return a String representing the name of the current player
     */
    public String getCurrentPlayerName() {
        return this.players[this.currentPlayer].getName();
    }

    /**
     * Makes the first move of the game.
     *
     * @param d  the direction of the first move
     * @param is the indices of the tiles to be played
     */
    public void first(Direction d, int... is) {

        Tile[] playedTiles = new Tile[is.length];
        for (int i = 0; i < is.length; i++) {
            playedTiles[i] = getTileFromHand(is[i]);
        }
        players[currentPlayer].addScore(grid.firstAdd(d, playedTiles));

        for (Tile playedTile : playedTiles) {
            players[currentPlayer].remove(playedTile);
        }
        players[currentPlayer].refill();

        pass();


    }

    /**
     * Plays a single tile on the grid.
     *
     * @param row   the row of the cell where the tile should be placed
     * @param col   the column of the cell where the tile should be placed
     * @param index the index of the tile to be played in the current player's hand
     */
    public void play(int row, int col, int index) {
        Tile playedTile = players[currentPlayer].getHand().get(index);
        players[currentPlayer].addScore(grid.add(row, col, playedTile));


        players[currentPlayer].remove(playedTile);

        players[currentPlayer].refill();

        pass();
    }

    /**
     * Plays multiple tiles on the grid in a straight line.
     *
     * @param row     the row of the cell where the first tile should be placed
     * @param col     the column of the cell where the first tile should be placed
     * @param d       the direction of the line
     * @param indexes the indices of the tiles to be played in the current player's hand
     */
    public void play(int row, int col, Direction d, int... indexes) {
        Tile[] playedTiles = new Tile[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            playedTiles[i] = getTileFromHand(indexes[i]);
        }

        players[currentPlayer].addScore(grid.add(row, col, d, playedTiles));
        for (int i = 0; i < indexes.length; i++) {
            players[currentPlayer].remove(playedTiles[i]);
        }
        players[currentPlayer].refill();

        pass();
    }

    /**
     * Plays multiple tiles on the grid at different positions.
     *
     * @param is an array of integers where every three integers represents a tile and its position in the grid
     */
    public void play(int... is) {
        if (is.length % 3 != 0) {
            throw new QwirkleException("number of param incorrect");
        }

        TileAtPosition[] playedTile = new TileAtPosition[is.length / 3];
        List<Tile> hand = players[currentPlayer].getHand();
        int cpt = 0;
        for (int i = 0; i + 2 < is.length; i += 3) {
            playedTile[cpt] = new TileAtPosition(is[i], is[i + 1], hand.get(is[i + 2]));
            cpt++;
        }
        players[currentPlayer].addScore(grid.add(playedTile));
        for (int i = 0; i < is.length; i += 3) {
            Tile tile = hand.get(is[i + 2]);
            players[currentPlayer].remove(tile);
        }

        players[currentPlayer].refill();

        pass();
    }

    /**
     * Gets the GridView object representing the grid.
     *
     * @return a GridView object representing the grid
     */
    public GridView getGrid() {
        return gridView;
    }

    /**
     * Returns the score of the current player.
     *
     * @return the score of the current player
     */
    public int getPlayerScore() {
        return players[currentPlayer].getScore();
    }

    /**
     * Returns the array of Player objects representing the players in the game.
     *
     * @return the array of Player objects
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Passes the turn to the next player.
     */
    public void pass() {
        if (currentPlayer == players.length - 1) {
            currentPlayer = 0;
        } else {
            this.currentPlayer++;
        }

    }

    /**
     * Checks if the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */

    public boolean isOver() {
        if (bag.size() == 0) {
            int firstRow = -1;
            int lastRow = -1;
            int firstCol = -1;
            int lastCol = -1;
            for (int i = 0; i < 91; i++) {
                for (int j = 0; j < 91; j++) {
                    if (gridView.get(i, j) != null) {
                        if (firstRow == -1 || firstRow > i) {
                            firstRow = i;
                        }
                        if (firstCol == -1 || firstCol > j) {
                            firstCol = j;
                        }
                        if (lastRow < i) {
                            lastRow = i;
                        }
                        if (lastCol < j) {
                            lastCol = j;
                        }
                    }

                }
            }
            boolean correspondence = false;

            for (Player player : this.players) {
                if (player.getHand().size() == 0) {
                    player.addScore(6);
                    return true;
                }

                for (Tile handTile : player.getHand()) {
                    for (int i = firstRow; i < lastRow; i++) {
                        for (int j = firstCol; j < lastCol; j++) {
                            if (gridView.get(i, j) != null) {
                                if (handTile.color() == gridView.get(i, j).color() ||
                                        handTile.shape() == gridView.get(i, j).shape()) {
                                    correspondence = true;
                                }
                            }
                        }
                    }
                }
            }
            if (!correspondence) {
                return true;
            }
        }
        return false;
    }

    /**
     * Writes the current object to a file to make a backup.
     *
     * @param fileName the name of the file to write the object to
     * @throws QwirkleException if an error occurs while writing the object to the file
     */
    public void write(String fileName) throws QwirkleException {
        String folderName = "sauvegarde";
        String filePath = folderName + "/" + fileName;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(this);

        } catch (Exception e) {
            throw new QwirkleException("error writing to file ");
        }

    }

    /**
     * Private method to get a Tile object from the current player's hand.
     *
     * @param pos the position of the tile in the current player's hand
     * @return a Tile object representing the tile in the specified position in the current player's hand
     */
    private Tile getTileFromHand(int pos) {
        return players[currentPlayer].getHand().get(pos);
    }
}

