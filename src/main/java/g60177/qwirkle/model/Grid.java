package g60177.qwirkle.model;


import java.io.Serializable;
import java.util.*;

public class Grid implements Serializable {

    private final Tile[][] tiles;
    private boolean isEmpty;

    public Grid() {
        this.tiles = new Tile[91][91];
        this.isEmpty = true;
    }

    /**
     * Retrieves the tile located at the specified position in the tile grid.
     *
     * @param row the row position of the tile.
     * @param col the column position of the tile.
     * @return the tile located at the specified position, or null if the position is outside the limits of the grid.
     */
    public Tile get(int row, int col) {
        if (row > 90 || col > 90 || row < 0 || col < 0) {
            return null;
        }
        return tiles[row][col];
    }


    /**
     * Adds a line of tiles to the center of the game board in a given direction.
     *
     * @param d    the direction in which the line of tiles will be added.
     * @param line the line of tiles to be added to the center of the board.
     * @return the number of points that the line has to report.
     * @throws QwirkleException if the board is not empty, if the line contains more than 6 tiles, if the line contains two identical tiles,
     *                          or if the tiles in the line do not have any characteristics in common.
     */
    public int firstAdd(Direction d, Tile... line) {
        int nbPoints = 0;
        if (!isEmpty) {
            throw new QwirkleException("the plateau isn't empty");
        }
        if (d == null) {
            throw new QwirkleException("The direction can be null");
        }
        for (Tile tile : line) {
            if (tile == null) {
                throw new QwirkleException("there is null tile in the line");
            }
        }
        lenghtAndDifference(line); // return an exception if the line is longer than 6 or if the tile are exactly the same
        sharedCharacteristic(line);// return an exception if the tiles of the line haven't any shared characteristic
        var row = 45;
        var col = 45;
        for (Tile lines : line) {
            tiles[row][col] = lines;
            row += d.getDeltaRow();
            col += d.getDeltaCol();
            nbPoints++;
        }
        this.isEmpty = false;
        if (nbPoints == 6) {
            nbPoints += 6;
        }
        return nbPoints;
    }

    /**
     * Adds a tile to the game board at the specified position.
     *
     * @param row  the row position at which to add the tile.
     * @param col  the column position at which to add the tile.
     * @param tile the tile to be added to the board.
     * @return the number of points that the tile has to report.
     * @throws QwirkleException if the position is outside the limits of the grid, if the grid is empty and the first tile has not been added yet,
     *                          if the position is already occupied by another tile, or if the tile does not have any characteristics in common with the tiles surrounding it,
     *                          if the position haven't any tile around her, if there is already a tile wih exactly the same characteristics in the X or Y axe.
     */
    public int add(int row, int col, Tile tile) {
        TileAtPosition tileAtPosition = new TileAtPosition(row, col, tile);
        return addTiles(tileAtPosition);
    }


    /**
     * Adds a line of tiles to the grid, starting at the specified row and column in the specified direction.
     *
     * @param row  the row in which the line starts (0-based index)
     * @param col  the column in which the line starts (0-based index)
     * @param d    the direction in which the line should be added (either horizontal or vertical)
     * @param line the tiles to be added to the grid
     * @return the number of points that the line has to report.
     * @throws QwirkleException if the specified position is out of bounds of the grid,
     *                          if there are no shared characteristics between the tiles in the line,
     *                          if there are already tiles in the line where the new tiles should be placed
     *                          if there are no shared characteristics between the new tiles and the tiles surrounding them in the grid
     *                          if there are duplicate tile in the X or Y axe
     *                          if the position haven't any tile around her
     */
    public int add(int row, int col, Direction d, Tile... line) {
        TileAtPosition[] tileAtPositions = new TileAtPosition[line.length];
        for (int i = 0; i < line.length; i++) {
            TileAtPosition tileAtPosition = new TileAtPosition(row, col, line[i]);
            tileAtPositions[i] = tileAtPosition;
            row += d.getDeltaRow();
            col += d.getDeltaCol();

        }
        return addTiles(tileAtPositions);
    }

