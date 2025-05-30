
public class Board  {


    private int rows;
    private int cols;
    
    /** The grid of pieces */
    private Player[][] grid;
    
    

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        grid = new Player[rows][cols];
        // set each cell of the board to null (empty).
        reset();

    }
    
    public void reset() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = null;
            }
        }
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    
    /**
    * Returns the Player whose piece occupies the given location, 
    * @param row int
    * @param col int
    */
    public Player getCell(int row, int col ) throws IndexOutOfBoundsException{
        if( (row < 0) || (col < 0) || (row >= rows) || (col >= cols) ) {
            throw new IndexOutOfBoundsException();
        } else {
            return grid[row][col];
        }
    }
    
    //returns true if there are no more plays left
    public boolean boardFilled(){
        for (Player[] players : grid) {
            for (Player player : players) {
                if (player == null)
                    return false;
            }
        }
        return true;
    }

    // Returns true if move is possible given board state.  
    public boolean possibleMove(Move move) {
        int column = move.getColumn();
        for (Player[] players : grid) {
            if (players[column] == null)
                return true;
        }
        return false;
    }
    
    // Adds a piece to the board for a given Move
    public void addPiece(Move move) {
        if (possibleMove(move)) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][move.getColumn()] == null){
                    grid[i][move.getColumn()] = move.getPlayer();
                    break;
                }
            }
        }

    }

    // if the board contains a winning position, returns the Player that wins.
    // Otherwise, returns null.  You could ignore lastMove. 
    // I made this way too complicated
    public Player winner(Move lastMove) {
        Player columns = checkColumns(lastMove);
        Player rows = checkRows(lastMove);
        Player diagonals = checkDiagonals(lastMove);
        if (columns != null)
            return columns;
        else if (rows != null)
            return rows;
        else if (diagonals != null)
            return diagonals;
        else 
            return null;
    }

    public Player checkColumns(Move lastMove){
        for (int column = 0; column < grid[0].length; column++) {
            String playerName = "";
            int count = 0;
            for (Player[] players : grid) {
                if (players[column] != null){
                    if (playerName == "" || !(players[column].getName().equals(playerName))){
                        playerName = players[column].getName();
                        count = 1;
                    } else{
                        count++;
                        if (count >= 4)
                            return players[column];
                    }
                } else{
                    count = 0;
                    playerName = "";
                }
            }
        }
        return null;
    }
    
    public Player checkRows(Move lastMove){
        for (int row = 0; row < grid.length; row++) {
            String playerName = "";
            int count = 0;
            for (Player player : grid[row]) {
                if (player != null){
                    if (playerName == "" || !(player.getName().equals(playerName))){
                        playerName = player.getName();
                        count = 1;
                    } else{
                        count++;
                        if (count >= 4)
                            return player;
                    }
                } else{
                    count = 0;
                    playerName = "";
                }
            }
        }
        return null;
    }

    public Player checkDiagonals(Move lastMove){
        // Check Up-Right Diagonals
        for (int row = 0; row < 3; row++) {
            int colStop = 1;
            if (row == 0)
                colStop = 4;
            for (int c = 0; c < colStop; c+=0) {
                int start = c;
                String playerName = "";
                int count = 0;
                for (int r = row; r < grid.length && c < grid[r].length; r++) {
                    if (grid[r][c] != null){
                        if (playerName == "" || !(grid[r][c].getName().equals(playerName))){
                            playerName = grid[r][c].getName();
                            count = 1;
                        } else{
                            count++;
                            if (count >= 4)
                                return grid[r][c];
                        }
                    }
                    c++;
                }
                c = ++start;
            }
        }

        // Check Down-Right Diagonals
        for (int row = grid.length-1; row >= 3; row--) {
            int colStop = 1;
            if (row == grid.length-1)
                colStop = 4;
            for (int c = 0; c < colStop; c+=0) {
                int start = c;
                String playerName = "";
                int count = 0;
                for (int r = row; r >= 0 && c < grid[r].length; r--) {
                    if (grid[r][c] != null){
                        if (playerName == "" || !(grid[r][c].getName().equals(playerName))){
                            playerName = grid[r][c].getName();
                            count = 1;
                        } else{
                            count++;
                            if (count >= 4)
                                return grid[r][c];
                        }
                    }
                    c++;
                }
                c = ++start;
            }
        }

        return null;
    }
    
} // end Board class
