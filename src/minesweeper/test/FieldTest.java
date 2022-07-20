package minesweeper.test;

import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Random randomGenerator = new Random();
    private Field field;
    private int rowCount;
    private int columnCount;
    private int minesCount;


    @BeforeEach
    public void initTest() {
        rowCount = randomGenerator.nextInt(10) + 5;
        columnCount = rowCount;
        minesCount = Math.max(1, randomGenerator.nextInt(rowCount * columnCount));
        field = new Field(rowCount, columnCount, minesCount);
    }

    @Test
    public void checkFieldInitialization() {
        assertEquals(rowCount, field.getColumnCount(), "Row count was initialized incorrectly");
        assertEquals(columnCount, field.getColumnCount(), "Column count was initialized incorrectly");
        assertEquals(minesCount, field.getMineCount(), "Mine count was initialized incorrectly");
        assertEquals(field.getState(), GameState.PLAYING, "Game state is not PLAYING");
    }

    @Test
    public void checkMarkTile() {
        int row = randomGenerator.nextInt(rowCount);
        int col = randomGenerator.nextInt(columnCount);
        field.markTile(row, col);
        assertEquals(Tile.State.MARKED, field.getTile(row, col).getState(), "Tile is not marked");
        field.markTile(row, col);
        assertEquals(Tile.State.CLOSED, field.getTile(row, col).getState(), "Tile is not marked");
    }

    @Test
    public void checkOpenMine() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (field.getTile(row, col) instanceof Mine) {
                    field.openTile(row, col);
                    assertEquals(Tile.State.OPEN, field.getTile(row, col).getState(), "Tile is not Open");
                    assertEquals(field.getState(), GameState.FAILED, "Game is not FAILED");
                }
            }
        }
    }


    @Test
    public void checkMinesCount() {
        int minesCounter = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (field.getTile(row, column) instanceof Mine) {
                    minesCounter++;
                }
            }
        }

        assertEquals(minesCount, minesCounter, "Field was initialized incorrectly - " +
                "a different amount of mines was counted in the field than amount given in the constructor.");
    }

    @Test
    public void fieldWithTooManyMines() {
        Field fieldWithTooManyMines = null;
        int higherMineCount = rowCount * columnCount + randomGenerator.nextInt(10) + 1;
        try {
            fieldWithTooManyMines = new Field(rowCount, columnCount, higherMineCount);
        } catch (Exception e) {
            // field with more mines than tiles should not be created - it may fail on exception
        }
        assertTrue((fieldWithTooManyMines == null) || (fieldWithTooManyMines.getMineCount() <= (rowCount * columnCount)));
    }

    // ... dalsie testy
}