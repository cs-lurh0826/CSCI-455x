// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA4
// Fall 2018

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
   class WordFinder
   @author Lu,Ruihui
   
   Simple Scrabble game: based on the rack and dictionary user provides, we will find all valid words in the dictionary.
   Finally, displays all valid words, with the corresponding Scrabble score for each word, in decreasing order by score. 
   For words with the same scrabble score, the words appear in alphabetical order.
   
   How to choose the dictionary (when run program):
   java WordFinder [dictionaryFile]
   If user does not provide dictionaryFile, program will use default dictionary: sowpods.txt.
   
   Note: 1. If the dictionary user provides does not exist, we will print an informative error message and program will exit.
         2. The program will run in a loop on the console, printing the prompt "Rack? ", and reading and processing each 
         rack you enter, until you tell it to exit. You tell the program to exit by typing in "." at the prompt (i.e., a 
         period). We aren't use a command such as "quit" as the sentinel, since that could be a legal rack.
         3. In the common format for showing Unix command-line syntax the square brackets (i.e., []) are not part of the 
         command that is typed: it just indicates that the command line argument shown is optional.
 */

public class WordFinder {

	public static void main(String[] args) {

		// Gets the input dictionary.
		// Default: sowpods.txt; otherwise, users can input a dictionary they want.
		String inFile = chooseInFile(args);
		
		// Scanner object to get users' input.
		Scanner in = new Scanner(System.in);
		
		// try-catch: when the dictionary user inputs does not exist, we cannot construct an
		// AnagramDictionary object because it will throw a FileNotFoundException. At this time,
		// we need to print related message to remind the user.
		try
		{
			// Constructs an AnagramDictionary object based on input dictionary. If input dictionary 
			// does not exist, throws a FileNotFoundException.
			AnagramDictionary dict = new AnagramDictionary(inFile);
			
			// If gets an AnagramDictionary object successfully, the scrabble begins.
			scrabbleBegins(in, dict);
			
		}
		catch (FileNotFoundException e) {
			// If input dictionary does not exist, we need to remind user, and program exits.
			System.out.println("The dictionary " + inFile + " does not exist!");
			// Closes the Scanner object when program exits.
			in.close();
		}
		
	}
	
	// <private static methods here>
	/**
	   We take an optional command-line argument for the dictionary file name. If that argument is left off,
	   it will use the Scrabble dictionary file sowpods.txt from the same directory as you are running your 
	   program, i.e. java WordFinder [dictionaryFile].
	   @param args the command-line argument(s)
	   @return the dictionary file name we will use for the game.
	 */
	private static String chooseInFile(String[] args) {
		return (args.length != 0) ? args[0] : "sowpods.txt";		
	}
	
	/**
	   After we construct an AnagramDictionary object successfully, the scrabble begins.
	   @param in  the Scanner object to get rack user inputs
	   @param dict  the AnagramDictionary object which we use to get valid words' scores.
	 */
	private static void scrabbleBegins(Scanner in, AnagramDictionary dict) {
		
		// Prints initial output message to tell users how to exit.
		System.out.println("Type . to quit.");
		
		// The scrabble will not exit until user inputs "."
		while (true) {
			
			// Tells users to input rack they want.
			System.out.print("Rack? ");
			
			// Gets user's input
			String userInput = in.nextLine();

			// If user's input is ".", the scrabble exits.
			if (userInput.equals(".")) { 
				// When scrabble exits, closes the Scanner object.
				in.close();
				// The scrabble exits.
				break;
			}
			
			// Gets rack consisted of letters from user's input.
			String rack = getRack(userInput);
			
			// Based on the valid rack and anagram dictionary we get from user's input,
			// gets the board of score(s) of valid word(s).
			Map<String, Integer> scoresBoard = getScoresBoard(rack, dict);
			
			// Prints the final board of score(s) based on the ordering of score(s).
			printScoresBoard(scoresBoard, rack);
			
		}
		
	}
	
	
	/**
	   Gets rack consisted of letters from user's input, i.e. "ca@lm" --> "calm", "ca l  m" --> "calm",
	   "@1123" --> ""...
	   @param userInput the String user inputs
	   @return the rack consisted of letters
	 */
	private static String getRack(String userInput) {
		
		StringBuilder rack = new StringBuilder();
		
		// Captures letters in the user's input and then gets a valid rack consisted of them.
		for (int i = 0 ; i < userInput.length() ; i ++) {
			
			// Gets the character at every position of user's input.
			char ch = userInput.charAt(i);
			
			// If the character is one of letters, then adds it to the rack.
			if (Character.isLetter(ch)) {
				rack.append(ch);
			}
			
		}
		
		return rack.toString();
		
	}
	
