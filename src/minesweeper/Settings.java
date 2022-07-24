package minesweeper;

import java.io.*;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;

    public static Settings BEGINNER = new Settings(9, 9, 10);
    public static Settings INTERMEDIATE = new Settings(16, 16, 40);
    public static Settings EXPERT = new Settings(16, 30, 99);

    private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "minesweeper.settings";

    public Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Settings)) {
            return false;
        }
        Settings s = (Settings) o;
        return s.rowCount == rowCount && s.columnCount == columnCount && s.mineCount == mineCount;
    }

    @Override
    public int hashCode() {
        return rowCount * columnCount * mineCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SETTING_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
           System.out.println("Failed to write settings to the object.");
        }
    }

    public static Settings load() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SETTING_FILE))) {
            Settings s = (Settings) ois.readObject();
            return s;
        } catch (IOException e) {
            System.out.println("Failed to open settings file.");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to quote settings.");
        }
        return BEGINNER;
    }

    @Override
    public String toString() {
        return "Settings [" + rowCount + "," + columnCount + "," + mineCount + "]";
    }

}
