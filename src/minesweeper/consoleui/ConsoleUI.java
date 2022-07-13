package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.Mine;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

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
        do {
            update();
            processInput();
            throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        int colOrder = 0;
        char rowOrder = 65;
        //throw new UnsupportedOperationException("Method update not yet implemented");
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int col = 0; col < field.getColumnCount(); col++) {

                if (row == 0 && col == 0) {
                    System.out.print("  ");
                } else if (row == 0) {
                    System.out.print(colOrder);
                    System.out.print(' ');
                    colOrder++;
                } else if (col == 0) {
                    System.out.print(rowOrder);
                    System.out.print(' ');
                    rowOrder++;
                } else {
                    if (field.getTile(row, col) instanceof Mine) {
                        System.out.print("*");
                    } else System.out.print(field.countAdjacentMines(row, col));
                    System.out.print(' ');
                }
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
    }
}
