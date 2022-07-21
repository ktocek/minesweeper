package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.Settings;
import minesweeper.core.Field;
import minesweeper.core.GameState;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    private String format = "%2s";

    private static final Pattern PATTERN = Pattern.compile("(X|x)|((M|O|m|o)([A-Za-z])(\\d*))");

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
        System.out.println("Enter game difficulty:");
        System.out.println("(1) BEGINNER, (2) INTERMEDIATE, (3) EXPERT, (ENTER) DEFAULT");
        String level = readLine();
        if(level != null && !level.equals("")) {
            try {
                int intLevel = Integer.parseInt(level);
                Settings s = switch (intLevel) {
                    case 2 -> Settings.INTERMEDIATE;
                    case 3 -> Settings.EXPERT;
                    default -> Settings.BEGINNER;
                };
                Minesweeper.getInstance().setSetting(s);
                this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
            } catch (NumberFormatException e) {
                //empty naschval
            }
        }
        do {
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {
                System.out.println("YOU WON!!");

                System.out.println(Minesweeper.getInstance().getBestTimes());
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
        System.out.println(Minesweeper.getInstance().getPlayingSeconds());
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
        String line = readLine();

        try {
            handleInput(line);
        }catch(WrongFormatException ex){
            System.out.println(ex.getMessage());
        }


    }

    void handleInput(String input) throws WrongFormatException{

        Matcher matcher = PATTERN.matcher(input);

        if (matcher.matches()) {

            if (matcher.group(1) != null) {
                System.exit(0);
            }

            char c = matcher.group(4).toUpperCase().charAt(0);
            int row = ((int) c) - 65;
            int col = Integer.parseInt(matcher.group(5));

            if (row >= field.getRowCount() || col >= field.getColumnCount()) {
                throw new WrongFormatException("Bad input!");
            } else {
                if (matcher.group(3).toLowerCase().equals("m")) {
                    field.markTile(row, col);
                }
                if (matcher.group(3).toLowerCase().equals("o")) {
                    field.openTile(row, col);
                }
            }
        } else throw new WrongFormatException("Bad input!");
    }
}