    /**
     * Adds new tile(s) at the specified positions.
     *
     * @param line the tiles to add
     * @return the number of points that the line has to report.
     * @throws QwirkleException if the grid is empty
     *                          if the tile aren't in the same axe
     *                          if you are out of index of the grid
     *                          if you try to put you tile at index not free
     *                          if there is any tile around the tile you are trying to put
     *                          if there is exactly the same tile in the X or Y axe
     *                          if there is any shared characteristics with the tiles surrounding the tile you are trying to put
     *                          if there is a gap in the line where you trie to put you tiles
     */
    public int add(TileAtPosition... line) {
        return addTiles(line);
    }

    /**
     * Adds new tile(s) at the specified positions.
     *
     * @param line the tiles to add
     * @return the number of points that the line has to report.
     * @throws QwirkleException if the grid is empty
     *                          if the tile aren't in the same axe
     *                          if you are out of index of the grid
     *                          if you try to put one of the tiles at index not free
     *                          if there is any tile around the tile you are trying to put
     *                          if there is exactly the same tile in the X or Y axe
     *                          if one of the tiles haven't the characteristic of the line (color or shape)
     *                          if there is any shared characteristics with the tiles surrounding the tile you are trying to put
     *                          if there is a gap in the line where you trie to put you tiles
     */
    private int addTiles(TileAtPosition... line) {
        if (this.isEmpty) {
            throw new QwirkleException("the grid is empty please use firstAdd first");
        }
        if (line.length == 0) {
            throw new QwirkleException("you have entered no parameters");
        }
        int tmpRow;
        int tmpCol;

        for (TileAtPosition tile : line) {
            tmpRow = tile.row();
            tmpCol = tile.col();
            if (tmpRow > 90 || tmpCol > 90 || tmpRow < 0 || tmpCol < 0) {
                throw new QwirkleException("You are out of index of the grid ");
            }
        }
        int row = line[0].row();
        int col = line[0].col();
        for (int i = 1; i < line.length; i++) {
            if (row != line[i].row() && col != line[i].col()) {
                throw new QwirkleException("You tiles are not in the same axe");
            }
        }
        Tile[] receptacleOfTileAtPosition = new Tile[line.length];
        for (int i = 0; i < line.length; i++) {
            receptacleOfTileAtPosition[i] = line[i].tile();
        }
        if (row > 91 || col > 91 || row < 0 || col < 0) {
            throw new QwirkleException("You are out of index of the grid ");
        }
        lenghtAndDifference(receptacleOfTileAtPosition); // return an exception if the line is longer than 6 or lower than 1 or if the tile are exactly the same
        sharedCharacteristic(receptacleOfTileAtPosition);// return an exception if the tiles of the line haven't any shared characteristic
        int row1;
        int col1;
        boolean freedomException = true;
        for (TileAtPosition tile : line) {
            row1 = tile.row();
            col1 = tile.col();
            if (tiles[row1][col1] != null) {
                throw new QwirkleException("you try to put you tile at index not free");
            }
            if (verifyTheFreedomAroundTheTile(row1, col1)) {
                freedomException = false;
            }
        }
        TileAtPosition currentTile;
        boolean notFreeException = false;
        for (TileAtPosition tile : line) {
            row1 = tile.row();
            col1 = tile.col();
            if (tiles[row1][col1] != null) {
                notFreeException = true;
            }
            currentTile = tile;
            tiles[row1][col1] = currentTile.tile();
        }
        boolean axeXAndYDuplicateException = false;
        boolean sharedCharacteristicsException = false;
        boolean XandYAxesException = false;
        boolean holeBetweenTiles = false;

        if (line.length > 1) {
            holeBetweenTiles = gapBetweenTiles(line);
        }

        Color color;
        Shape shape;
        for (TileAtPosition tileAtPosition : line) {
            row1 = tileAtPosition.row();
            col1 = tileAtPosition.col();
            currentTile = tileAtPosition;

            if (verifyTheXAndYAxesDuplicateTiles(row1, col1, currentTile.tile())) {
                axeXAndYDuplicateException = true;
            }
            if (verifyTheXAndYAxes(row1, col1, currentTile.tile())) {
                XandYAxesException = true;
            }
            color = currentTile.tile().color();
            shape = currentTile.tile().shape();
            if (sharedCharacteristics(row1, col1, color, shape)) {
                sharedCharacteristicsException = true;
            }

        }
        if (freedomException || axeXAndYDuplicateException || sharedCharacteristicsException
                || notFreeException || XandYAxesException || holeBetweenTiles) {
            for (TileAtPosition tileAtPosition : line) {
                row1 = tileAtPosition.row();
                col1 = tileAtPosition.col();
                tiles[row1][col1] = null;
            }
        }
        if (freedomException) {
            throw new QwirkleException("You can't put your tile here cause there is any tile around her");
        }
        if (axeXAndYDuplicateException) {
            throw new QwirkleException("You can't put your tile here because the is already the same in the Y axe");
        }
        if (sharedCharacteristicsException) {
            throw new QwirkleException("You can't put your tile here cause there is any shared characteristics" +
                    " with the tiles surrounding it ");
        }
        if (notFreeException) {
            throw new QwirkleException("You can't put two tile at same position");
        }
        if (XandYAxesException) {
            throw new QwirkleException("haven't the characteristic of the line (color or shape)");
        }
        if (holeBetweenTiles) {
            throw new QwirkleException("You can't put tiles with a gap between them");
        }
        return points(line);
    }


