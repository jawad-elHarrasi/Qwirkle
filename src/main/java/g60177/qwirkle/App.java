package g60177.qwirkle;

import g60177.qwirkle.model.Direction;
import g60177.qwirkle.model.Game;
import g60177.qwirkle.view.View;

import java.io.File;
import java.util.*;

import static g60177.qwirkle.view.View.*;

public class App {

    private static final String QUIT = "^\\s*q\\s*$";
    private static final String PASS = "^\\s*p\\s*$";
    private static final String FIRST_PLAY = "^\\s*f\\s+[lrud]\\s+\\d(\\s*[1-5])*\\s*$";
    private static final String PLAY_ONE = "^\\s*o(\\s+([0-9]|[1-8][0-9]|90)){2}\\s+[0-5]\\s*$";
    private static final String PLAY_LINE = "^\\s*l(\\s+([0-9]|[1-8][0-9]|90)){2}\\s+[lrud]\\s+[0-5](\\s*[1-5])*\\s*";
    private static final String PLIC_PLOC = "^\\s*m(\\s+([0-9]|[1-8][0-9]|90)){2}\\s+[0-5]((\\s+([0-9]|[1-8][0-9]|90)){2}\\s+[0-5])*\\s*$";

    private final static int minPlayer = 2;
    private final static Scanner clavier = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("""
                \033[93m                                                                                                                                                                                  \s
                \033[93m                                                                                                                                                                                  \s
                \033[93m           .---.          ,--,        ,-.                   ____             \033[38;5;208m       ___                  \033[94m      ,----..                                     ,-.  ,--,            \s
                \033[0m\033[93m          /. ./|        ,--.'|    ,--/ /|                 ,'  , `.            \033[38;5;208m    ,--.'|_                \033[94m    /   /   \\                ,--,            ,--/ /|,--.'|            \s
                \033[0m\033[93m      .--'.  ' ;        |  | :  ,--. :/ |   ,---.      ,-+-,.' _ |           \033[38;5;208m     |  | :,'   ,---.       \033[94m   /   .     :         .---,--.'|    __  ,-,--. :/ ||  | :            \s
                \033[0m\033[93m     /__./ \\ : |        :  : '  :  : ' /   '   ,'\\  ,-+-. ;   , ||          \033[38;5;208m      :  : ' :  '   ,'\\   \033[94m     .   /   ;.  \\       /. ./|  |,   ,' ,'/ /:  : ' / :  : '            \s
                \033[0m\033[93m .--'.  '   \\' .  ,---. |  ' |  |  '  /   /   /   |,--.'|'   |  ||,---.      \033[38;5;208m   .;__,'  /  /   /   |    \033[94m  .   ;   /  ` ;    .-'-. ' `--'_   '  | |' |  '  /  |  ' |     ,---.  \s
                \033[0m\033[93m/___/ \\ |    ' ' /     \\'  | |  '  |  :  .   ; ,. |   |  ,', |  |/     \\   \033[38;5;208     |  |   |  .   ; ,. :  \033[94m     ;   |  ; \\ ; |   /___/ \\: ,' ,'|  |  |   ,'  |  :  '  | |    /     \\ \s
                \033[0m\033[93m;   \\  \\;      :/    /  |  | :  |  |   \\ '   | |: |   | /  | |--/    /  |   \033[38;5;208m    :__,'| :  '   | |: :  \033[94m     |   :  | ; | '.-'.. '   ' '  | |  '  :  / |  |   \\ |  | :   /    /  |\s
                \033[0m\033[93m \\   ;  `      .    ' / '  : |__'  : |. \\'   | .; |   : |  | , .    ' / |    \033[38;5;208m     '  : |__'   | .; :    \033[94m   .   |  ' ' ' /___/ \\:     |  | :  |  | '  '  : |. \\'  : |__.    ' / |\s
                \033[0m\033[93m  .   \\    .\\  '   ;   /|  | '.'|  | ' \\ |   :    |   : |  |/  '   ;   /|   \033[38;5;208m      |  | '.'|   :    |    \033[94m   '   ;  \\; /  .   \\  ' .\\  '  : |__;  : |  |  | ' \\ |  | '.''   ;   /|\s
                \033[0m\033[93m   \\   \\   ' \\ '   |  / ;  :    '  : |--' \\   \\  /|   | |`-'   '   |  / |  \033[38;5;208m       ;  :    ;\\   \\  / \033[94m        \\   \\  ',  . \\   \\   ' \\ |  | '.'|  , ;  '  : |--';  :    '   |  / |\s
                \033[0m\033[93m    :   '  |--"|   :    |  ,   /;  |,'     `----' |   ;/       |   :    |      \033[38;5;208m   |  ,   /  `----'       \033[94m   ;   :      ; \\   \\  |--";  :    ;---'   ;  |,'   |  ,   /|   :    |\s
                \033[0m\033[93m     \\   \\ ;    \\   \\  / ---`-' '--'              '---'         \\   \\  /  \033[38;5;208m         ---`-'           \033[94m         \\   \\ .'`--" \\   \\ |   |  ,   /        '--'      ---`-'  \\   \\  / \s
                \033[0m\033[93m      '---"      `----'                                          `----'        \033[38;5;208m                         \033[94m       `---`        '---"     ---`-'                            `----'  \s
                \033[0m""");

