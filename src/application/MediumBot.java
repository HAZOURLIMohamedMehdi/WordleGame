package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;


public class MediumBot {
	public String name;

	public static List<String> wordsProposed = new ArrayList<String>();
	public static List<String> wordsTopropose = new ArrayList<String>();
	public static List<Character> bannedCharacter = new ArrayList<Character>();

	public MediumBot() {
	}

	public MediumBot(String name, int difficulty) {
		this.name = name;
	}

	/* Première tentative */

	public String playMediumFirstTry(int lenOfWord) {
		MediumBot.wordsTopropose = Parseur.getListeMotsDeLongueur(lenOfWord);
		String wordToPropose = pickRandomWord(MediumBot.wordsTopropose);
		MediumBot.wordsTopropose.remove(wordToPropose);
		MediumBot.wordsProposed.add(wordToPropose);
		return wordToPropose;
	}

	/* Deuxième tentative */

	public String playMediumNextTry(int tryNumber, List<String> possibleWords, List<Character> lastGuessResult) {
		if (MediumBot.wordsProposed.isEmpty() || tryNumber >= MediumBot.wordsProposed.size()) {
			return "";
		}
		String letterPlacedWell = "";
		int j = 0;
		for (char guess : lastGuessResult) {
			if (guess == '*') {
				letterPlacedWell += MediumBot.wordsProposed.get(tryNumber).charAt(j);
				
			} 
			else if (guess == '%')
			{
				letterPlacedWell += "1";
			}
			else
			{
				letterPlacedWell += "0";
				MediumBot.bannedCharacter.add(MediumBot.wordsProposed.get(tryNumber).charAt(j));
			}
			j++;
		}
		return letterPlacedWell;
	}

	public static void banWords(ArrayList<String> listToStudy, String lastWordProposedBinary) {
	    Iterator<String> iterator = listToStudy.iterator();
	    while (iterator.hasNext()) {
	        String s = iterator.next();
	        if (s.length() != lastWordProposedBinary.length()) {
	            iterator.remove(); // Supprimer les mots de longueur différente
	        } else {
	            boolean containsMismatch = false;
	            for (int i = 0; i < s.length(); i++) {
	                if (lastWordProposedBinary.charAt(i) == '0' && s.charAt(i) != lastWordProposedBinary.charAt(i)) {
	                    iterator.remove(); // Supprimer le mot s'il contient une lettre absente dans le mot comparé
	                    containsMismatch = true;
	                    break;
	                }
	                if (lastWordProposedBinary.charAt(i) != '0' && lastWordProposedBinary.charAt(i) != '1' && 
	                    s.charAt(i) != lastWordProposedBinary.charAt(i)) {
	                    iterator.remove(); // Supprimer le mot s'il ne correspond pas à la séquence binaire
	                    containsMismatch = true;
	                    break;
	                }
	            }
	            if (!containsMismatch) {
	                for (int i = 0; i < s.length(); i++) {
	                    if (lastWordProposedBinary.charAt(i) == '0') {
	                        iterator.remove(); // Supprimer le mot s'il contient une lettre absente dans le mot comparé
	                        break;
	                    }
	                }
	            }
	        }
	    }
	}



	public String pickRandomWord(List<String> listOfWords) {
		Random random = new Random();
		int randomIndex = random.nextInt(listOfWords.size());
		return listOfWords.get(randomIndex);
	}
	
	public String comparerMots(String mot1, String mot2) {
	    StringBuilder resultat = new StringBuilder();

	    // Vérification de la longueur des mots
	    if (mot1.length() != mot2.length()) {
	        return "Les mots n'ont pas la même longueur";
	    }

	    // Comparaison caractère par caractère
	    for (int i = 0; i < mot1.length(); i++) {
	        if (mot1.charAt(i) == mot2.charAt(i)) {
	            resultat.append('*'); // Même caractère au même index
	        } else {
	            boolean memeCaractereTrouve = false;
	            for (int j = 0; j < mot2.length(); j++) {
	                if (mot1.charAt(i) == mot2.charAt(j)) {
	                    memeCaractereTrouve = true;
	                    break;
	                }
	            }
	            if (memeCaractereTrouve) {
	                resultat.append('%'); // Caractère trouvé dans l'autre mot
	            } else {
	                resultat.append('/'); // Caractère différent
	            }
	        }
	    }

	    return resultat.toString();
	}


}
