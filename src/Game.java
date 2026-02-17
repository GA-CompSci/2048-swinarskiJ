import java.util.ArrayList;
import java.util.Random;

/**
 * Game class for 2048
 * Contains all game logic and state management
 * 
 * STUDENT VERSION: Complete the TODO sections to make the game work!
 */
public class Game {
    private static final int BOARD_SIZE = 4;
    private static final int WIN_VALUE = 2048;
    
    private int[][] board;
    private int score;
    private Random random;
    private boolean hasWon;
    private boolean gameOver;
    
    /**
     * Constructor - initializes a new game
     */
    public Game() {
        random = new Random();
        resetGame();
    }
    
    /**
     * Resets the game to initial state
     */
    public void resetGame() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        score = 0;
        hasWon = false;
        gameOver = false;
        
        // Add two initial tiles
        addRandomTile();
        addRandomTile();
    }
    
    /**
     
     * Requirements:
     * - 90% chance of adding a 2
     * - 10% chance of adding a 4
     * - Should only add to empty cells
     * - Use the getEmptyCells() method to find empty positions
     * 
     * Hint: Use random.nextInt(10) < 9 for 90% probability
     */
    private void addRandomTile() {
       
        ArrayList<int[]> emptyCells = getEmptyCells(); // get empties
        if (emptyCells.isEmpty()) return; // checks if board is already full
        
        int[] spot = emptyCells.get((int)(Math.random()* emptyCells.size())); // get empty
       
        if(random.nextInt(10) < 9) board[spot[0]][spot[1]] = 2;
        else board[spot[0]][spot[1]] = 4;
        //90% for 2, 10% for 4

    }
    
    /**

     * Requirements:
     * - Return an ArrayList of int arrays [row, col] for each empty cell
     * - A cell is empty if its value is 0
     * 
     * Hint: Loop through the board and check each cell
     */
    private ArrayList<int[]> getEmptyCells() {
  
        ArrayList<int[]> emptyCells = new ArrayList<>();
       
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                if(board[row][col] == 0) emptyCells.add(new int[]{row,col});
            }
        }

        return emptyCells;
    }
    
    /**
     * Requirements:
     * - Slide all tiles to the left (remove gaps)
     * - Merge adjacent tiles with same value
     * - Each tile can only merge once per move
     * - Update score when tiles merge (add merged value to score)
     * - Add a random tile after a successful move
     * - Return true if any tiles moved, false otherwise
     * 
     * Algorithm hints:
     * 1. For each row:
     *    a. Compress tiles to the left (remove zeros)
     *    b. Merge adjacent equal tiles
     *    c. Check if the row changed
     * 2. If any row changed, add a random tile
     */
    public boolean moveLeft() {
   
        boolean moved = false;

        for(int row = 0; row < board.length; row++){
            int[] temp = new int[BOARD_SIZE];

            // SMART COPY
            int copyCount = 0;
            for(int col = 0; col < board[0].length; col++){
                if(board[row][col] != 0) temp[copyCount++] = board[row][col];
            }

            //HARD PART - MERGE + SCOOTCH
            for(int col = 0; col < board[0].length-1; col++){
                if(temp[col] == temp[col+1]){
                    temp[col] = temp[col]*2;

                    score += temp[col];
                    for(int scootch = col+1; scootch < board[0].length-1; scootch++){
                        temp[scootch] = temp[scootch+1];
                    }
                    //add zero at end
                    temp[board[0].length-1] = 0;
                }
            }

            //CHECK DIFF
            for(int col = 0; col < board[0].length; col++){
                if(temp[col] != board[row][col]){
                    moved = true;
                    board[row] = temp;
                }
            }
        }
        
        if(moved) addRandomTile();
        return moved;
    }
    
    /**

     * Requirements:
     * - Similar to moveLeft but in opposite direction
     * - Slide tiles to the right
     * - Merge from right to left
     * 
     * Hint: Process from right to left instead of left to right
     */
    public boolean moveRight() {
        boolean moved = false;

        for(int row = 0; row < board.length; row++){
            int[] temp = new int[BOARD_SIZE];

            // SMART COPY
            int copyCount = BOARD_SIZE - 1;
            for(int col = board[0].length-1; col>=0; col--){
                if(board[row][col] != 0) temp[copyCount--] = board[row][col];
            }

            //HARD PART - MERGE + SCOOTCH
            for(int col = board[0].length-1; col>0; col--){
                if(temp[col] == temp[col-1]){
                    temp[col] = temp[col]*2;

                    score += temp[col];
                    for(int scootch = col-1; scootch > board[0].length-1; scootch--){
                        temp[scootch] = temp[scootch-1];
                    }
                    //add zero at end
                    temp[0]= 0;
                }
            }

            //CHECK DIFF
            for(int col = 0; col < board[0].length; col++){
                if(temp[col] != board[row][col]){
                    moved = true;
                    board[row] = temp;
                }
            }
        }
        
        if(moved) addRandomTile();
        return moved;
    }
    
    /**
   
     * Requirements:
     * - Similar logic to moveLeft but operates on columns
     * - Slide tiles up
     * - Merge from top to bottom
     * 
     * Hint: Work with columns instead of rows
     */
    public boolean moveUp() {

        boolean moved = false;

        for (int col = 0; col < board[0].length; col++) {
            int[] temp = new int[BOARD_SIZE];

            // SMART COPY
            int copyCount = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col] != 0) temp[copyCount++] = board[row][col];
            }

            // HARD PART - MERGE + SCOOTCH
            for (int row = 0; row < board.length - 1; row++) {
                if (temp[row] == temp[row + 1]) {
                    temp[row] = temp[row] * 2;
                    score += temp[row];
                    for (int scootch = row + 1; scootch < board.length - 1; scootch++) {
                        temp[scootch] = temp[scootch + 1];
                    }
                    temp[board.length - 1] = 0;
                }
            }

            // CHECK DIFF
            for (int row = 0; row < board.length; row++) {
                if (temp[row] != board[row][col]) {
                    moved = true;
                }
                board[row][col] = temp[row];
            }
        }

        if (moved) addRandomTile();
        return moved;
    }
    
    /**
     * Requirements:
     * - Similar to moveUp but in opposite direction
     * - Slide tiles down
     * - Merge from bottom to top
     */
    public boolean moveDown() {
        boolean moved = false;

        for (int col = 0; col < board[0].length; col++) {
            int[] temp = new int[BOARD_SIZE];

            // SMART COPY
            int copyCount = 0;
            for (int row = board.length - 1; row >= 0; row--) {
                if (board[row][col] != 0) temp[copyCount++] = board[row][col];
            }

            // HARD PART - MERGE + SCOOTCH
            for (int row = 0; row < board.length - 1; row++) {
                if (temp[row] == temp[row + 1]) {
                    temp[row] = temp[row] * 2;
                    score += temp[row];
                    for (int scootch = row + 1; scootch < board.length - 1; scootch++) {
                        temp[scootch] = temp[scootch + 1];
                    }
                    temp[board.length - 1] = 0;
                }
            }

            // CHECK DIFF - write back bottom to top
            for (int row = board.length - 1; row >= 0; row--) {
                int tempIndex = board.length - 1 - row;
                if (temp[tempIndex] != board[row][col]) {
                    moved = true;
                }
                board[row][col] = temp[tempIndex];
            }
        }

        if (moved) addRandomTile();
        return moved;
    }
    
    /**

     * Requirements:
     * - Return true if any tile has value >= WIN_VALUE (2048)
     * - Once won, should continue returning true (use hasWon field)
     * 
     * Hint: Check all tiles and update the hasWon field
     */
    public boolean hasWon() {
        

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] >= WIN_VALUE) {
                    hasWon = true;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * TODO #8: Implement method to check if game is over
     * Requirements:
     * - Game is over when:
     *   1. No empty cells remain AND
     *   2. No adjacent tiles (horizontal or vertical) can be merged
     * - Update the gameOver field when game ends
     * 
     * Hint: First check for empty cells, then check all adjacent pairs
     */
    public boolean isGameOver() {
        // TODO: Complete this method
        
        return false;
    }
    
    // ===================== PROVIDED METHODS - DO NOT MODIFY =====================
    
    /**
     * Gets a copy of the current board state
     */
    public int[][] getBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    /**
     * Gets the current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the board size
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }
    
    /**
     * Helper method for debugging - prints the board to console
     */
    public void printBoard() {
        System.out.println("Score: " + score);
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf("%4d ", board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------");
    }
}