    /**
     * @return if the grid is empty or not
     */
    public boolean isEmpty() {
        return isEmpty;
    }


    /**
     * Calculates the score for a line of tiles.
     *
     * @param line the line of tiles represented by TileAtPosition objects
     * @return the score for the line of tiles
     */
    private int points(TileAtPosition... line) {
        int score = 0;
        Direction[] direction = new Direction[2];

        if (line.length != 1 && line[0].row() == line[1].row()) {
            direction[0] = Direction.RIGHT;
            direction[1] = Direction.LEFT;
        } else {
            direction[0] = Direction.UP;
            direction[1] = Direction.DOWN;
        }
        TileAtPosition t1 = line[0];
        int tempRow = t1.row();
        int tempCol = t1.col();
        int cpt = 1;
        int tmpCpt = 0;

        for (Direction d : direction) {
            tempRow += d.getDeltaRow();
            tempCol += d.getDeltaCol();
            while (tiles[tempRow][tempCol] != null) {
                if (tmpCpt == 0) {
                    score++;
                }
                score++;
                tmpCpt++;
                tempRow += d.getDeltaRow();
                tempCol += d.getDeltaCol();
                cpt++;

            }
            tempRow = t1.row();
            tempCol = t1.col();
        }
        if (cpt == 6) {
            score += 6;
        }
        if (line.length != 1 && line[0].row() == line[1].row()) {
            direction[0] = Direction.UP;
            direction[1] = Direction.DOWN;
        } else {
            direction[0] = Direction.LEFT;
            direction[1] = Direction.RIGHT;
        }
        int tempScore = 0;
        for (TileAtPosition t : line) {
            tmpCpt = 0;
            cpt = 1;
            tempRow = t.row();
            tempCol = t.col();
            for (Direction d : direction) {

                tempRow += d.getDeltaRow();
                tempCol += d.getDeltaCol();
                while (tiles[tempRow][tempCol] != null) {
                    if (tmpCpt == 0) {
                        tempScore++;
                    }
                    tmpCpt++;
                    tempScore++;
                    tempRow += d.getDeltaRow();
                    tempCol += d.getDeltaCol();
                    cpt++;
                }
                tempRow = t.row();
                tempCol = t.col();
            }
            if (cpt == 6) {
                tempScore += 6;
            }
        }
        return score + tempScore;
    }


    /**
     * Verifies if there are any adjacent tiles around the specified tile location.
     *
     * @param row the row index of the tile location
     * @param col the column index of the tile location
     * @return true if there are adjacent tiles, false otherwise
     */
    private boolean verifyTheFreedomAroundTheTile(int row, int col) {
        int cpt = 0;
        if (col < tiles.length - 1 && tiles[row][col + 1] != null) {
            cpt++;
        }
        if (col > 0 && tiles[row][col - 1] != null) {
            cpt++;
        }
        if (row > 0 && tiles[row - 1][col] != null) {
            cpt++;
        }
        if (row < tiles.length - 1 && tiles[row + 1][col] != null) {
            cpt++;
        }
        return cpt != 0;
    }


