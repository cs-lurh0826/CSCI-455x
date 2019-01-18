// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA3
// Fall 2018

/**

   MineSweeper -- main class for a GUI minesweeper game.
   Games use a 9 x 9 board with 10 randomly placed mines.  For more details about this
   game and how to play it, see the assignment description.
   
   To run it from the command line: 
      java MineSweeper
      
   DO NOT CHANGE THIS FILE

 */

import javax.swing.JFrame;

public class MineSweeper {
   
   private static final int FRAME_WIDTH = 400;
   private static final int FRAME_HEIGHT = 425;
   
   private static int SIDE_LENGTH = 9;
   private static int NUM_MINES = 10;
   

   public static void main(String[] args) {

      JFrame frame = new JFrame();

      frame.setTitle("Minesweeper");

      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

      GameBoardPanel gameBoard = new GameBoardPanel(SIDE_LENGTH, SIDE_LENGTH, NUM_MINES);

      frame.add(gameBoard);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.setVisible(true);

   }

}

