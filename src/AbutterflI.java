/**
 * STUDENT FILE
 *
 * Name: ______________________________
 * AI Code Name: ______________________
 *
 * Strategy Description:
 * This AI uses an attack-scoring strategy. Instead of looking for one specific
 * formation, it evaluates every empty cell and scores it based on how many
 * opponent cells surround it, how strongly connected the opponent is, and
 * whether my own cells can support the attack. It chooses the highest-scoring
 * location.
 */
public class AbutterflI extends CellAI {

    int myId = getID();

    @Override
    public String getAIName() {
        return "AbutterflI";
    }

    @Override
    public Location select(Grid grid) {
        int opponent = findOpponentId(grid, myId);

        Location bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (grid.getCell(r, c) != -1) {
                    int score = scoreMove(grid, r, c, opponent, myId);

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Location(r, c);
                    }
                }
            }
        }
        if(bestMove!=null)
        {
                if (bestMove.getRow() > 0
                    && bestMove.getRow() < grid.getRows() - 1
                    && bestMove.getCol() > 0
                    && bestMove.getCol() < grid.getCols() - 1)
            {
                int row = bestMove.getRow();
                int col = bestMove.getCol();
                if (grid.getCell(row + 1, col) == opponent
                    && grid.getCell(row + 1, col + 1) == opponent
                    && grid.getCell(row, col + 1) == opponent)
                {
                    bestMove=null;
                }
                if(grid.getCell(row-1,col)==opponent&&grid.getCell(row-1,col+1)==opponent&&grid.getCell(row,col+1)==opponent)
                {
                    bestMove=null;
                }
                if(grid.getCell(row-1,col)==opponent&&grid.getCell(row-1,col-1)==opponent&&grid.getCell(row,col-1)==opponent)
                {
                    bestMove=null;
                }
                if(grid.getCell(row+1,col)==opponent&&grid.getCell(row+1,col-1)==opponent&&grid.getCell(row,col-1)==opponent)
                {
                    bestMove=null;
                }

            }
        }
        if (bestMove == null) {
            bestMove = findIsolatedTriple(grid, opponent);
        }
        if (bestMove == null) {
            bestMove = findIsolated2x2(grid, myId);
        }
        if (bestMove == null) {
            bestMove = findIsolatedHive(grid, opponent);
        }
        if (bestMove == null) {
            bestMove = findIsolatedHivePlus(grid, opponent);
        }
        if (bestMove == null) {
                for (int r = 0; r < grid.getRows(); r++) {
                    for (int c = 0; c < grid.getCols(); c++) {
                        if (grid.getCell(r, c) == opponent) {
                           bestMove = lastResortSquare(grid, opponent);
                        }
                    }
                }
        }
        if (bestMove == null) {
                for (int r = 0; r < grid.getRows(); r++) {
                    for (int c = 0; c < grid.getCols(); c++) {
                        if (grid.getCell(r, c) == opponent) {
                            return new Location(r, c);
                        }
                    }
                }
        }
        return bestMove;
    }
    public static Location lastResortSquare(Grid grid, int opponentId) {
        for (int r = 0; r <= grid.getRows() - 2; r++) {
            for (int c = 0; c <= grid.getCols() - 2; c++) {
                if (grid.getCell(r, c) == opponentId
                    && grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 1) == opponentId) {
                   int[][] cornerOffsets = {{ -1, -1 },{ -1, 2 },{ 2, -1 },{ 2, 2 }};

                for (int[] offset : cornerOffsets) {
                    int r1 = r + offset[0];
                    int c1 = c + offset[1];
                    if (r1 >= 0 && r1 < grid.getRows() && c1 >= 0 && c1 < grid.getCols()
                            && grid.getCell(r1, c1) == -1) {
                        return new Location(r1, c1);
                    }
                }
                }
            }
        }
        return null;
    }
    public static Location findIsolatedHivePlus(Grid grid, int opponentId) {
    for (int r = 0; r < grid.getRows() - 2; r++) {
        for (int c = 0; c < grid.getCols() - 2; c++) {

            //look at all orientations
            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && grid.getCell(r + 2, c + 2) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c) == opponentId
                    && grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 1) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && grid.getCell(r + 2, c + 2) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }

            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && grid.getCell(r + 2, c) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 1) == opponentId
                    && grid.getCell(r + 2, c) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c) == opponentId
                    && grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
            if (grid.getCell(r, c + 1) == opponentId
                    && grid.getCell(r + 1, c) == opponentId
                    && grid.getCell(r + 1, c + 1) == opponentId
                    && grid.getCell(r + 1, c + 2) == opponentId
                    && grid.getCell(r + 2, c + 1) == opponentId
                    && isIsolatedHivePlus(grid, r, c)) {

                Location extra = findExtraCell(grid, r, c);
                if (extra != null) {
                    return extra;
                }
            }
        }
    }

    return null;
}
public static boolean isIsolatedHivePlus(Grid grid, int startRow, int startCol) {
    int liveCellsInShape = 0;

    for (int row = startRow; row <= startRow + 2; row++) {
        for (int col = startCol; col <= startCol + 2; col++) {
            if (grid.getCell(row, col) != -1) {
                liveCellsInShape++;
            }
        }
    }

    if (liveCellsInShape != 5) {
        return false;
    }

    for (int row = startRow - 1; row <= startRow + 3; row++) {
        for (int col = startCol - 1; col <= startCol + 3; col++) {
            if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getCols()) {
                continue;
            }

            boolean insideShape = (row >= startRow
                    && row <= startRow + 2
                    && col >= startCol
                    && col <= startCol + 2);
            if (!insideShape && grid.getCell(row, col) != -1) {
                return false;
            }
        }
    }

    return true;
}

