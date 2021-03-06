package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static Minesweeper instance;

    private Settings setting;

    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
    }

    public static Minesweeper getInstance() {
        if (instance == null) {
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

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        userInterface = new ConsoleUI();
        System.out.println("Hello enter your name: ");
        String name = readLine();
        setting = Settings.load();
        bestTimes.addPlayerTime(name, getPlayingSeconds());
        startMillis = System.currentTimeMillis();
        Field field = new Field(
                setting.getRowCount(),
                setting.getColumnCount(),
                setting.getMineCount()
        );
        userInterface.newGameStarted(field);

    }


    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Minesweeper.getInstance();

    }
}
