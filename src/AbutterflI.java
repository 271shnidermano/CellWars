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
        ArrayList<Location> aliveCells = new ArrayList<>();
        ArrayList<Integer> valueAtCell = new ArrayList<>();
        aliveCells = aliveCells(grid);
        valueAtCell = valueAtCell(grid, aliveCells);
        int[][] fakeGridSource = new int[grid.getRows()][grid.getCols()];
        for (int i = 0; i < grid.getRows(); i++)
        {
            for (int j = 0; j < grid.getCols(); j++)
            {
                if (aliveCells.contains(new Location(i, j)))
                {
                    fakeGridSource[i][j] = valueAtCell.get(aliveCells.indexOf(new Location(i, j)));
                }
                else
                {
                    fakeGridSource[i][j] = -1;
                }
            }
        }

        Location bestMove = bestMove(fakeGridSource, myId);
        return bestMove;
    }
    public static ArrayList<Location> aliveCells(Grid grid)
    {
        ArrayList<Location> aliveCells = new ArrayList<>();
        for (int i = grid.getRows() - 1; i >= 0; i--)

        {
            for (int j = grid.getCols() - 1; j >= 0; j--)
            {
                if (grid.getCell(i, j) != -1)
                {
                    aliveCells.add(new Location(i, j));
                }
            }
        }
        return aliveCells;
    }
    public static ArrayList<Integer> valueAtCell(Grid grid, ArrayList<Location> aliveCells)
    {
        ArrayList<Integer> valueAtCell = new ArrayList<>();
        for (int i = 0; i < aliveCells.size(); i++)
        {
            Location loc = aliveCells.get(i);
            valueAtCell.add(grid.getCell(loc.getRow(), loc.getCol()));
        }
        return valueAtCell;
    }
    public static Location bestMove(int[][] fakeGridSource, int myId){
        Location bestMove = null;
        int bestCount = -1;
        for(int i = 0; i < fakeGridSource.length; i++)
        {
            for (int j = 0; j < fakeGridSource[i].length; j++)
            {
                if(fakeGridSource[i][j] != -1)
                {
                    continue;
                }

                fakeGridSource[i][j] = myId;
                int[][] fakeGridSourceNextGeneration = fakeGridSourceNextGeneration(fakeGridSource,myId);
                int count = 0;
                for(int k = 0; k < fakeGridSourceNextGeneration.length; k++)
                {
                    for (int l = 0; l < fakeGridSourceNextGeneration[k].length; l++)
                    {
                        if(fakeGridSourceNextGeneration[k][l] == myId)
                        {
                            count++;
                        }
                    }
                }
                if(count > bestCount)
                {
                    bestCount = count;
                    bestMove = new Location(i, j);
                }
                fakeGridSource[i][j] = -1;
            }
        }
        return bestMove;
    }
  public static int[][] fakeGridSourceNextGeneration(int[][] fakeGridSource, int myId) {
        int[][] nextGeneration = new int[fakeGridSource.length][fakeGridSource[0].length];
        int theirId = 0; 
        if(myId == 0) {
            theirId = 1;
        } else {
            theirId = 0;
        }
       for (int row = 0; row < fakeGridSource.length; row++) {
            for (int col = 0; col < fakeGridSource[0].length; col++) {
                int liveNeighborsME = neighborCountME(fakeGridSource, row, col, myId);
                int liveNeighborsTHEY = neighborCountTHEY(fakeGridSource, row, col, myId);
            if (fakeGridSource[row][col] != -1) 
            {
                    if (liveNeighborsME+liveNeighborsTHEY == 2 || liveNeighborsME+liveNeighborsTHEY == 3) 
                    {
                        if(liveNeighborsME>liveNeighborsTHEY)
                        {
                            nextGeneration[row][col] = myId;
                        }
                        else
                        {
                            nextGeneration[row][col] = theirId;
                        }
                    
                    }
                else 
                {
                    nextGeneration[row][col] = -1;
                }
            }
            else 
            {
                if (liveNeighborsME+liveNeighborsTHEY == 3) {
                        if(liveNeighborsME>liveNeighborsTHEY)
                        {
                            nextGeneration[row][col] = myId;
                        }
                        else
                        {
                            nextGeneration[row][col] = theirId;
                        }
                }
                else {
                    nextGeneration[row][col] = -1;
                }
            }
        }
        }
        return nextGeneration;
    }
public static int neighborCountME(int[][] fakeGridSource, int row, int col, int myId) {
        if (row < 0 || row >= fakeGridSource.length || col < 0 || col >= fakeGridSource[0].length) {
            throw new IllegalArgumentException("Row and column are out of bounds.");
        }

        int myCount = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) {
                    continue;
                }
                if (r >= 0 && r < fakeGridSource.length && c >= 0 && c < fakeGridSource[0].length && fakeGridSource[r][c] != -1) {
                    if (fakeGridSource[r][c] == myId) {
                        myCount++;
                    }
                }
            }
        }

        return myCount;
    }
public static int neighborCountTHEY(int[][] fakeGridSource, int row, int col, int myId) {
        if (row < 0 || row >= fakeGridSource.length || col < 0 || col >= fakeGridSource[0].length) {
            throw new IllegalArgumentException("Row and column are out of bounds.");
        }
        int theyCount = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) {
                    continue;
                }
                if (r >= 0 && r < fakeGridSource.length && c >= 0 && c < fakeGridSource[0].length && fakeGridSource[r][c] != -1) {
                    if (fakeGridSource[r][c] != myId) {
                        theyCount++;
                    }
                }
            }
        }

        return theyCount;
    }
}
