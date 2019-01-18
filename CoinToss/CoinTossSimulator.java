// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA1
// Fall 2018

import java.util.Random;

/**
 * class CoinTossSimulator
 * 
 * Simulates trials of repeatedly tossing two coins and allows the user to access the
 * cumulative results.
 * 
 * NOTE: we have provided the public interface for this class.  Do not change
 * the public interface.  You can add private instance variables, constants, 
 * and private methods to the class.  You will also be completing the 
 * implementation of the methods given. 
 * 
 * Invariant: getNumTrials() = getTwoHeads() + getTwoTails() + getHeadTails()
 * 
 */
public class CoinTossSimulator {

	// The number of trials
	private int numTrials;
	// The number of times the result was two heads
	private int twoHeads;
	// The number of times the result was two tails
	private int twoTails;
	// The number of times the result was one of each
	private int headTails;
	// Two different values when the coin was head or tail
	private static final int HEAD_OF_COIN = 0;
	private static final int TAIL_OF_COIN = 1;
	
   /**
      Creates a coin toss simulator with no trials done yet.
   */
   public CoinTossSimulator() {
	   
	   // Initializes all of numbers
	   this.numTrials = 0;
	   this.twoHeads = 0;
	   this.twoTails = 0;
	   this.headTails = 0;  
	   
   }


   /**
      Runs the simulation for numTrials more trials. Multiple calls to this method
      without a reset() between them *add* these trials to the current simulation.
      
      @param numTrials  number of trials to for simulation; must be >= 1
    */
   public void run(int numTrials) {
	   
	   this.numTrials += numTrials;
	   // Generates a random number (0 or 1)
	   Random numberGenerator = new Random();
	   // The variables used to judge the result
	   int firstCoin, secondCoin;
	   
	   for (int i = 0 ; i < numTrials ; i ++) {
		   firstCoin = numberGenerator.nextInt(2);
		   secondCoin = numberGenerator.nextInt(2);
		   // Counts three different results
		   if (firstCoin == HEAD_OF_COIN && secondCoin == HEAD_OF_COIN) {
			   this.twoHeads ++;
		   } else if (firstCoin == TAIL_OF_COIN && secondCoin == TAIL_OF_COIN) {
			   this.twoTails ++;
		   } else {
			   this.headTails ++;
		   }
	   }
	   
   }


   /**
      Get number of trials performed since last reset.
   */
   public int getNumTrials() {
       return this.numTrials;
   }


   /**
      Get number of trials that came up two heads since last reset.
   */
   public int getTwoHeads() {
       return this.twoHeads;
   }


   /**
     Get number of trials that came up two tails since last reset.
   */  
   public int getTwoTails() {
       return this.twoTails;
   }


   /**
     Get number of trials that came up one head and one tail since last reset.
   */
   public int getHeadTails() {
       return this.headTails;
   }


   /**
      Resets the simulation, so that subsequent runs start from 0 trials done.
    */
   public void reset() {
	   
	   // Resets all of numbers
	   this.numTrials = 0;
	   this.twoHeads = 0;
	   this.twoTails = 0;
	   this.headTails = 0;
	   
   }

}