package g60177.qwirkle.view;

import g60177.qwirkle.model.*;


import java.util.List;

/**
 * The View class contains static methods used to display the game board and player's hands to the console. It also contains
 * a method for displaying a help message and error messages.
 */
public class View {

    /**
     * Displays the game board to the console.
     *
     * @param grid the game board to be displayed
     */
    public static void display(GridView grid) {
        if (grid.isEmpty()) {
            return;
        }

        System.out.println();
        System.out.println();
        int firstRow = -1;
        int lastRow = -1;
        int firstCol = -1;
        int lastCol = -1;
        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                if (grid.get(i, j) != null) {
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

        for (int i = firstRow; i <= lastRow; i++) {
            System.out.print(i + "| ");
            for (int j = firstCol; j <= lastCol; j++) {
                if (grid.get(i, j) == null) {
                    System.out.print("   ");
                } else {
                    System.out.print(" " + formTileShapeAndColor(grid.get(i, j)) + " ");

                }
            }

            System.out.println();
        }
        System.out.print("     ");
        for (int i = firstCol; i <= lastCol; i++) {
            if (i <= 9) {
                System.out.print("0" + i + "  ");
            } else {
                System.out.print(i + " ");
            }
        }

        System.out.println("\n" + "\n" + "\n");


    }

    /**
     * Displays a player's hand to the console.
     *
     * @param hand the player's hand to be displayed
     * @param name the name of the player
     */
    public static void display(List<Tile> hand, String name, int score) {
        System.out.println("\n" + "\n");
        System.out.println("Player : " + name);
        System.out.print("Hand : ");
        System.out.print("[ ");
        for (int i = 0; i < hand.size(); i++) {
            if (i != hand.size() - 1) {
                System.out.print(formTileShapeAndColor(hand.get(i)) + "  ");
            } else {
                System.out.print(formTileShapeAndColor(hand.get(i)));
            }
        }
        System.out.println(" ]");
        System.out.print("         ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(i + "  ");
        }
        System.out.println(" ");
        System.out.println(name + " have " + score + " points");

    }

    /**
     * Displays the end results by sorting and printing the players' scores in descending order.
     *
     * @param players an array of Player objects representing the players to display the results for
     */
    public static void displayEnd(Player[] players) {
        Player temp;
        for (int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length - 1; i++) {
                if (players[i].getScore() > players[i + 1].getScore()) {
                    temp = players[i];
                    players[i] = players[i + 1];
                    players[i + 1] = temp;
                }
            }
        }

        System.out.println("\033[32m" + "the top is :" + "\n");
        System.out.println("\033[33m" + "first : " + "                        " + players[players.length - 1].getName()
                + " with " + players[players.length - 1].getScore() + " points" + "\n");
        System.out.println("\033[38;5;208m" + "second:" + "     " + players[players.length - 2].getName() +
                " with " + players[players.length - 2].getScore() + " points" + "\n");
        System.out.println("\033[31m" + "third :" + "                                              "
                + players[players.length - 3].getName() + " with " + players[players.length - 3].getScore() + " points");
        System.out.println("\n" + "\033[34m" + "good Bye");

    }

    /**
     * Displays a help message to the console.
     */
    public static void displayHelp() {
        System.out.println();
        System.out.println("\033[91m" + " Q W I R K L E\n" + "\033[0m" + "\n" +
                "\033[38;5;208m" + "Qwirkle command:\n" + "\033[0m" +
                "\033[93m" + "- play 1 tile : o <row> <col> <i>\n" +
                "- play line: l <row> <col> <direction> <i1> [<i2>]\n" +
                "- play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]\n" +
                "- play first : f [direction] [<i2>]\n" +
                "- pass : p\n" +
                "- quit : q\n" + "\033[0m" +
                "\033[92m" + "i : index in list of tiles\n" +
                "d : direction in l (left), r (right), u (up), d (down)\n" + "\033[0m");
    }

    /**
     * Displays an error message to the console.
     *
     * @param message the error message to be displayed
     */
    public static void displayError(String message) {
        System.out.println("\033[91m" + "Warning : " + message + "\033[0m");
        System.out.println("\n");
    }

    /**
     * Transforms a tile's shape into a string representation.
     *
     * @param tile the tile whose shape is to be transformed
     * @return the string representation of the tile's shape
     */
    private static String transfromShape(Tile tile) {
        return switch (tile.shape()) {
            case CROSS -> "x";
            case ROUND -> "\u25CF";
            case DIAMOND -> "\u25C6";
            case STAR -> "\u2605";
            case SQUARE -> "\u25A0";
            case PLUS -> "\u271A";
        };
    }

    /**
     * Formats a tile's shape and color into a string representation that can be displayed on the console.
     *
     * @param tile the tile whose shape and color are to be formatted
     * @return the formatted string representation of the tile's shape and color
     */
    private static String formTileShapeAndColor(Tile tile) {
        return switch (tile.color()) {
            case RED -> "\033[91m" + transfromShape(tile) + "\033[0m";
            case GREEN -> "\033[92m" + transfromShape(tile) + "\033[0m";
            case YELLOW -> "\033[93m" + transfromShape(tile) + "\033[0m";
            case ORANGE -> "\033[38;5;208m" + transfromShape(tile) + "\033[0m";
            case PURPLE -> "\033[95m" + transfromShape(tile) + "\033[0m";
            case BLUE -> "\033[94m" + transfromShape(tile) + "\033[0m";
        };
    }


}