    /**
     * Checks if all tiles in the line have at least one common characteristic.
     *
     * @param line the line of tiles to check
     * @throws QwirkleException if at least two tiles don't have any common characteristics
     */
    private void sharedCharacteristic(Tile[] line) {
        Color color = line[0].color();
        Shape shape = line[0].shape();
        for (int i = 1; i < line.length; i++) {
            if (line[i].color() != color && line[i].shape() != shape) {
                throw new QwirkleException("no characteristics in common");
            }
        }
    }


    /**
     * Verifies if the length and difference of a line of tiles being played is valid.
     *
     * @param line the line of tiles being played
     * @throws QwirkleException if the line is too long or contains duplicates
     */
    private void lenghtAndDifference(Tile[] line) {
        if (line.length > 6 || line.length < 1) {
            throw new QwirkleException("required number of tile not respected ");
        }
        HashSet<Tile> hashSetList = new HashSet<>();
        Collections.addAll(hashSetList, line);
        if (line.length != hashSetList.size()) {
            throw new QwirkleException("You can't put two tiles with exactly the same characteristics");
        }
    }

    /**
     * Verifies if the placement of a tile on the game board is valid in the X and Y axes.
     *
     * @param row the row index of the tile placement
     * @param col the column index of the tile placement
     *            //     * @param lines the tile being placed
     * @throws QwirkleException if the tile placement is invalid due to duplication of the tile in the X or Y axis
     */
    private boolean sharedCharacteristics(int row, int col, Color color, Shape shape) {
        boolean exeption = false;
        if (col < tiles.length - 1 && tiles[row][col + 1] != null) {
            if (tiles[row][col + 1].color() != color && tiles[row][col + 1].shape() != shape) {
                exeption = true;
            }
        }
        if (col > 0 && tiles[row][col - 1] != null) {
            if (tiles[row][col - 1].color() != color && tiles[row][col - 1].shape() != shape) {
                exeption = true;
            }
        }
        if (row > 0 && tiles[row - 1][col] != null) {
            if (tiles[row - 1][col].color() != color && tiles[row - 1][col].shape() != shape) {
                exeption = true;
            }
        }
        if (row < tiles.length - 1 && tiles[row + 1][col] != null) {
            if (tiles[row + 1][col].color() != color && tiles[row + 1][col].shape() != shape) {
                exeption = true;
            }
        }
        return exeption;
    }

    /**
     * Verifies if there are any duplicate tiles on the same row and column as the given tile.
     *
     * @param row   the row of the given tile
     * @param col   the column of the given tile
     * @param lines the given tile
     * @return true if there are duplicate tiles on the same row or column as the given tile, false otherwise
     */
    private boolean verifyTheXAndYAxesDuplicateTiles(int row, int col, Tile lines) {
        List<Tile> axeY = new ArrayList<>();
        boolean exception = false;
        axeY.add(lines);
        var i = 1;
        while (tiles[row - i][col] != null) {
            axeY.add(tiles[row - i][col]);
            i++;
        }
        i = 1;
        while (tiles[row + i][col] != null) {
            axeY.add(tiles[row + i][col]);
            i++;
        }
        Set<Tile> verifyY = new HashSet<>(axeY);
        if (verifyY.size() != axeY.size()) {
            exception = true;
        }
        List<Tile> axeX = new ArrayList<>();
        axeX.add(lines);
        i = 1;
        while (tiles[row][col + i] != null) {
            axeX.add(tiles[row][col + i]);
            i++;
        }
        i = 1;
        while (tiles[row][col - i] != null) {
            axeX.add(tiles[row][col - i]);
            i++;
        }
        Set<Tile> verifyX = new HashSet<>(axeX);
        if (verifyX.size() != axeX.size()) {
            exception = true;
        }
        return exception;
    }

