// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA3
// Fall 2018

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
   GameBoardPanel class
   This is the GUI for the program: it contains the display and controls for a game, and the minefield display (grid
   of "buttons").  It's the View and Controller in the MVC design pattern, whereas the Model is the VisibleField 
   and MineField.
   It is decomposed into a few other classes that are inner classes of this one.
   
   Depends on the existence of the two files "images/facesmile.gif" and "images/facedead.gif"
   
   @author CMB
      
   Change history:
   [10/10/18 CMB] Fixed bug where it displayed "?" in some squares erroneously at the end of the game.
            (included refactoring to keep the body of updateDisplayProperties within 30 lines)

 */


class GameBoardPanel extends JPanel {
   
   /**
      Design of the GameBoardPanel:
      The game data (Model) is in the visibleField instance variable (and the underlying minefield in 
      visibleField().getMineField()).
      Contains inner classes SquareView (a JLabel) and SquareListener (a MouseListener for that label), 
      which are the View and Controller, respectively, for an individual square (one instance of each of these 
      is created for each square on the board).  
      The Model for a single square is not a separate class, but is collectively part of visibleField and can be
      accessed using VisibleField methods getStatus(row, col) and isCovered(row, col), and can be mutated with
      VisibleField methods cycleGuess(row, col) and uncover(row, col) [the last of these can change more than one 
      square]
    */
      
   private static final int BORDER_THICKNESS = 1;
   private static final Border COVERED_BORDER = BorderFactory.createRaisedBevelBorder();
   private static final Border UNCOVERED_BORDER = BorderFactory.createLoweredBevelBorder();

