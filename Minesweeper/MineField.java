// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA3
// Fall 2018

import java.util.Random;

/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   
	// <put instance variables here>
	private int numRows; 
	// the number of rows in the field
	private int numCols;		
	// the number of columns in the field
	private int numMines;	
	// the number of mines in this minefield
	private boolean[][] mineData;
	// the 2D boolean array to record whether there is a mine at the exact location
	// true --> a mine, false --> no mine

   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
    * @param mineData the data for the mines; must have at least one row and one col.
    */
   public MineField(boolean[][] mineData) {
	   
	   // Based on the parameter, initializes the data of minefield including numRows, numCols, mineData.
	   // Because the parameter (mineData) has at least one row and one column, so we can initialize numRows 
	   // and numCols like below
	   numRows = mineData.length;
	   numCols = mineData[0].length;
	   this.mineData = mineData; 

	   // Gets the number of mines in the parameter (mineData)
	   for (int i = 0 ; i < numRows ; i ++) {
		   for (int j = 0 ; j < numCols ; j ++) { 
			   if (mineData[i][j] == true) { 
				   numMines ++;
			   }   
		   }   
	   }
	   
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
	   
	   // Initializes the minefield with the fixed data
	   this.numRows = numRows;
	   this.numCols = numCols;
	   this.numMines = numMines;
	   
	   // Initializes the minData. When constructs, there is no mine in the field.
	   // The default value of every element of 2D boolean array is false
	   this.mineData = new boolean[numRows][numCols];
	   
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   public void populateMineField(int row, int col) {
      
	   // Removes any current mines on the minefield
	   resetEmpty();
	   
	   Random random = new Random();
	   // Generates the location to place a mine randomly
	   int rowOfMine = 0;
	   // the row of location to place a mine
	   int colOfMine = 0;
	   // the column of location to place a mine
	   int countMines = 0;
	   // the number of mines which have been placed
	   
	   // Begins placing mines until we have placed all mines (numMines)
	   while (countMines < numMines) {
		   
		   // Generates the row and column randomly
		   rowOfMine = random.nextInt(numRows);
		   colOfMine = random.nextInt(numCols);
		   
		   // There are two cases we cannot place the mine:
		   // 1. We cannot place the mine at location with the parameters (row, col).
		   // 2. This square already has a mine.
		   if (((row == rowOfMine) && (col == colOfMine)) || hasMine(rowOfMine, colOfMine)) {
			   continue;
		   }
		   else {
			   // Places a mine
			   mineData[rowOfMine][colOfMine] = true;
			   countMines ++;
		   }
		   
	   }
	   
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state the minefield is in at the beginning of a game.
    */
   public void resetEmpty() {
      
	   // Resets the minefield to all empty squares, which means there is no mine in the field.
	   for (int i = 0 ; i < numRows ; i ++) {
		   for (int j = 0 ; j < numCols ; j ++) {
			   mineData[i][j] = false;
		   }
	   }
	   
   }

   
  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      
	   int numAdj = 0;
	   // the number of mines adjacent to (row, col)
	   
	   // four directions of (row, col)
	   int up = row - 1;
	   int down = row + 1;
	   int left = col - 1;
	   int right = col + 1;
	   
	   // Checks 8 adjacent squares, and gets the number of mines adjacent to (row, col)
	   if (inRange(up, left) && hasMine(up, left)) { numAdj ++; }
	   if (inRange(up, col) && hasMine(up, col)) { numAdj ++; }
	   if (inRange(up, right) && hasMine(up, right)) { numAdj ++; }
	   if (inRange(row, left) && hasMine(row, left)) { numAdj ++; }
	   if (inRange(row, right) && hasMine(row, right)) { numAdj ++; }
	   if (inRange(down, left) && hasMine(down, left)) { numAdj ++; }
	   if (inRange(down, col) && hasMine(down, col)) { numAdj ++; }
	   if (inRange(down, right) && hasMine(down, right)) { numAdj ++; }
	   
	   return numAdj;
	   
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
	   
	   // Row cannot be < 0 or >= numRows
	   // The valid range is [0, numRows - 1]
	   if (row < 0 || row >= numRows) { return false; }
	   
	   // Column cannot be < 0 or >= numCols
	   // The valid range is [0, numCols - 1]
	   if (col < 0 || col >= numCols) { return false; }
	   
	   return true;
      
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return numRows;       
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */    
   public int numCols() {
      return numCols;
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {
      return mineData[row][col];
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return
    */
   public int numMines() {
      return numMines;
   }

   
   // <put private methods here>
   
}

