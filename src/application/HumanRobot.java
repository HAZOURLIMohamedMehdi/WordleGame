package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HumanRobot {
	public HumanRobot() {
	}

	public Map<Character, Double> getLetterProbabilities(int wordLength) {
	    String filePath = "C:\\Users\\etudiant\\eclipse-workspace\\Wordle\\src\\application\\dictionnaire.txt";
	    Map<Character, Double> letterProbabilities = new HashMap<>();

	    try {
	        // Read the content of the file
	        String content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();

	        // Print out the content to check if it's being read correctly
	        //System.out.println("Content read from file: " + content);

	        // Filter words of the desired length
	        List<String> wordsOfLength = Arrays.stream(content.split("\\n"))
	                .filter(word -> word.length() == wordLength).collect(Collectors.toList());

	        // Count letters in filtered words
	        Map<Character, Integer> letterCounts = new HashMap<>();
	        for (String word : wordsOfLength) {
	            for (char c : word.toCharArray()) {
	                letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
	            }
	        }

	        int totalLetters = wordsOfLength.stream().mapToInt(String::length).sum();

	        // Calculate probabilities
	        for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
	            char letter = entry.getKey();
	            int count = entry.getValue();
	            double probability = (double) count / totalLetters;
	            letterProbabilities.put(letter, probability);
	        }

	    } catch (FileNotFoundException e) {
	        System.err.println("Le fichier n'a pas été trouvé : " + e.getMessage());
	    }
	    return letterProbabilities;
	}

	public double calculateWordProbability(String word,int wordLength) {
		Map<Character, Double> letterProbabilities = getLetterProbabilities(wordLength);
		double wordProbability = 1.0;

		for (char c : word.toCharArray()) {
			if (letterProbabilities.containsKey(c)) {
				wordProbability *= letterProbabilities.get(c);
			} else {
				// Si une lettre du mot n'est pas dans le fichier, on suppose sa probabilité à 0
				//wordProbability *= 0.0;
			}
		}
		return wordProbability;
	}
	
	public String determineBestWord(ArrayList<String> wordPossible, int wordLength) {
		int max = 0 ;
		String wordToTry = "" ;
		// On peut enlever wordLength des arguments car c'est la longueur des mots de la liste
		for(String s : wordPossible)
		{
			if (calculateWordProbability(s, wordLength) > max)
			{
				max = (int) calculateWordProbability(s, wordLength) ;
				wordToTry = s ;
			}
		}
		return wordToTry;
	}
	
	

	public static void main(String[] args) {
		HumanRobot humanRobot = new HumanRobot();
		double probability = humanRobot.calculateWordProbability("mot",3);
		System.out.println("La probabilité d'apparition du mot est : " + probability);
	}
}