// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI455 PA2
// Fall 2018

import java.util.ArrayList;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/

public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt description for details.
   
   private static final int MAX_PILES = CARD_TOTAL;
   // the largest possible number of piles, namely the largest capacity of 
   // partially-filled array. I.e., initial configuration is [1, 1, ..., 1]
   // (the number of 1 is CARD_TOTAL)
   
   /**
      Uses a partially-filled array representation.

      Representation invariant:
      -- numPiles is the number of piles
      -- 0 < numPiles <= bulPiles.length (MAX_PILES : 45)
      -- If numPiles > 0, the index of bulPiles is from 0 to (numPiles - 1)
      -- Every element of array represents the number of card(s) in this pile
      -- Every value of bulPiles needs to be > 0
      -- CARD_TOTAL = bulPiles[0] + bulPiles[1] + ... + bulPiles[numPiles - 1]
      -- When a pile has no cards, it will be removed from the array.
      -- A new pile will be created and put at the end of array after each round
      -- At last, numPiles must be NUM_FINAL_PILES, so CARD_TOTAL = bulPiles[0] + 
      															bulPiles[1] + ... + 
      															bulPiles[NUM_FINAL_PILES - 1]
   */
   
   // <add instance variables here>
   private int numPiles;
   private int[] bulPiles;
 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {
	  
	   numPiles = piles.size();
	   bulPiles = new int[MAX_PILES];
	   
	   for (int i = 0 ; i < numPiles ; i ++) {
		  bulPiles[i] = piles.get(i);
	   }
	  
	   assert isValidSolitaireBoard();
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
      PRE: SolitaireBoard.CARD_TOTAL = bulPiles[0] + bulPiles[1] + ... + bulPiles[numPiles - 1]
   */
   public SolitaireBoard() {
	   
	   numPiles = 0;
	   
	   int numCards = 0;	// The number of cards in each pile
	   Random numGenerator = new Random(); // Generates a number from 0 to 45 randomly
	   
	   // Constructs an int array with MAX_PILES to ensure the array has enough capacity
	   bulPiles = new int[MAX_PILES];
	   
	   // leftCard is used to calculate remaining cards while assigning cards to piles
	   int leftCard = CARD_TOTAL;
	   
	   // Generates the initial configuration with a series of random numbers
	   while (true) {   
		   numCards = numGenerator.nextInt(CARD_TOTAL + 1);	// Generates the number of card(s)
		   
		   if (numCards == 0) {	// Initially, the number of card(s) cannot be 0
			   continue;
		   }
		   
		   leftCard -= numCards;	// Calculates the number of remaining card(s)
		   
		   // If leftCard > 0, keep assigning cards because we still have cards
		   // If leftCard == 0, stop assigning cards because we have no cards
		   // If leftCard < 0, numCard generated randomly is greater than 
		   // the number of remaining cards, so we need a new number
		   if (leftCard > 0) {	
			   bulPiles[numPiles] = numCards;
			   numPiles ++;
		   }
		   else if (leftCard == 0) {
			   bulPiles[numPiles] = numCards;
			   numPiles ++;
			   break;
		   }
		   else {
			   leftCard += numCards;
			   continue;
		   }   
	   }
	   
	   assert isValidSolitaireBoard();
   }
    
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
      PRE: the number of cards in the new pile = the number of piles last round = the number of 
      		remaining piles + the number of removed piles
   */
   public void playRound() {
	   
	   // Records the number of removed piles
	   int removedPiles = 0;
	   
	   // Plays one round of Bulgarian solitaire
	   for (int loc = 0 ; loc < numPiles ; loc ++) {
		   bulPiles[loc] --;	 // Takes one card from each pile
		   
		   if (bulPiles[loc] == 0) {	// If the pile has no cards, remove it.
			   remove(loc);
			   loc -- ;
			   removedPiles ++;
		   }
	   }
	   
	   // After removing, numPiles is not only the number of remaining piles, 
	   // but also the index of the new pile
	   bulPiles[numPiles] = numPiles + removedPiles;
	   numPiles ++;
	   
	   assert isValidSolitaireBoard();
   }
   
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
   */
   public boolean isDone() {
	    
	   // At the end of the game, the number of card(s) in each pile is from 1 to
	   // NUM_FINAL_PILES. These numbers are in any order and only appear one time.
	   for (int i = 1 ; i <= NUM_FINAL_PILES ; i ++) {
		   if (!containOnce(i)) {
			   assert isValidSolitaireBoard();
			   return false;
			   }
	   }
	   
	   assert isValidSolitaireBoard();
	   return true; 
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {
	   
	   // Constructs a stringbuilder object to generate the expected string
	   StringBuilder configMessage = new StringBuilder();
	   
	   // Gets a space-separated list of numbers with no leading or trailing spaces.
	   for (int i = 0 ; i < numPiles - 1; i ++) {
		   configMessage.append(bulPiles[i] + " ");
	   }
	   
	   configMessage.append(bulPiles[numPiles - 1]);
	   
	   assert isValidSolitaireBoard();
	   return configMessage.toString();
	   
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
      PRE: SolitaireBoard.CARD_TOTAL = bulPiles[0] + bulPiles[1] + ... + bulPiles[numPiles - 1]
   */
   private boolean isValidSolitaireBoard() {
	   
	   int totalCards = 0;
	   
	   for (int i = 0 ; i < numPiles ; i ++) {
		   totalCards += bulPiles[i];
	   }
	   
	   if (totalCards != CARD_TOTAL) { return false;	}
	   
	   return true;
   }
   

   // <add any additional private methods here>
   /**
    * Removes the pile which has no cards
    * @param loc the index of the removed pile
    */
   private void remove(int loc) {
	   
	   for (int i = loc + 1 ; i < numPiles ; i ++) {
		   bulPiles[i - 1] = bulPiles[i];
	   }
	   
	   numPiles --;
	   
   }

   /**
    * Checks whether the given value appears only one time in bulPiles, no matter what their order are
    * @param value the given value
    * @return a boolean: true --> the given value appears one time
    * 					false --> the given value does not appear or appears more than one time
    */
   private boolean containOnce(int value) {
	   
	   assert isValidSolitaireBoard();
	   
	   // The times the given value appears
	   int valueCount = 0;
	   
	   // Counts the times the given value appears
	   for (int i = 0 ; i < numPiles ; i ++) {
		   if (bulPiles[i] == value) {
			   valueCount ++;
		   }
	   }
	   
	   if (valueCount == 1) { return true; }

	   return false;
   }

   
}
