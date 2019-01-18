// Name: Ruihui Lu
// USC NetID: ruihuilu
// CSCI455 PA2
// Fall 2018

import java.util.ArrayList;
import java.util.Scanner;

/**
 * class BulgarianSolitaireSimulator
 * @author Lu,Ruihui
 * 
 * Simulates Bulgarian Solitaire one time. Users can choose one of four startup modes to 
 * begin the game.
 *
 * Four startup modes: 
 * 1. start(): uses random numbers to initialize board and then plays until the end without stops
 * 2. sStart(): uses random numbers to initialize board and stops at each round
 * 3. uStart(): uses user's input to initialize board and then plays until the end without stops
 * 4. usStart(): uses user's input to initialize board and stops at each round
 * 
 * How to choose the startup mode:
 * 1. uses command "java -ea BulgarianSolitaireSimulator"
 * 2. uses command "java -ea BulgarianSolitaireSimulator -s"
 * 3. uses command "java -ea BulgarianSolitaireSimulator -u"
 * 4. uses command "java -ea BulgarianSolitaireSimulator -u -s" or "java -ea BulgarianSolitaireSimulator -s -u"
 **** "-ea" makes assert statements in the program work
*/

public class BulgarianSolitaireSimulator {

	/**
	 * Based on command(s), the bulgarian solitaire starts one time.
	 * In the main method, only uses private method, chooseStart, to decide the startup mode;
	 * the parameters of chooseStart are used to set up the startup mode.
	 * @param args command(s) the user inputs, i.e. -u -s
	 */
	public static void main(String[] args) {
     
		boolean singleStep = false;
		boolean userConfig = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-u")) {
				userConfig = true; 
			}
			else if (args[i].equals("-s")) {
				singleStep = true;
			}
		}

		// <add code here>
		Scanner in = new Scanner(System.in);
		
		chooseStart(userConfig, singleStep, in);
	}
   
	// <add private static methods here>
	/**
	 * Judges whether the program can get a series of valid numbers from user's input to
	 * initialize piles.
	 * @param in a Scanner object to get user's input
	 * @param piles an Arraylist object to store user's input
	 * @return a boolean to represent whether input is valid. true -> valid, false -> invalid
	 */
	private static boolean isValidInput(Scanner in, ArrayList<Integer> piles) {
		
		// numCard is used to identify each integer which user inputs
		int numCard = 0;
		
		// Gets user's input
		String lineOfConfig = in.nextLine();
		Scanner lineScanner = new Scanner(lineOfConfig);
		
		// Judges whether input is invalid. There are the following cases (invalid):
		// 1. user's input is not Integer
		// 2. user's input is <= 0
		// 3. the sum of user's input is != CARD_TOTAL
		while (lineScanner.hasNext()) {
			
			if (lineScanner.hasNextInt()) {
				
				numCard = lineScanner.nextInt();
				
				// Case 2:
				if (numCard <= 0) {
					piles.clear();
					return false;
				}
				
				piles.add(numCard);	// If the number is valid, adds it to piles.
				
			} else {
				// case 1:
				piles.clear();
				return false;
			}
			
		}
	
		// Case 3:
		if (sum(piles) != SolitaireBoard.CARD_TOTAL) {
			piles.clear();
			return false;
		}
		
		return true;
		
	}
	
	
	/**
	 * Calculates the number of total cards from user's input
	 * @param piles the ArrayList to store user's input
	 * @return an integer to represent the number of total cards
	 */
	private static int sum(ArrayList<Integer> piles) {
		
		int totalCards = 0;
		
		for (int i = 0 ; i < piles.size(); i ++) {
			totalCards += piles.get(i);
		}
		
		return totalCards;
	}
	
	
	/**
	 * Initializes solitaire board with user's input
	 * @param in a Scanner object to get user's input
	 * @return the initialized solitaire board
	 */
	private static SolitaireBoard getUserBoard(Scanner in) {
		
		// piles is used to store user's input to initialize solitaire board
		ArrayList<Integer> piles = new ArrayList<Integer>();
		
		// isValid is used to judge whether user's input is legal, and its default 
		// value is false
		boolean isValid = false;
		
		System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL + 
							"\nYou will be entering the initial configuration of the cards "
							+ "(i.e., how many in each pile).");
		
		// Gets a series of valid numbers from user's input to initialize solitaire board.
		// If isValid is true, constructs a SolitaireBoard object with user's input; if false,
		// prints error message and keep getting user's input
		while (!isValid) {
			
			System.out.println("Please enter a space-separated list of positive integers followed by newline:");
			
			isValid = isValidInput(in, piles);	// Judges whether the program gets valid input
			
			if (isValid == false) {
				System.out.println("ERROR: Each pile must have at least one card " +
					"and the total number of cards must be 45");
			}
			
		}
		
		// Returns a SolitaireBoard object with user's input, and starts.
		return new SolitaireBoard(piles);
	}
	
	
	/**
	 * Bulgarian Solitaire starts and prints the board configuration each round until the end
	 * @param board the SolitaireBoard object
	 */
	private static void start(SolitaireBoard board) {
		
		// Records the number of rounds
		int count = 0;
		
		System.out.println("Initial configuration: " + board.configString());
		
		// Judges whether Bulgarian Solitaire has not finished.
		// If not, keep playing and printing the current configuration.
		while (!board.isDone()) {
			board.playRound();
			count ++;
			
			System.out.println("[" + count + "] Current configuration: " + 
								board.configString());
		}
		
		System.out.println("Done!");
		
	}
	
	
	/**
	 * One startup mode: the program will use user's input to initialize piles and check
	 * whether user's input is legal. If legal, Bulgarian Solitaire will start.
	 * @param in the Scanner object to get user's input
	 */
	private static void uStart(Scanner in) {
		
		SolitaireBoard userBoard = getUserBoard(in);
		
		start(userBoard);
		
	}

	
	/**
	 * One startup mode: the program will stop at each round and wait for user's commands to
	 * continue working.
	 * @param board the SolitaireBoard object
	 * @param in the Scanner object to get commands
	 */
	private static void sStart(SolitaireBoard board, Scanner in) {
		
		// Records the number of rounds
		int count = 0;
				
		System.out.println("Initial configuration: " + board.configString());
				
		// Judges whether Bulgarian Solitaire has not finished.
		// If not, keep playing and printing the current configuration.
		while (!board.isDone()) {
			
			board.playRound();
			count ++;
			
			System.out.println("[" + count + "] Current configuration: " + 
								board.configString());
			System.out.print("<Type return to continue>");
			
			// Continues playing until user types return
			while (in.nextLine().length() != 0) {}
			
		}
		
		System.out.println("Done!");
		
	}

	
	/**
	 * One startup mode: the program will use user's input to initialize piles and check
	 * whether user's input is legal. If legal, Bulgarian Solitaire will start. Then, the 
	 * program will stop at each round and wait for user's commands to continue working.
	 * @param in the Scanner object to get commands and user's input
	 */
	private static void usStart(Scanner in) {
		
		SolitaireBoard userBoard = getUserBoard(in);
		
		sStart(userBoard, in);
		
	}
	
	
	/**
	 * Based on user's command, chooses one startup mode.
	 * @param userConfig decides whether uses user's input to initialize piles
	 *					true -- > yes, false --> no
	 * @param singleStep decides whether stops at each round
	 * 					true -- > yes, false --> no
	 */
	private static void chooseStart(boolean userConfig, boolean singleStep, Scanner in) {
		
		if (userConfig == true && singleStep == false) {
			uStart(in);
		} else if (userConfig == false && singleStep == true) {
			sStart(new SolitaireBoard(), in);
		} else if (userConfig == true && singleStep == true) {
			usStart(in);
		} else {
			start(new SolitaireBoard());
		}
		
	}

	
}
