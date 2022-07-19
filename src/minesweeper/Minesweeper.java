package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

import java.util.concurrent.TimeUnit;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */
    private ConsoleUI userInterface;

    private long startMillis;

    public int getPlayingSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(startMillis - System.currentTimeMillis());
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        userInterface = new ConsoleUI();
        System.out.println("Hello " + System.getProperty("user.name"));
        startMillis = System.currentTimeMillis();
        System.out.println(getPlayingSeconds());
        Field field = new Field(10, 10, 10);
        userInterface.newGameStarted(field);

    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }
}
