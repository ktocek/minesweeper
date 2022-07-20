package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.GameState;

import java.util.concurrent.TimeUnit;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */

    private static Minesweeper instance;

    public static Minesweeper getInstance(){
        if (instance == null){
            new Minesweeper();
        }
        return instance;
    }

    private ConsoleUI userInterface;

    private long startMillis;

    private BestTimes bestTimes = new BestTimes();

    public BestTimes getBestTimes() {
        return bestTimes;
    }

    public int getPlayingSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startMillis);
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        bestTimes.addPlayerTime("Tino",7);

        userInterface = new ConsoleUI();
        System.out.println("Hello " + System.getProperty("user.name"));
        startMillis = System.currentTimeMillis();
        Field field = new Field(10, 10, 10);
        userInterface.newGameStarted(field);

    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        Minesweeper.getInstance();

    }
}
