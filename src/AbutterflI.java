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
        Location duoMove = searchDuo(grid, myId);
        return duoMove != null ? duoMove : fallbackMove(grid);
    }
    public static Location searchDuo(Grid grid, int myId) {
        int theirId = findOpponentId(grid, myId);

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                // Horizontal pair: [r,c] and [r,c+1].
                if (c + 1 < grid.getCols()
                        && isPair(grid.getCell(r, c), grid.getCell(r, c + 1), myId, theirId)
                        && isIsolatedHorizontalPair(grid, r, c)) {
                    if (c > 0 && grid.getCell(r, c - 1) == -1) {
                        return new Location(r, c - 1);
                    }
                    if (c + 2 < grid.getCols() && grid.getCell(r, c + 2) == -1) {
                        return new Location(r, c + 2);
                    }
                }

                // Vertical pair: [r,c] and [r+1,c].
                if (r + 1 < grid.getRows()
                        && isPair(grid.getCell(r, c), grid.getCell(r + 1, c), myId, theirId)
                        && isIsolatedVerticalPair(grid, r, c)) {
                    if (r > 0 && grid.getCell(r - 1, c) == -1) {
                        return new Location(r - 1, c);
                    }
                    if (r + 2 < grid.getRows() && grid.getCell(r + 2, c) == -1) {
                        return new Location(r + 2, c);
                    }
                }
            }
        }

        return null;
    }

    /** Return a legal move when no isolated duo was found. */
    private static Location fallbackMove(Grid grid) {
        // Prefer spawning in an empty cell.
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (grid.getCell(r, c) == -1) {
                    return new Location(r, c);
                }
            }
        }

        // If the board is full, any existing cell can still be killed legally.
        if (grid.getRows() > 0 && grid.getCols() > 0) {
            return new Location(0, 0);
        }
        return null;
    }

    private static int findOpponentId(Grid grid, int myId) {
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                int cell = grid.getCell(r, c);
                if (cell != -1 && cell != myId) {
                    return cell;
                }
            }
        }
        return -1;
    }

    private static boolean isPair(int first, int second, int myId, int theirId) {
        return (first == myId && second == myId)
                || (first == myId && second == theirId)
                || (first == theirId && second == myId);
    }

    private static boolean isIsolatedHorizontalPair(Grid grid, int row, int col) {
        return surroundingCellsAreDead(grid, row - 1, row + 1, col - 1, col + 2,
                row, col, row, col + 1);
    }

    private static boolean isIsolatedVerticalPair(Grid grid, int row, int col) {
        return surroundingCellsAreDead(grid, row - 1, row + 2, col - 1, col + 1,
                row, col, row + 1, col);
    }

    private static boolean surroundingCellsAreDead(Grid grid, int firstRow, int lastRow,
            int firstCol, int lastCol, int pairRow1, int pairCol1,
            int pairRow2, int pairCol2) {
        for (int r = firstRow; r <= lastRow; r++) {
            for (int c = firstCol; c <= lastCol; c++) {
                if (r < 0 || r >= grid.getRows() || c < 0 || c >= grid.getCols()) {
                    continue;
                }
                boolean isPairCell = (r == pairRow1 && c == pairCol1)
                        || (r == pairRow2 && c == pairCol2);
                if (!isPairCell && grid.getCell(r, c) != -1) {
                    return false;
                }
            }
        }
        return true;
    }
    public static int[][] makeBoard (Grid grid, int myId) {
        int[][] fakeGrid = new int[grid.getRows()][grid.getCols()];
       for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (grid.getCell(r, c) == myId) {
                    fakeGrid[r][c] = myId;
                } else if( grid.getCell(r, c) != -1) {
                    if(myId == 0) {
                        fakeGrid[r][c] = 1;
                    } else {
                        fakeGrid[r][c] = 0;
                    }
                }
                else
                {
                    fakeGrid[r][c] = -1;
                }
            }
        }
        return fakeGrid;
    }
}