package com.example.minesweepertsifx;
import static java.lang.Math.max;
import static java.lang.Math.random;

public class MinesweeperGrid {

    int rows = 1;
    int cols = 1;
    int mines = 0;
    int plantedFlags = 0 ;
    boolean gameLost = false ;
    boolean gameWon = false ;
    boolean populatedMines = false ;

    MinesweeperSquare[][] grid;

    // Constructor
    public MinesweeperGrid (int r, int c, int m) {
        this.rows = r;
        this.cols = c;
        this.mines = m;
        grid = new MinesweeperSquare[this.rows][this.cols] ;
        // Iterate over all rows
        for (int row = 0 ; row < rows ; ++row) {
            // Iterate over all columns
            for (int col = 0 ; col < cols ; ++col) {
                grid[row][col] = new MinesweeperSquare() ;
            }
        }
    }

    // Methods
    public void populateMines() {
        int minesAdded = 0 ;
        while (minesAdded < this.mines) {
            int newMineRow = (int) Math.round(Math.random() * (this.rows - 1));
            int newMineCol = (int) Math.round(Math.random() * (this.cols - 1));
            if (grid[newMineRow][newMineCol].isMine == false) {
                grid[newMineRow][newMineCol].isMine = true;
                ++minesAdded;
            }
        }
    }
    public void populateMinesExcluding(int exclRow, int exclCol) {
        int minesAdded = 0 ;
        int cntr = 0 ;
        grid[exclRow][exclCol].isMine = true ;

        while (minesAdded < this.mines) {
            cntr++;
            int newMineRow = (int) Math.round(Math.random() * (this.rows - 1));
            int newMineCol = (int) Math.round(Math.random() * (this.cols - 1));
            if (grid[newMineRow][newMineCol].isMine == false) {
                grid[newMineRow][newMineCol].isMine = true;
                ++minesAdded;
            }
        }
        grid[exclRow][exclCol].isMine = false ;
    }
    public void printGrid () {
        // Print Header
        System.out.println("Mines: " + this.mines + ", Planted Flags: " + plantedFlags);

        // Print horizontal co-ord
        if (rows > 9) { System.out.print(" "); }
        System.out.print("  ");
        for (int col = 0 ; col < cols ; ++col) {
            if (col < 10) { System.out.print(" " + col + " ") ; }
            else { System.out.print(col + " "); }
        }
        System.out.println();


        // Iterate over all rows
        for (int row = 0 ; row < rows ; ++row) {
            // Print vertical co-ord
            if (rows > 9 && row < 10) { System.out.print(" " + row + " ");}
            else { System.out.print(row + " "); }

            // Iterate over all columns
            for (int col = 0 ; col < cols ; ++col) {
                System.out.print(" " + grid[row][col].flag + " ");
            }
            System.out.println();
        }
        // Print footer
        System.out.println("Use \"row col r\" to reveal, use \"row col f\" to plant a flag.");

    }
    public void printSolution () {
        // Print Header
        System.out.println("This is the solution.\nMines: " + this.mines);

        // Print horizontal co-ord
        System.out.print("  ");
        for (int col = 0 ; col < cols ; ++col) {
            System.out.print(" " + col + " ") ;
        }
        System.out.println();


        // Iterate over all rows
        for (int row = 0 ; row < rows ; ++row) {
            // Print vertical co-ord
            System.out.print(row + " ");

            // Iterate over all columns
            for (int col = 0 ; col < cols ; ++col) {
                if (grid[row][col].isMine == true) { System.out.print(" Q "); }
                else { System.out.print("   "); }
            }
            System.out.println();
        }
    }
    public void click(int row, int col, boolean plantFlag) {
        // Are we simply marking a flag?
        if (plantFlag == true) {
            if (this.grid[row][col].flag == 'P') {
                this.grid[row][col].flag = '.' ;
                plantedFlags--;
            } else if (this.grid[row][col].flag == '.') {
                this.grid[row][col].flag = 'P' ;
                plantedFlags++;
            }
        }
        // Are we clicking for real?
        else {
            // if it's the first click, populate the mines
            if (populatedMines == false) {
                this.populateMinesExcluding(row, col);
                populatedMines = true ;
            }

            // Check if it is a mine
            if (this.grid[row][col].isMine == true) {
                this.grid[row][col].flag = 'Q' ;
                gameLost = true ;
            } else {
                int adjMines = this.countAdjacentMines(row, col) ;
                if (adjMines == 0) { this.grid[row][col].flag = ' ' ; }
                else {this.grid[row][col].flag = (char)(adjMines + '0');}
                if (adjMines == 0) {
                    // Click all adjacent
                    int startRow = Math.max(0, row-1);
                    int   endRow = Math.min(this.rows-1, row+1);
                    int startCol = Math.max(0, col-1);
                    int   endCol = Math.min(this.cols-1, col+1);

                    // Iterate over all columns
                    for (int curRow = startRow ; curRow <= endRow ; ++curRow) {
                        // Iterate over all columns
                        for (int curCol = startCol ; curCol <= endCol ; ++curCol) {
                            if (grid[curRow][curCol].flag == '.') {
                                this.click(curRow, curCol, false);
                            }
                        }
                    }
                }
            }
        }
    }
    public int countAdjacentMines(int row, int col) {
        int numMines = 0;
        int startRow = Math.max(0, row-1);
        int   endRow = Math.min(this.rows-1, row+1);
        int startCol = Math.max(0, col-1);
        int   endCol = Math.min(this.cols-1, col+1);

        // Iterate over all columns
        for (int curRow = startRow ; curRow <= endRow ; ++curRow) {
            // Iterate over all columns
            for (int curCol = startCol ; curCol <= endCol ; ++curCol) {
                if (grid[curRow][curCol].isMine == true) { numMines++; }
            }
        }
        return numMines;

    }
    public boolean checkWinCondition() {
        // if every safe square has been tested then you win
        // iterate over the rows

        boolean gameWin = true ;

        for (int row = 0 ; row < rows ; ++row) {
            // Iterate over all columns
            for (int col = 0 ; col < cols ; ++col) {
                // check condition
                if (this.grid[row][col].flag == '.' && this.grid[row][col].isMine == false) {gameWin = false ;}
            }
        }
        return gameWin ;
    }

    public MinesweeperSquare[][] getGrid() {
        return grid;
    }
}