    /**
     * Verifies if the given tile forms a valid line on both X and Y axes. A line is considered valid if all the tiles
     * in the line have either the same color or the same shape.
     *
     * @param row   the row of the tile
     * @param col   the column of the tile
     * @param lines the tile to verify
     * @return true if the tile forms an invalid line on either X or Y axis, false otherwise
     */
    private boolean verifyTheXAndYAxes(int row, int col, Tile lines) {
        List<Color> colorsX = new ArrayList<>();
        List<Shape> shapesX = new ArrayList<>();
        boolean error = false;
        Direction d = Direction.RIGHT;
        int tmpRow = row;
        int tmpCol = col;
        colorsX.add(lines.color());
        shapesX.add(lines.shape());
        tmpRow += d.getDeltaRow();
        tmpCol += d.getDeltaCol();
        while (tiles[tmpRow][tmpCol] != null) {
            colorsX.add(tiles[tmpRow][tmpCol].color());
            shapesX.add(tiles[tmpRow][tmpCol].shape());
            tmpRow += d.getDeltaRow();
            tmpCol += d.getDeltaCol();
        }
        tmpRow = row;
        tmpCol = col;
        tmpRow += d.opposite().getDeltaRow();
        tmpCol += d.opposite().getDeltaCol();
        while (tiles[tmpRow][tmpCol] != null) {
            colorsX.add(tiles[tmpRow][tmpCol].color());
            shapesX.add(tiles[tmpRow][tmpCol].shape());
            tmpRow += d.opposite().getDeltaRow();
            tmpCol += d.opposite().getDeltaCol();
        }

        Set<Color> hashColors = new HashSet<>(colorsX);
        Set<Shape> hashShape = new HashSet<>(shapesX);
        if (hashColors.size() != 1 && hashShape.size() != 1) {
            error = true;
        }

        List<Color> colorsY = new ArrayList<>();
        List<Shape> shapesY = new ArrayList<>();
        Direction d1 = Direction.UP;
        int tmpRow1 = row;
        int tmpCol1 = col;
        colorsY.add(lines.color());
        shapesY.add(lines.shape());
        tmpRow1 += d1.getDeltaRow();
        tmpCol1 += d1.getDeltaCol();
        while (tiles[tmpRow1][tmpCol1] != null) {
            colorsY.add(tiles[tmpRow1][tmpCol1].color());
            shapesY.add(tiles[tmpRow1][tmpCol1].shape());
            tmpRow1 += d1.getDeltaRow();
            tmpCol1 += d1.getDeltaCol();
        }
        tmpRow1 = row;
        tmpCol1 = col;
        tmpRow1 += d1.opposite().getDeltaRow();
        tmpCol1 += d1.opposite().getDeltaCol();
        while (tiles[tmpRow1][tmpCol1] != null) {
            colorsY.add(tiles[tmpRow1][tmpCol1].color());
            shapesY.add(tiles[tmpRow1][tmpCol1].shape());
            tmpRow1 += d1.opposite().getDeltaRow();
            tmpCol1 += d1.opposite().getDeltaCol();
        }

        Set<Color> hashColorsY = new HashSet<>(colorsY);
        Set<Shape> hashShapeY = new HashSet<>(shapesY);
        if (hashColorsY.size() != 1 && hashShapeY.size() != 1) {
            error = true;
        }
        return error;


    }

    /**
     * Checks if there is a gap between the tiles in the given line.
     *
     * @param line an array of TileAtPosition objects representing the line of tiles to check for gaps
     * @return true if there is a gap between the tiles in the line, false otherwise
     */
    private boolean gapBetweenTiles(TileAtPosition... line) {
        int maxRow = line[0].row();
        int minRow = line[0].row();
        int minCol = line[0].col();
        int maxCol = line[0].col();
        if (line[0].row() == line[1].row()) {
            for (int i = 1; i < line.length; i++) {
                if (line[i].col() < minCol) {
                    minCol = line[i].col();
                }
            }
            for (int i = 1; i < line.length; i++) {
                if (line[i].col() > maxCol) {
                    maxCol = line[i].col();
                }
            }
            int row = line[0].row();
            for (int i = minCol; i <= maxCol; i += Direction.RIGHT.getDeltaCol()) {
                if (tiles[row][i] == null) {
                    return true;
                }
            }

        } else {
            for (int i = 1; i < line.length; i++) {
                if (line[i].row() < minRow) {
                    minRow = line[i].row();
                }
            }
            for (int i = 1; i < line.length; i++) {
                if (line[i].row() > maxRow) {
                    maxRow = line[i].row();
                }
            }
            int col = line[0].col();
            for (int i = minRow; i <= maxRow; i += Direction.DOWN.getDeltaRow()) {
                if (tiles[i][col] == null) {
                    return true;
                }
            }

        }
        return false;

    }

}






