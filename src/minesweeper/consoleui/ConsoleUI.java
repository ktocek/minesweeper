package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    private String format = "%2s";

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;

        this.format = "%"
                + (1 + String.valueOf(field.getColumnCount()).length())
                + "s";
        do {
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {
                System.out.println("YOU WON!!");
                System.exit(0);
            }
            if (field.getState() == GameState.FAILED) {
                System.out.println("YOU LOSE!!");
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //throw new UnsupportedOperationException("Method update not yet implemented");
        System.out.printf(format, "");
        for (int c = 0; c < field.getColumnCount(); c++) {
            System.out.printf(format, c);
        }
        System.out.println();

        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.printf(format, (char) (row + 65));
            for (int col = 0; col < field.getColumnCount(); col++) {
                System.out.printf(format, field.getTile(row, col));
            }
            System.out.println();
        }
        System.out.print("Please enter your selection <X> EXIT, <MA1> MARK, <OB4> OPEN:");
        System.out.println();
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        //throw new UnsupportedOperationException("Method processInput not yet implemented");
        final String regex = "(X|x)|((M|O|m|o)([A-Za-z])(\\d*))";
        String line = readLine();
        System.out.println(line);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);


        if (matcher.matches()) {

            char c = matcher.group(4).toUpperCase().charAt(0);
            int row = ((int) c) - 65;
            int col = Integer.parseInt(matcher.group(5));

            if (row >= field.getRowCount() || col >= field.getColumnCount()) {
                System.out.println("Bad input!");
            } else {
                if (matcher.group(1) != null) {
                    System.exit(0);
                }
                if (matcher.group(3).toLowerCase().equals("m")) {
                    field.markTile(row, col);
                }
                if (matcher.group(3).toLowerCase().equals("o")) {
                    field.openTile(row, col);
                }
            }
        } else System.out.println("Bad input!");
    }
}
