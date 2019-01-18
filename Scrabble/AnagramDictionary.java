// Name: Ruihui Lu
// USC NetID: ruihuilu
// CS 455 PA4
// Fall 2018

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Scanner;


/**
   A dictionary of all anagram sets. 
   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
 */

public class AnagramDictionary {
   
	// instance variable(s):
	private Map<String, ArrayList<String>> anagramDict;
	// the map to represent every word and its corresponding letters with their frequencies
	// Key --> the specified form of word(s), i.e. "aa" --> "a2", "mac" --> "a1c1m1"...
	// Value --> the list of word(s) with corresponding form


   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.  
      PRE: The strings in the file are unique.
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException {

	   // Initializes the anagramDict as HashMap because we do not care about the order of
	   // Key set in the anagram dictionary.
	   anagramDict = new HashMap<>();
	   
	   // Creates a File object to get the file named filename
	   File file = new File(fileName);
	   
	   // Uses the Scanner object to process the file. If the file does not exist, throws a 
	   // FileNotFoundException; otherwise, put this entry into map.
	   // Note: Uses the try-with-resources to close 'in' automatically.
	   try (Scanner in = new Scanner(file))
	   {
		   while (in.hasNext()) {
			   
			   // Gets the word in the original dictionary.
			   String word = in.next();
			   
			   // Gets the String consisted of this word's corresponding letters with 
			   // their frequencies, i.e. "calm" --> "a1c1l1m1"
			   String comp = wordComp(word);
			   
			   // Returns the list of word(s) to which the specified form is mapped, or null 
			   // if this map contains no mapping for this form.
			   ArrayList<String> oldVals = anagramDict.get(comp);
			   
			   // If there is no mapping, adds a new list of word(s) to which the specified form is 
			   // mapped; otherwise, updates the list of word(s) to which the specified form is mapped
			   if (oldVals == null) {
				   oldVals = new ArrayList<>();
				   oldVals.add(word);
			   }
			   else {
				   oldVals.add(word);
			   }
			   
			   anagramDict.put(comp, oldVals);
			   
		   }
	   }
	   
   }
   
   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param s string to process
      @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String s) {
	   
	   // Gets the specified form of the string to process
	   String sComp = wordComp(s);
	   
	   // Returns the list of word(s) to which the form(sComp) is mapped, or null if there 
	   // is no mapping for this form.
	   ArrayList<String> anagrams = anagramDict.get(sComp);
	   
	   return anagrams;
	   
   }
   
   
   /**
      Returns a new representative form of a given string. The new form consists of the word's corresponding letters
      and their frequencies, i.e. "calm" --> "a1c1l1m1", abbabdd --> "a2b3d2".
      @param word the string to be processed to get a new representative form
      @return the specified form of the given word
    */
   private String wordComp(String word) {
	   
	   	// Uses TreeMap to gets all sorted letters with their frequencies in a string.
		Map<Character, Integer> numLetters = new TreeMap<>();
		
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
		
		// A StringBuilder object is more convenient for us to get a new form of a string.
		StringBuilder newForm = new StringBuilder();
		
		// Traverses all mappings to get the new form of the given string.
		for (Map.Entry<Character, Integer> cur : numLetters.entrySet()) {
			newForm.append(cur.getKey() + "" + cur.getValue());
		}
	   
		return newForm.toString();
	   
   }
   
}
