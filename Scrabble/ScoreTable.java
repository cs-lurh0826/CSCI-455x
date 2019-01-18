// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA4
// Fall 2018

import java.util.Arrays;

/**
   This class has information about Scrabble scores for scrabble letters and words. In scrabble, not every letter
   has the same value. Here are all the letter values:
   - 1 point : A, E, I, O, U, L, N, S, T, R
   - 2 points : D, G
   - 3 points : B, C, M, P
   - 4 points : F, H, V, W, Y
   - 5 points : K
   - 8 points : J, X
   - 10 points : Q, Z
   
   This class has a method called getScore(), which is used to calculate the score of one valid word.
   @author Lu,Ruihui 
 */
public class ScoreTable {
	
	public static final int NUM_OF_LETTERS = 26;
	// the constant to represent the total number of letters
	
	// the following constants represent different points according to the frequency of each letter
	public static final int FIRST_POINT = 1;		// A, E, I, O, U, L, N, S, T, R
	public static final int SECOND_POINT = 2;	// D, G
	public static final int THIRD_POINT = 3;		// B, C, M, P
	public static final int FOURTH_POINT = 4;	// F, H, V, W, Y
	public static final int FIFTH_POINT = 5;		// K
	public static final int SIXTH_POINT = 8;		// J, X
	public static final int SEVENTH_POINT = 10;	// Q, Z
	
	
	// Instance variable(s):
	private int[] scoresBoard;
	// the int array to store all scores of all letters
	
	
	/**
	   Constructs a ScoreTable object, and initializes all scores of all letters
	 */
	public ScoreTable() {
		
		scoresBoard = new int[NUM_OF_LETTERS];
		
		// Based on alphabetical order, the range of index [0, 25] represents the range of points of a/A ~ z/Z,
		// i.e. scoresBoard[0] represents the point of a/A, scoresBoard[1] --> b/B ... and so on.
		// A, E, I, O, U, L, N, S, T, R
		scoresBoard['A' - 'A'] = scoresBoard['E' - 'A'] = scoresBoard['I' - 'A'] = scoresBoard['O' - 'A'] =
			scoresBoard['U' - 'A'] = scoresBoard['L' - 'A'] = scoresBoard['N' - 'A'] = scoresBoard['S' - 'A'] = 
				scoresBoard['T' - 'A'] = scoresBoard['R' - 'A'] = FIRST_POINT;
		// D, G
		scoresBoard['D' - 'A'] = scoresBoard['G' - 'A'] = SECOND_POINT;
		// B, C, M, P
		scoresBoard['B' - 'A'] = scoresBoard['C' - 'A'] = scoresBoard['M' - 'A'] = scoresBoard['P' - 'A'] = THIRD_POINT;
		// F, H, V, W, Y
		scoresBoard['F' - 'A'] = scoresBoard['H' - 'A'] = scoresBoard['V' - 'A'] = scoresBoard['W' - 'A'] = 
			scoresBoard['Y' - 'A'] = FOURTH_POINT;
		// K
		scoresBoard['K' - 'A'] = FIFTH_POINT;
		// J, X
		scoresBoard['J' - 'A'] = scoresBoard['X' - 'A'] = SIXTH_POINT;
		// Q, Z
		scoresBoard['Q' - 'A'] = scoresBoard['Z' - 'A'] = SEVENTH_POINT;
		
	}	
	
	/**
	   Gets the score of one word.
	   @param word  whose score we need to calculate
	   @return the total score of the word
	 */
	public int scoreOf(String word) {
		
		int totalScore = 0;
		
		// Because lower case and upper case of the same letter have the same score, we just need to 
		// 1. change all letters to lower cases, and minus 'a'; (I use this method)
		// 2. change all letters to upper cases, and minus 'A';
		for (int i = 0 ; i < word.length() ; i ++) {
			totalScore += scoresBoard[word.toLowerCase().charAt(i) - 'a'];
		}
		
		return totalScore;
		
	}
	
}