public static Location findExtraCell(Grid grid, int r, int c) {

    for (int row = r - 1; row <= r + 3; row++) {
        for (int col = c - 1; col <= c + 3; col++) {
            if (row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getCols()
                    && grid.getCell(row, col) == -1) {
                return new Location(row, col);
            }
        }
    }

    return null;
}


    public static Location findIsolatedHive(Grid grid, int opponentId) {
        for (int r = 1; r < grid.getRows() - 1; r++) {
            for (int c = 1; c < grid.getCols() - 1; c++) {
                if (grid.getCell(r, c) == -1
                        && grid.getCell(r - 1, c) == opponentId
                        && grid.getCell(r + 1, c) == opponentId
                        && grid.getCell(r, c - 1) == opponentId
                        && grid.getCell(r, c + 1) == opponentId
                        && isIsolatedHive(grid, r, c)) {
                    return new Location(r - 1, c);
                }
            }
        }
        return null;
    }

    public static boolean isIsolatedHive(Grid grid, int centerRow, int centerCol) {
        for (int r = centerRow - 1; r <= centerRow + 1; r++) {
            for (int c = centerCol - 1; c <= centerCol + 1; c++) {
                if (r < 0 || r >= grid.getRows() || c < 0 || c >= grid.getCols()) {
                        continue;
                    }

                    boolean isPartOfPlus = (r == centerRow && c == centerCol)
                        || (r == centerRow - 1 && c == centerCol)
                        || (r == centerRow + 1 && c == centerCol)
                        || (r == centerRow && c == centerCol - 1)
                        || (r == centerRow && c == centerCol + 1);

                if (!isPartOfPlus && grid.getCell(r, c) != -1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static Location findIsolatedTriple(Grid grid, int opponentId) {
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c + 2 < grid.getCols(); c++) {
                if (grid.getCell(r, c) == opponentId
                        && grid.getCell(r, c + 1) == opponentId
                        && grid.getCell(r, c + 2) == opponentId
                        && isIsolatedTriple(grid, r, c, true)) {
                    return new Location(r, c + 1);
                }
            }
        }

        for (int r = 0; r + 2 < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (grid.getCell(r, c) == opponentId
                        && grid.getCell(r + 1, c) == opponentId
                        && grid.getCell(r + 2, c) == opponentId
                        && isIsolatedTriple(grid, r, c, false)) {
                    return new Location(r + 1, c);
                }
            }
        }

        return null;
    }

    public static boolean isIsolatedTriple(Grid grid, int startRow, int startCol, boolean horizontal) {
        int rowMin = startRow - 1;
        int colMin = startCol - 1;

        int rowMax;
        int colMax;

        if (horizontal) {
            rowMax = startRow + 1;
            colMax = startCol + 3;
        } else {
            rowMax = startRow + 3;
            colMax = startCol + 1;
        }

        for (int r = rowMin; r <= rowMax; r++) {
            for (int c = colMin; c <= colMax; c++) {
                if (r < 0 || r >= grid.getRows() || c < 0 || c >= grid.getCols()) {
                    continue;
                }

                boolean isPartOfTriple = false;
                if (horizontal) {
                    isPartOfTriple = (r == startRow && (c == startCol || c == startCol + 1 || c == startCol + 2));
                } else {
                    isPartOfTriple = (c == startCol && (r == startRow || r == startRow + 1 || r == startRow + 2));
                }

                if (!isPartOfTriple && grid.getCell(r, c) != -1) {
                    return false;
                }
            }
        }

        return true;
    }


    public static Location findIsolated2x2(Grid grid, int myId) {
        for (int r = 0; r + 1 < grid.getRows(); r++) {
            for (int c = 0; c + 1 < grid.getCols(); c++) {
                int a = grid.getCell(r, c);
                int b = grid.getCell(r, c + 1);
                int d = grid.getCell(r + 1, c);
                int e = grid.getCell(r + 1, c + 1);

                if (is2x2Pattern(a, b, d, e, myId) && isIsolated2x2(grid, r, c)) {
                    int[] vals = { a, b, d, e };
                    for (int row = r; row <= r + 1; row++) {
                        for (int col = c; col <= c + 1; col++) {
                            int cell = grid.getCell(row, col);
                            if (cell != -1 && cell != myId) {
                                return new Location(row, col);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean is2x2Pattern(int a, int b, int d, int e, int myId) {
        int opponent = -1;
        int[] vals = { a, b, d, e };

        for (int v : vals) {
            if (v != -1 && v != myId) {
                opponent = v;
                break;
            }
        }

        if (opponent == -1) {
            return false;
        }

        boolean p1 = (a == myId && b == myId && d == opponent && e == opponent);
        boolean p2 = (a == opponent && b == opponent && d == myId && e == myId);
        boolean p3 = (a == myId && b == opponent && d == myId && e == opponent);
        boolean p4 = (a == opponent && b == myId && d == opponent && e == myId);
        boolean p5 = (a == opponent && b == myId && d == myId && e == opponent);
        boolean p6 = (a == myId && b == opponent && d == opponent && e == myId);

        return p1 || p2 || p3 || p4||p5||p6;
    }

    private static boolean isIsolated2x2(Grid grid, int startRow, int startCol) {
        for (int r = startRow - 1; r <= startRow + 2; r++) {
            for (int c = startCol - 1; c <= startCol + 2; c++) {
                if (r < 0 || r >= grid.getRows() || c < 0 || c >= grid.getCols()) {
                    continue;
                }

                boolean insideSquare = (r >= startRow && r <= startRow + 1)
                        && (c >= startCol && c <= startCol + 1);

                if (!insideSquare && grid.getCell(r, c) != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int scoreMove(Grid grid, int row, int col, int opponent, int myId) {
        int score = 0;

        int enemyNeighbors = 0;
        int myNeighbors = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r < 0 || r >= grid.getRows() || c < 0 || c >= grid.getCols()) {
                    continue;
                }

                int cell = grid.getCell(r, c);

                if (cell == opponent) {
                    enemyNeighbors++;
                } else if (cell == myId) {
                    myNeighbors++;
                }
            }
        }

        score += enemyNeighbors * 20;
        score += myNeighbors * 5;
        if (enemyNeighbors + myNeighbors >= 2) {
            score += 8;
        }
        if (enemyNeighbors >= 3) {
            score += 25;
        }
        if (enemyNeighbors >= 5) {
            score += 30;
        }
        if (enemyNeighbors == 0 && myNeighbors == 0) {
            score -= 15;
        }

        int centerRow = grid.getRows() / 2;
        int centerCol = grid.getCols() / 2;
        int distance = Math.abs(row - centerRow) + Math.abs(col - centerCol);

        score -= distance;
        //just trying to add in that randomness idek atp
        score += Math.random() * 3;

        return score;
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
}