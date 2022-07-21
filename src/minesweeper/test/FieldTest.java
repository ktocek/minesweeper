package minesweeper.test;

import minesweeper.core.*;
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
    public void checkOpenClue() {
        int ranRow = randomGenerator.nextInt(rowCount);
        int ranCol = randomGenerator.nextInt(columnCount);
        int random = 0;
        boolean clueIsOpen = true;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (field.countAdjacentMines(row, col) != 0 && !(field.getTile(row, col) instanceof Mine)) {
                    field.openTile(row, col);
                    assertEquals(field.getState(), GameState.PLAYING, "GameState is not PLAYING");
                    break;
                }
            }
        }

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if(field.countAdjacentMines(row, col) == 0 && !(field.getTile(row, col) instanceof Mine)){
                    field.openTile(row, col);
                    assertEquals(field.getState(), GameState.PLAYING, "GameState is not PLAYING");
                    for (int r = 0; r < rowCount; r++) {
                        for (int c = 0; c < columnCount; c++) {
                            if(field.getTile(r, c).getState() == Tile.State.OPEN && !(field.getTile(r, c) instanceof Clue)){
                                assertTrue(clueIsOpen = false, "Not only clue is OPEN");
                                break;
                            }
                        }
                    }
                }
            }
        }

        while (random == 0) {
            if (field.getTile(ranRow, ranCol).getState() == Tile.State.CLOSED) {
                field.markTile(ranRow, ranCol);
                field.openTile(ranRow, ranCol);
                assertEquals(Tile.State.MARKED, field.getTile(ranRow, ranCol).getState(), "Tile is not marked");
                random++;
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