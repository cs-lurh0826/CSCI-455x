// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA1
// Fall 2018

import java.util.Scanner;
import javax.swing.JFrame;

/**
 * CoinSimViewer class
 * @author Lu,Ruihui
 * This requires the user to input the number of trials. Then, it checks
 * the validity of the input and uses three labeled bars to show results 
 * of trials.
 */
public class CoinSimViewer {

	/**
	 * Gets the valid number of trials the user input
	 * @return the number of trials
	 */
	private static int getNumTrials() {
		
		Scanner in = new Scanner(System.in);
		// The number of trials the user input
		int numTrials;
		
		// Returns the number of trials until the user inputs a valid number
		while (true) {
			
			System.out.print("Please input the number of trials you want: ");
		    numTrials = in.nextInt();
		    
		    //Checks whether numTrials is >= 1
		    if (numTrials < 1) {
		    		System.out.println("ERROR: Number entered must be greater than 0.");
		    } else {
		    		return numTrials;
		    }
		}
		
	}

	/**
	 * Tosses two coins and shows results of trials.
	 * @param numTrials the number of trials
	 */
	private static void showResults(int numTrials) {
		
		JFrame frame = new JFrame();
		
	    frame.setSize(800, 500);
	    frame.setTitle("CoinSim");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    CoinSimComponent component = new CoinSimComponent(numTrials);
	    frame.add(component);
	    frame.setVisible(true);
	    
	}
	
	public static void main(String[] args) {
	    
	    int numTrials = getNumTrials();
	    
	    showResults(numTrials);
	    
	}
	
}

