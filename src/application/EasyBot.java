package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;


public class EasyBot {
	public String name;

	public static List<String> wordsProposed = new ArrayList<String>();
	public static List<String> wordsTopropose = new ArrayList<String>();
	public static List<Character> bannedCharacter = new ArrayList<Character>();

	public EasyBot() {
	}

	public EasyBot(String name, int difficulty) {
		this.name = name;
	}

	/* Première tentative */

	public String playEasyFirstTry(int lenOfWord) {
		EasyBot.wordsTopropose = Parseur.getListeMotsDeLongueur(lenOfWord);
		String wordToPropose = pickRandomWord(EasyBot.wordsTopropose);
		EasyBot.wordsTopropose.remove(wordToPropose);
		EasyBot.wordsProposed.add(wordToPropose);
		return wordToPropose;
	}

	/* Deuxième tentative */

	public String playEasyNextTry(int tryNumber, List<String> possibleWords, List<Boolean> lastGuessResult) {
		if (EasyBot.wordsProposed.isEmpty() || tryNumber >= EasyBot.wordsProposed.size()) {
			return "";
		}

		String letterPlacedWell = "";
		int j = 0;
		for (boolean guess : lastGuessResult) {
			if (guess) {
				letterPlacedWell += EasyBot.wordsProposed.get(tryNumber).charAt(j);
			} else {
				letterPlacedWell += "0";

				EasyBot.bannedCharacter.add(EasyBot.wordsProposed.get(tryNumber).charAt(j));
			}
			j++;
		}
		return letterPlacedWell;
	}


	public static void banWords(ArrayList<String> listToStudy, String lastWordProposedBinary) {
	    Iterator<String> iterator = listToStudy.iterator();
	    while (iterator.hasNext()) {
	        String s = iterator.next();
	        for (int i = 0; i < s.length(); i++) {
	            if (lastWordProposedBinary.charAt(i) != '0' && s.charAt(i) != lastWordProposedBinary.charAt(i)) {
	                iterator.remove();
	                break; 
	            }
	        }
	    }
	}

	public String pickRandomWord(List<String> listOfWords) {
		Random random = new Random();
		int randomIndex = random.nextInt(listOfWords.size());
		return listOfWords.get(randomIndex);
	}

}
