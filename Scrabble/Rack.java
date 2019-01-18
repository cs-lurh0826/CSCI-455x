// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA4
// Fall 2018

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   A Rack of Scrabble tiles
 */

public class Rack {

	/**
	   Uses the helper method, which has been provided, to get all subsets of the given string. 
	   Therefore, we need to know all letters the given string has, and their numbers(frequencies) 
	   of occurrences.
	   @param word the string whose all subsets we need to get
	   @return the list of all subsets of the given string
	 */
	public static ArrayList<String> allSubsets(String word) {
		
		// Uses HashMap to gets all letters with their frequencies in the given string.
		Map<Character, Integer> numLetters = new HashMap<>();
		
		for (int i = 0 ; i < word.length() ; i ++) {
			
			// Gets the letter in every position of a string.
			Character letter = word.charAt(i);
			// Gets the frequency of the letter in this string.
			Integer numOfLetter = numLetters.get(letter);
			
			// If numOfLetter is null, there is no mapping for this letter in the map, and then we
			// add a new mapping; otherwise, the frequency of this letter plus one.
			if (numOfLetter == null) {
				numLetters.put(letter, 1);
			}
			else {
				numLetters.put(letter, numOfLetter + 1);
			}
			
		}
		
		// A StringBuilder object is more convenient for us to get the string consisted of letters it has.
		StringBuilder unique = new StringBuilder();
		
		// Each element in int array represents the frequency of the corresponding letter in the given string,
		// i.e. mult[0] --> charAt(0), mult[1] --> charAt(1) ...
		int[] mult = new int[numLetters.size()];
		
		// The starting index of int array mult --> thus, 0 <= i < numLetters.size()
		int i = 0;
		
		// Traverses all entries in the map to complete StringBuilder unique and int array mult.
		for (Map.Entry<Character, Integer> cur : numLetters.entrySet()) {
			unique.append(cur.getKey());
			mult[i++] = cur.getValue();		// Note: here, the final value of i is numLetters.size(), 
											// but it has no difference.
		}
		
		// Calls the helper method to return all subsets of the given string.
		return allSubsets(unique.toString(), mult, 0);
		
	}

   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset
      @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }
  
}