   private static final Border PADDING = BorderFactory.createEmptyBorder(10, 10, 10, 10);
   private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
   private static final Font INCORRECT_GUESS_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 36);

   private static final Color EXPLODED_MINE_COLOR = Color.RED;
   private static final Color MINE_COLOR = Color.BLACK;
   private static final Color MINE_GUESS_COLOR = Color.YELLOW;
   
   // these two files need to be in a subdir of the location of the .class files
   private static final String HAPPY_ICON_FILE_NAME = "images/facesmile.gif";
   private static final String SAD_ICON_FILE_NAME = "images/facedead.gif";
   
   private final ImageIcon happyIcon = createImageIcon(HAPPY_ICON_FILE_NAME);
   private final ImageIcon sadIcon = createImageIcon(SAD_ICON_FILE_NAME);
   
   private static final String GAME_STATUS_TOOLTIP_TEXT = "displays whether you won or lost";
   private static final String MINE_GUESS_TOOLTIP_TEXT = "number of mines left to guess";
   private static final String NEW_GAME_TOOLTIP_TEXT = "new game";
   
   private static final Border TOP_LABEL_BORDER = BorderFactory.createLineBorder(Color.black);
   private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();

   // controls/displays at the top
   private JLabel mineGuessLabel;
   private JButton newGameButton;
   private JLabel gameStatusLabel;
  
   // Grid of "buttons" (actually JLabels)
   private SquareView[][] mySquares;  // need so we can update display en masse

   private VisibleField visibleField;     // game data (the Model in MVC)
   
   private boolean userChoseFirstLocation = false;   // tells whether user has yet opened the first square in a game
                                 // so we can guarantee it's not a mine, by generating the mines after this click.
   
   private boolean isRandomMineField;    // also can play the game with a fixed mine field
   
   
   /**
     Creates a GameBoardPanel from a given VisibleField (which contains an associated MineField).
     When we create it this way, the same MineField object is used for all the games played until we exit the app.
     @param visibleField  the VisibleField that this GUI reflects
   */
   public GameBoardPanel(VisibleField visibleField) { 
      
      this.visibleField = visibleField;     
      this.isRandomMineField = false;
      
      setUpGUI(); 
      
   }
   
   /**
     Creates a GameBoardPanel for a minefield with the given dimensions and number of mines.  The mine placements
     are chosen randomly later, and are regenerated for each new game.  For all games, the minefield used will
     have the same numRows, numCols, and numMines.
     @param numRows  number of rows the minefield will have, must be positive
     @param numCols  number of columns the minefield will have, must be positive
     @param numMines   number of mines the minefield will have once we populate it.
     PRE: numMines < 1/3 of possible field locations
   */
   public GameBoardPanel(int numRows, int numCols, int numMines) {
      
      assert numRows > 0 && numCols > 0;
      int limit = numRows * numCols; 
      assert numMines < limit / 3.0;
      
      this.visibleField = new VisibleField(new MineField(numRows, numCols, numMines));
      this.isRandomMineField = true;
      
      setUpGUI();
   }
   
   /**
      Create all the elements GUI (i.e., components and listeners) and their organization.
    */
   private void setUpGUI() {
      setLayout(new BorderLayout());
      setBorder(PADDING);
      
      JPanel top = setUpTopPanel();
      
      add(top, BorderLayout.NORTH);
      
      JPanel board = new JPanel();
          
      mySquares = new SquareView[visibleField.getMineField().numRows()][visibleField.getMineField().numCols()];
      
      GridLayout squareLayout = new GridLayout(mySquares.length, mySquares[0].length);   
      squareLayout.setHgap(BORDER_THICKNESS);
      squareLayout.setVgap(BORDER_THICKNESS);

      board.setLayout(squareLayout);
      
      for (int row = 0; row < mySquares.length; row++) {
         for (int col = 0; col < mySquares[0].length; col++) {
            mySquares[row][col] = addSquare(row, col, board);
         }
      }
      
      add(board, BorderLayout.CENTER);
   }
   
   
   /**
      Set up top area of the gui that has the display of (1) how many mines left to guess, (2) the new game button 
      (which displays a happy or sad face based on whether you just lost or not), and (3) an area to display a message
      about whether the user won or lost when a game is over.
    * @return the panel that contains these three elements
    */
   private JPanel setUpTopPanel() {
      
      JPanel top = new JPanel();
      top.setLayout(new GridLayout(0, 3));
      top.setBorder(PADDING);
      
      // tells how many mines guessed in game
      mineGuessLabel = new JLabel(Integer.toString(visibleField.getMineField().numMines()));
      mineGuessLabel.setHorizontalAlignment(SwingConstants.CENTER);
      mineGuessLabel.setBorder(TOP_LABEL_BORDER);
      mineGuessLabel.setToolTipText(MINE_GUESS_TOOLTIP_TEXT);
    
      // new game button (also displays happy face unless you lost)
      newGameButton = new JButton(happyIcon);      
      newGameButton.setBorder(EMPTY_BORDER);
      newGameButton.setBackground(null);
      newGameButton.setToolTipText(NEW_GAME_TOOLTIP_TEXT);
      
      // clicking newGameButton starts a new game
      newGameButton.addActionListener(new ActionListener() {  // this listener is an anonymous inner class
         public void actionPerformed(ActionEvent event) {
            // doesn't reset the MineField here, just the display, because populateMineField clears old mines
            // before populating it with new ones and for non-random MineField, we use the same minefield in
            // subsequent games.
            visibleField.resetGameDisplay();
            userChoseFirstLocation = false;
            updateAllSquaresViews();
            mineGuessLabel.setText(Integer.toString(visibleField.getMineField().numMines()));
            newGameButton.setIcon(happyIcon);
            gameStatusLabel.setText("");
            repaint();
         }
      });
      
      // tells whether won / lost     
      gameStatusLabel = new JLabel("");
      gameStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
      gameStatusLabel.setBorder(TOP_LABEL_BORDER);
      gameStatusLabel.setToolTipText(GAME_STATUS_TOOLTIP_TEXT);

      top.add(mineGuessLabel);
      top.add(newGameButton);
      top.add(gameStatusLabel);
      
      return top;
   }
   
   
   /** 
      Create an ImageIcon from the given file, or returns null if the path was invalid. 
         (adapted from code in Oracle Java Tutorials
         https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html)
    * @param path    relative path to the file that has the image
    * @return   ImageIcon for this image or null if path was invalid
    */
   private ImageIcon createImageIcon(String path) {
      java.net.URL imgURL = getClass().getResource(path);
      if (imgURL != null) {
         return new ImageIcon(imgURL, path);
      } else {
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }


   /**
      Update all the squares in the GUI based on the current state of the VisibleField.
    */
   private void updateAllSquaresViews() {
      for (int row = 0; row < mySquares.length; row++) {
         for (int col = 0; col < mySquares[0].length; col++) {
            mySquares[row][col].updateDisplayProperties();
         }
      }   
   }
   
   
   /**
      Add the and return the View object for the square at location (row, col), and add it to the board panel.
    * @param row  row of the square to add
    * @param col  column of the square to add
    * @param board  the panel to add it to
    * @return
    */
   private SquareView addSquare(int row, int col, JPanel board) {       
      SquareView square = new SquareView(row, col);
      board.add(square); 
      return square; 
   }
   
   
   //-------------------------------------------------------------------------------------
   // INNER CLASS SquareListener
   // the Controller (in MVC) class for a square
   private class SquareListener extends MouseAdapter {
      
      private SquareView mySquare;
      
      public SquareListener(SquareView mySquare) {
         this.mySquare = mySquare;
      }
      
      /**
         Invoked when a mouse button has been pressed in this component.
       */
      public void mousePressed(MouseEvent e) {
         
         if (visibleField.isGameOver()) return;  // don't respond to clicks if the game is over
         
         if (e.getButton() == MouseEvent.BUTTON1) { // left click
            openSquare();
         }
         else if (e.getButton() == MouseEvent.BUTTON3) {  // right click
            changeGuessStatus();
         }
      }
      
 
      /**
         Open this square.  This in turn may recursively open other squares.  If this square has a mine in it
         this action can end the game (in a loss).  If this is the first one to be opened in a game, this will 
         trigger the initial placement of the mines before the recursion (and guarantee that so no mine is on this
         square so a user doesn't lose on the first click).
       */
      private void openSquare() {
                              // can't open it when it's a mine guess (user has to right click to "?" state first)
         if ((visibleField.getStatus(mySquare.getRow(), mySquare.getCol()) == VisibleField.MINE_GUESS) ||
               (visibleField.isUncovered(mySquare.getRow(), mySquare.getCol())))  {   // already has been uncovered
            return;         
         }
         // only choose mine locations once user has opened one square   
         // but only if we're using random minefield, o.w., we use the same mine locs for every game
         //                                              (they were set in the constructor)
         if (!userChoseFirstLocation && isRandomMineField) {  // first time uncovering a square
            userChoseFirstLocation = true;
            // doesn't put a mine in the location they chose
            visibleField.getMineField().populateMineField(
                                          mySquare.getRow(), mySquare.getCol());
         }
         
         // recursively opens up empty areas
         boolean isNotAMine = visibleField.uncover(mySquare.getRow(), mySquare.getCol());

         if (visibleField.isGameOver()) {
            if (isNotAMine) {
               // win condition
               // update statusLabel
               gameStatusLabel.setText("You won!");
            }
            else {  // loss condition
               newGameButton.setIcon(sadIcon);
               gameStatusLabel.setText("You lost!");
            }
            
         }
          
         updateAllSquaresViews();     // don't know which squares changed, so update view for all squares to match 
                                      // changes in the model
         repaint();
           
      }
      
      /**
         Change the status of a covered (i.e, non-opened) square and the display of the number of mines guessed, if
         appropriate.  See documentation of VisibleField cycleGuess() for details of the states it can go through.
       */
      private void changeGuessStatus() {
         // if the square is uncovered, can't do this operation
         if (visibleField.isUncovered(mySquare.getRow(), mySquare.getCol())) { return; }
         // update model
         visibleField.cycleGuess(mySquare.getRow(), mySquare.getCol());
         // if went to MINE_GUESS or QUESTION, the number of mine guesses changes (either up or down)
         if (visibleField.getStatus(mySquare.getRow(), mySquare.getCol()) != VisibleField.COVERED) {          
            int minesLeft = visibleField.numMinesLeft();
            int displayNum = (minesLeft >= 0) ? minesLeft : 0; // non-neg
            mineGuessLabel.setText(Integer.toString(displayNum));
         }
         // get changed state of square from model, and update the View accordingly
         // (for such an action, only the one square gets changed)
         mySquare.updateDisplayProperties();
      }
      
   }
   //------ END OF INNER CLASS SquareListener---------------------------------------------

   
   //-------------------------------------------------------------------------------------
   // INNER CLASS SquareView
   // the View (in MVC) class for a square
   private class SquareView extends JLabel {
      
      // uses a label instead of a button because it makes it easier to process left and right mouse clicks.
      // (see SquareListener class)
      // we change the border of the label so it looks like a button that's is or is not pushed in depending
      // on whether the square has been uncovered 
      
      public static final int PREF_SQUARE_WIDTH = 25;
      public static final int PREF_SQUARE_HEIGHT = 25;
            
      private int myRow;
      private int myCol;

      
      /**
         Create the View class for a square at the given location.  
       * @param row the row this square is in
       * @param col the column this square is in
       */
      public SquareView(int row, int col) {
         myRow = row;
         myCol = col;
         setBorder(COVERED_BORDER);
         setPreferredSize(new Dimension(PREF_SQUARE_WIDTH, PREF_SQUARE_HEIGHT));
         setHorizontalAlignment(SwingConstants.CENTER);
         setFont(DEFAULT_FONT);
         updateDisplayProperties();
         addMouseListener(new SquareListener(this));
      }
      
      
      /**
         Get the row this square is in
         @return the row
       */
      public int getRow() { return myRow; }
      
      
      /**
         Get the column this square is in
         @return the column
      */
      public int getCol() { return myCol; }
      
      
      /**
        Update square view based on status of corresponding square in model
       */
      public void updateDisplayProperties() {
         int status = visibleField.getStatus(myRow, myCol);
         
         setSquareBorder();  // REFACTORED 10/10
         
         if (status == VisibleField.COVERED) {
            setBackground(null);
            setFont(DEFAULT_FONT);
            setText("");
         }
         else if (status == 0) {    // no neighboring mines (empty square)
            setText("");
         }
         else if (status == VisibleField.MINE_GUESS) {
            setOpaque(true);
            setBackground(MINE_GUESS_COLOR);
            setText("");  // FIXED BUG 10/10
         }
         else if (status == VisibleField.QUESTION) {
            setBackground(null);
            setText("?");
         }
         else if (status == VisibleField.MINE) {
            setOpaque(true);
            setBackground(MINE_COLOR);
            setText("");  // FIXED BUG 10/10
         }
         else if (status == VisibleField.INCORRECT_GUESS) {            
            setFont(INCORRECT_GUESS_FONT);
            setText("X");

         }
         else if (status == VisibleField.EXPLODED_MINE) {
            setOpaque(true);
            setBackground(EXPLODED_MINE_COLOR);
            setText("");  // FIXED BUG 10/10
         }
         else if (status > 0) {  // status is the number of neighboring mines
            setText(Integer.toString(status));
         }
         else {
            throw new AssertionError(status);
         }
      }
      
      
      /**
         Set the border to show whether it was already clicked.
       */
      private void setSquareBorder() {
         if (visibleField.isUncovered(myRow, myCol)) {  
            setBorder(UNCOVERED_BORDER);  
         }
         else {
            setBorder(COVERED_BORDER);   
         }
      }
   }
   //-----------------------------END OF INNER CLASS SquareView --------------------------

}