import java.util.ArrayList;
/**
 * STUDENT FILE
 *
 * Name: ______________________________
 * AI Code Name: ______________________
 *
 * Strategy Description:
 * Replace this comment with a short explanation of the strategy your AI uses.
 * Your final strategy must be fundamentally different from the sample AIs.
 */
public class AbutterflI extends CellAI {
    int myId = getID();
    @Override
    public String getAIName() {
        return "AbutterflI";
    }

    @Override
    public Location select(Grid grid) {
        /*
         * Replace this starter strategy.
         *
         * Helpful information:
         *   getID()                     -> your cell ID
         *   grid.getRows()              -> number of rows
         *   grid.getCols()              -> number of columns
         *   grid.getCell(r, c)          -> -1 if dead, otherwise an AI ID
         *   GridFunctions.getNeighbors  -> number of living neighbors
         *   GridFunctions.mostCommonNeighbor -> most common neighboring AI
         *   randomInt(bound)            -> reproducible random integer
         */
        return null;
    }
    public static int[][] simulateNextTurn(Grid grid) {
        int[][] fakeGrid = new int[grid.getRows()][grid.getCols()];
    return null;
    }
    

    /**
     * Advances the entire society by one generation.
     *
     * Rules:
     * 1. A dead cell with exactly 3 live neighbors becomes alive.
     * 2. A live cell with 2 or 3 live neighbors survives.
     * 3. A live cell with fewer than 2 neighbors dies from isolation.
     * 4. A live cell with more than 3 neighbors dies from overpopulation.
     *
     * TODO: Complete this method.
     */
    public static int[][] update(int[][] society) {
        // TODO: Create a SECOND 2D boolean array for the next generation.
        //
        // IMPORTANT:
        // Do not change society while you are still using it to calculate
        // neighbors. Every cell in the new generation must be based on the
        // same old generation.
        int[][] nextGeneration = new int[society.length][society[0].length];

       for (int row = 0; row < society.length; row++) {
         for (int col = 0; col < society[0].length; col++) {
            int liveNeighbors = neighborCount(row, col, society);
            if (society[row][col] != -1) {
                if (liveNeighbors == 2 || liveNeighbors == 3) {
                    nextGeneration[row][col] = society[row][col];
                }
                else {
                    nextGeneration[row][col] = -1;
                }
            }
            else {
                if (liveNeighbors == 3) {
                    nextGeneration[row][col] = true;
                }
                else {
                    nextGeneration[row][col] = false;
                }
            }
        }
    }

        society = nextGeneration;
    }

public static int neighborCount(int row, int col, int[][] society) {
        if (row < 0 || row >= society.length || col < 0 || col >= society[0].length) {
            throw new IllegalArgumentException("Row and column are out of bounds.");
        }

        int count = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) {
                    continue;
                }
                if (r >= 0 && r < society.length && c >= 0 && c < society[0].length && society[r][c]!=-1) {
                    count++;
                }
            }
        }

        return count;
    }
}