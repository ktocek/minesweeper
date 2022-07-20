package minesweeper.core;

import minesweeper.Minesweeper;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;
    private final Mine mine = new Mine();

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        if (mineCount > rowCount * columnCount){
            throw new ArithmeticException("To many mines");
        }else {
            this.rowCount = rowCount;
            this.columnCount = columnCount;
            this.mineCount = mineCount;
            tiles = new Tile[rowCount][columnCount];

            //generate the field content
            generate();
        }
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

    public GameState getState() {
        return state;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {

        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);

            if (tile instanceof Clue) {
                Clue c = (Clue)tile;
                if(c.getValue() == 0) {
                    openAdjacentTiles(row, column);
                }
            }

            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        if (tiles[row][column].getState() == Tile.State.MARKED) {
            tiles[row][column].setState(Tile.State.CLOSED);
        } else {
            tiles[row][column].setState(Tile.State.MARKED);
        }
    }

    /**
     * Generates playing field.
     */

    private void generate() {
        //throw new UnsupportedOperationException("Method generate not yet implemented");
        Random r = new Random();
        int i = 0;

        while (i < mineCount) {
            int row = r.nextInt(rowCount);
            int col = r.nextInt(columnCount);

            if (getTile(row, col) == null) {
                tiles[row][col] = mine;
                i++;
            }

        }
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (getTile(row, col) == null) {
                    tiles[row][col] = new Clue(countAdjacentMines(row, col));
                }
            }
        }


    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        return (rowCount * columnCount) - getNumberOf(Tile.State.OPEN) == mineCount;
    }

    private int getNumberOf(Tile.State state) {
        int numberOf = 0;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (tiles[row][col].getState() == state) {
                    numberOf++;
                }
            }
        }

        return numberOf;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    public int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public void openAdjacentTiles(int row, int column) {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                                openTile(actRow, actColumn);
                    }
                }
            }
        }
    }

}