        try {

            System.out.println("do you want to retrieve a previous party (y or n )");
            String input = clavier.nextLine().toLowerCase();
            if (input.equals("y")) {
                String cheminDossier = "sauvegarde";
                File dossier = new File(cheminDossier);
                File[] files = dossier.listFiles();
                if (files.length > 0) {
                System.out.println("which party you want to retrieve");

                    for (File file : files) {
                        System.out.println(file.getName());
                    }

                    String fileToRetrieve = clavier.nextLine();
                    for (File file : files) {
                        if (Objects.equals(fileToRetrieve, file.getName())) {
                            newGame(Game.getFromFile(fileToRetrieve));
                            return;
                        }
                    }
                }
                else {
                    System.out.println("\033[91m" + "Any backup to retrieve"+"\033[0m"+"\n");
                }


            }
        } catch (Exception e) {
            System.out.println(e);
        }
        int nbPlayer = 0;
        while (nbPlayer < minPlayer) {
            System.out.println("Enter number of player ");
            nbPlayer = robusterIntEntire();
        }
        int cpt = 1;
        HashSet<String> playerList = new HashSet<>();
        int lengthPlayer;
        while (nbPlayer > 0) {
            lengthPlayer = playerList.size();
            System.out.println("Enter the name of the " + cpt + " player");
            try {
                String playerName = clavier.nextLine();
                playerList.add(playerName);
                if (playerList.size() != lengthPlayer) {
                    nbPlayer--;
                    cpt++;
                } else {
                    throw new IllegalArgumentException("You already entered this name, retry");
                }

            } catch (Exception e) {
                displayError(e.getMessage());
            }
        }
        List<String> listPlayerName = new ArrayList<>(playerList);

        newGame(new Game(listPlayerName));


    }

    /**
     * Starts a new game with the given player list.
     *
     * @param game the game to play
     */
    private static void newGame(Game game) {
        boolean over = false;
        while (!over) {
            try {
                over = game.isOver();
                if (over) {
                    displayEnd(game.getPlayers());
                    return;
                }

                View.display(game.getGrid());
                displayHelp();
                display(game.getCurrentPlayersHand(), game.getCurrentPlayerName(), game.getPlayerScore());
                var play = readLine();
                String[] split = play.split("\\s");
                Direction direction;
                switch (split[0]) {
                    case "q":
                        System.out.println("do you want to save this game (y or n)");
                        String input = clavier.nextLine().toLowerCase();
                        if (input.equals("y")) {
                            System.out.println("how you want to call this backup");
                            String name = clavier.nextLine();
                            game.write(name);
                        }
                        System.out.println("\033[0;33m" + "Good Bye" + "\033[0m");
                        return;

                    case "p":
                        game.pass();
                        break;
                    case "f":
                        direction = direction(split[1]);
                        int[] indexes = new int[split.length - 2];
                        for (int j = 0; j < indexes.length; j++) {
                            indexes[j] = Integer.parseInt(split[j + 2]);
                        }
                        game.first(direction, indexes);
                        break;

                    case "o":
                        int row = Integer.parseInt(split[1]);
                        int col = Integer.parseInt(split[2]);
                        int index = Integer.parseInt(split[3]);
                        game.play(row, col, index);
                        break;
                    case "l":
                        int row1 = Integer.parseInt(split[1]);
                        int col1 = Integer.parseInt(split[2]);
                        direction = direction(split[3]);
                        int[] indexes2 = new int[split.length - 4];
                        for (int j = 0; j < indexes2.length; j++) {
                            indexes2[j] = Integer.parseInt(split[j + 4]);
                        }
                        game.play(row1, col1, direction, indexes2);

                        break;
                    case "m":
                        int[] indexes3 = new int[split.length - 1];
                        int cpt = 0;
                        for (int j = 1; j < split.length; j += 3) {
                            indexes3[cpt] = Integer.parseInt(split[j]);
                            indexes3[cpt + 1] = Integer.parseInt(split[j + 1]);
                            indexes3[cpt + 2] = Integer.parseInt(split[j + 2]);
                            cpt += 3;

                        }
                        game.play(indexes3);
                        break;
                }


            } catch (Exception e) {
                displayError(e.getMessage());
            }


        }
    }

    /**
     * Reads a line of input from the console and validates it against regular expressions for valid game commands.
     *
     * @return A string containing the user's input if it matches one of the regular expressions, otherwise prompts the user to retry.
     */
    private static String readLine() {
        String input = null;
        do {
            if (input != null) {
                System.out.println("\033[91m" + "wrong entry, retry" + "\033[0m");
            } else {
                System.out.println("\033[94m" + "what do you play " + "\033[0m");
            }

            input = clavier.nextLine().toLowerCase();

        } while (!input.matches(PASS) && !input.matches(QUIT) && !input.matches(FIRST_PLAY) && !input.matches(PLAY_ONE) && !input.matches(PLAY_LINE) && !input.matches(PLIC_PLOC));

        return input;
    }


    /**
     * Converts a string representation of a direction to a Direction object.
     *
     * @param direction a string representation of a direction ("u" for up, "d" for down, "r" for right, "l" for left)
     * @return the corresponding Direction object
     */
    private static Direction direction(String direction) {
        return switch (direction) {
            case "u" -> Direction.UP;
            case "d" -> Direction.DOWN;
            case "r" -> Direction.RIGHT;
            case "l" -> Direction.LEFT;
            default -> null;
        };
    }

    /**
     * This method reads an integer from the user and ensures that it is a positive integer.
     *
     * @return the positive integer entered by the user
     */
    private static int robusterIntEntire() {
        int nb;
        do {
            while (!clavier.hasNextInt()) {
                System.out.println("It's not an integer");
                clavier.next();
            }
            nb = clavier.nextInt();
            clavier.nextLine();
            if (nb < 0) {
                System.out.println("Integer but negative");
            }
        } while (nb < 0);
        return nb;
    }
}