	/**
	    Based on the valid rack and anagram dictionary we get from user's input, gets the board of score(s) 
	    of valid word(s).
	   @param rack the valid rack
	   @param dict the anagram dictionary
	   @return the map which represents the board of score(s)
	 */
	private static Map<String, Integer> getScoresBoard(String rack, AnagramDictionary dict) {
		
		// Gets all subsets of the valid rack.
		ArrayList<String> allSubSets = Rack.allSubsets(rack);
		
		// Uses TreeMap to represent the board of the score(s)
		Map<String, Integer> scoresBoard = new TreeMap<>();
		
		// Constructs a ScoreTable object to get the score of one valid word.
		ScoreTable scoreTable = new ScoreTable();
		
		// Looks up all anagrams of all subsets (including itself) of the valid rack.
		for (int i = 0 ; i < allSubSets.size() ; i ++) {
			
			ArrayList<String> allAnagrams = dict.getAnagramsOf(allSubSets.get(i));
			
			// For every subset, if there is no its anagram in the dictionary, its list of anagrams 
			// will be null; otherwise, adds its anagram(s) and corresponding score(s) to the scoreboard.
			if (allAnagrams != null) {
				for (int j = 0 ; j < allAnagrams.size() ; j ++) {
					// Gets every anagram.
					String validWord = allAnagrams.get(j);
					// Adds this anagram and its score to scoreboard.
					scoresBoard.put(validWord, scoreTable.scoreOf(validWord));
				}	
			}
			
		}
		
		return scoresBoard;
		
	}

	/**
	   Displays all valid words, with the corresponding Scrabble score for each word, in decreasing order by score. 
	   For words with the same scrabble score, the words appear in alphabetical order.
	   @param scoresBoard the final board of scores of all valid words.
	   @param rack the valid rack
	 */
	private static void printScoresBoard(Map<String, Integer> scoresBoard, String rack) {

		// Prints related message including how many words we get, and the valid rack.
		System.out.println("We can make " + scoresBoard.size() + " words from \"" + rack + "\"");
		
		// If there is no valid word, we do nothing.
		if (scoresBoard.size() != 0) {
			
			System.out.println("All of the words with their scores (sorted by score):");
		
			// Sorts words in decreasing order by score. If words have the same score, in alphabetical order(TreeMap).
			ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(scoresBoard.entrySet());
			Collections.sort(list, new ScoreComparator());
		
			// Prints all valid words and their scores.
			for (Map.Entry<String, Integer> curr : list) {
				System.out.println(curr.getValue() + ": " + curr.getKey());
			}
			
		}
			
	}
	
}

/**
   class ScoreComparator
   @author Lu,Ruihui
   
   This class is only used to provide a sorting rule: all valid words will be sorted in decreasing order by score. If 
   words have the same scrabble score, they appear in alphabetical order(TreeMap).
 */

class ScoreComparator implements Comparator<Map.Entry<String, Integer>>{
	   
	   public int compare(Map.Entry<String, Integer> item1, Map.Entry<String, Integer> item2){
	      return Integer.compare(item2.getValue(), item1.getValue());
	   }
	   
}
