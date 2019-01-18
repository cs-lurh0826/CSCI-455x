// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA3
// Fall 2018


/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
  user can see about the minefield), Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to moves the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms
  the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
  outside this class via the getMineField accessor.  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MAX_ADJ_MINES = 8;	// the maximum number of adjacent mines 
   public static final int MIN_ADJ_MINES = 0;	// the minimum number of adjacent mines
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   // <put instance variables here>
   private MineField mineField;
   // the MineField object which has all information of the minefield
   private int[][] status;
   // the 2D int array to represent the status of every location in the minefield
   private static final int UNCOVERED_STATES = 0;
   // the value of the bound between uncovered states and covered states
   // If >= UNCOVERED_STATES, uncovered; otherwise, covered
 

   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      
	   // Initializes the MineField object we use in the visible field
	   this.mineField = mineField;
	   status = new int[mineField.numRows()][mineField.numCols()];
	   
	   // At the beginning of minesweeper, each square is covered.
	   for (int i = 0 ; i < mineField.numRows() ; i ++) {
		   for (int j = 0 ; j < mineField.numCols() ; j ++) {
			   status[i][j] = COVERED;
		   }
	   }
	   
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying MineField. 
   */     
   public void resetGameDisplay() {
	   
	   // Resets the status of each location, which means each square is covered.
	   for (int i = 0 ; i < mineField.numRows() ; i ++) {
		   for (int j = 0 ; j < mineField.numCols() ; j ++) {
			   status[i][j] = COVERED;
		   }
	   }
	   
   }
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {
      return mineField;
   }
   
   
   /**
      get the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the beginning of the class
      for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      return status[row][col];
   }

   
   /**
      Return the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  So the value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
	   
	  // the number of squares whose status are guessed
	  int guessNum = 0;
	  
	  // Gets the number of squares whose status are guessed 
	  for (int i = 0 ; i < mineField.numRows() ; i ++) {
		  for (int j = 0 ; j < mineField.numCols() ; j ++) {
			  if (getStatus(i, j) == MINE_GUESS) {
				  guessNum ++;
			  }
		  }
	  }
	  
	  // Returns the number of mines left to guess
      return (mineField.numMines() - guessNum);

   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      
	   // Changes the status of the square according to the following sequence:
	   // COVERED --> MINE_GUESS --> QUESTION --> COVERED
	   if (getStatus(row, col) == COVERED) {
		   status[row][col] = MINE_GUESS;
	   }
	   else if (getStatus(row, col) == MINE_GUESS) {
		   status[row][col] = QUESTION;
	   }
	   else if (getStatus(row, col) == QUESTION) {
		   status[row][col] = COVERED;
	   } 
	   else {
		   return;	// Calls on an uncovered square has no effect.
	   }
	   
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
	    
	   // When we uncover one square, there are two possible results:
	   // 1. If there is a mine, updates the status of this square which represents the failure
	   // of the minesweeper.
	   // 2. If there is no mine, updates the status of squares, and the minesweeper does not finish
	   // until the game wins or loses.
	   if (mineField.hasMine(row, col)) {
		   // result 1:
		   status[row][col] = EXPLODED_MINE;
		   return false;
	   } 
	   else {
		   // result 2:
		   updateStatus(row, col);
		   return true;
	   }
	  
   }
 
   
   /**
      Returns whether the game is over.
      @return whether game over
    */
   public boolean isGameOver() {
	   
	   // isGameLose: true --> we lose
	   // isGameWin: true --> we win
	   boolean isGameLose = false;
	   boolean isGameWin = false;
	   
	   int countUncov = 0;
	   // the number of squares in uncovered status except for EXPLODED_MINE
	   int noMineSpace = mineField.numRows() * mineField.numCols() - mineField.numMines();
	   // the number of squares which have no mines
	   
	   // If there is a square whose status is EXPLODED_MINE which means we have uncovered
	   // a mine, we lose; otherwise, if the status of the square is uncovered, countUncov 
	   // plus 1.
	  for (int i = 0 ; i < mineField.numRows() ; i ++) {
		  for (int j = 0 ; j < mineField.numCols() ; j ++) {
			  if (status[i][j] == EXPLODED_MINE) {
				  isGameLose = true;
				  break;
			  }
			  else if (isUncovered(i, j)) {
				  countUncov ++;
			  }
		  } 
	  }
	  
	  // If we have uncovered all the squares which have no mines, we win.
	  if (countUncov == noMineSpace) {
		  isGameWin = true;
	  }
	  
	  // If game is over, updates all the final status.
	  gameIsOver(isGameWin, isGameLose);
	  
	  // Game is over when we lose or win
	  return isGameLose || isGameWin;

   }
 
   
   /**
      Return whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
	   
	   // true --> uncovered, false --> covered
	   boolean isUncovered = false;
	   
	   // Judges whether this square has been uncovered.
	   if (getStatus(row, col) >= UNCOVERED_STATES) {
		   isUncovered = true;
	   }
	   
	   return isUncovered;
	   
   }
   
 
   // <put private methods here>
   
   /**
    * Uncovers this square, and if there is no mine, updates the status of this square. It also uncovers all
    * the squares in the neighboring area that are also not next to any mines. It may uncover a large region.
    * Any mine-adjacent squares we reach will be uncovered, and form the boundary of this region. 
    * 
    * There are four cases we need to keep searching through or stop searching:
	* 1. The location is invalid --> inRange(row, col) is false
	* 2. The status of the square is MINE_GUESS
	* 3. The square is uncovered
	* 4. This square has adjacent mine(s)
	* 
    * @param row  of the square
    * @param col  of the square
    */
   private void updateStatus(int row, int col) {
	   
	   // case 1, case 2 and case 3:
	   if ((!getMineField().inRange(row, col)) || isUncovered(row, col) || 
			   status[row][col] == MINE_GUESS ) {
		   return;
	   }
	   
	   if (mineField.numAdjacentMines(row, col) == MIN_ADJ_MINES) {
		   status[row][col] = MIN_ADJ_MINES;
		   // If this square has no adjacent mines, keep searching the squares in the neighboring area 
		   // until we meet one of four stopping cases.
		   updateStatus(row - 1, col - 1);
		   updateStatus(row - 1, col);
		   updateStatus(row - 1, col + 1);
		   updateStatus(row, col - 1);
		   updateStatus(row, col + 1);
		   updateStatus(row + 1, col - 1);
		   updateStatus(row + 1, col);
		   updateStatus(row + 1, col + 1);
	   }
	   else {
		   // case 4:
		   status[row][col] = mineField.numAdjacentMines(row, col);
		   return;
	   }
	   
   }
   
   /**
    * Updates the final status of each square when game is over.
    * There are two results we need to consider:
    * 1. Game is over because we win
    * 2. Game is over because we lose
    * @param isGameWin whether game is over because we win
    * @param isGameLose whether game is over because we lose
    */
   private void gameIsOver(boolean isGameWin, boolean isGameLose) {
	   
	   // Game is over because we win. If we win, we have already uncovered all the squares 
	   // with no mines, so we do not need to change them. If the square has a mine and its 
	   // status is not MINE_GUESS, updates the status to MINE_GUESS.
	   if (isGameWin) {
		   for (int i = 0 ; i < mineField.numRows() ; i ++) {
			   for (int j = 0 ; j < mineField.numCols() ; j ++) {
				   if (mineField.hasMine(i, j) && status[i][j] != MINE_GUESS) {
							status[i][j] = MINE_GUESS;
				   }  
			   } 
		   	}
	   	}
		  
	   // Game is over because we lose. We need to conside following conditions:
	   // 1. This square has a mine and we have not uncovered it:
	   //		a. If its status is MINE_GUESS, we do nothing.
	   //		b. If its status is not MINE_GUESS, updates status to MINE.
	   // 2. Otherwise:
	   //		a. If its status is MINE_GUESS, updates status to INCORRECT_GUESS.
	   //		b. If its status is not MINE_GUESS, we do nothing.
	   if (isGameLose) {
		   for (int i = 0 ; i < mineField.numRows() ; i ++) {
			   for (int j = 0 ; j < mineField.numCols() ; j ++) {
				   
				   if (mineField.hasMine(i, j) && status[i][j] != EXPLODED_MINE) {
					   
					   if (status[i][j] != MINE_GUESS) {
						   status[i][j] = MINE;
					   }
					   
				   }
				   else {
					   
					   if (status[i][j] == MINE_GUESS) {
						   status[i][j] = INCORRECT_GUESS;
					   }
					   
				   }
				   
			   	}  
		   	}
	   	}
		  
   }
   
}
