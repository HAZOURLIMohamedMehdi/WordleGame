package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Mediumbot extends Bot {

	public Mediumbot() {
		this.wordsPossible = new ArrayList<String>();
		this.wordsProposed = new ArrayList<String>();
	}

	public void playFirstTry(int numberOfLetters) {
		this.wordsPossible = Parseur.getListeMotsDeLongueur(numberOfLetters);
		Random random = new Random();
		int randomIndex = random.nextInt(this.wordsPossible.size());
		String randomWord = wordsPossible.get(randomIndex);
		System.out.println("Le bot this joue le mot : " + randomWord);
		this.wordsProposed.add(randomWord);
		this.wordsPossible.remove(randomWord);
	}
	
	public void playNextTime() {
	    if (!wordsPossible.isEmpty()) {
	        Random random = new Random();
	        int randomIndex = random.nextInt(this.wordsPossible.size());
	        String randomWord = wordsPossible.get(randomIndex);
	        System.out.println("Le bot this joue le mot : " + randomWord);
	        this.wordsProposed.add(randomWord);
	        this.wordsPossible.remove(randomWord);
	    } else {
	        System.out.println("La liste des mots possibles est vide.");
	    }
	}



	public void banWords(String lastGuess, String lastGuessResult) {
	    Iterator<String> iterator = wordsPossible.iterator();
	    while (iterator.hasNext()) {
	        String word = iterator.next();
	        for (int i = 0; i < lastGuessResult.length(); i++) {
	            char resultChar = lastGuessResult.charAt(i);
	            char guessChar = lastGuess.charAt(i);

	            if (resultChar == '/' && word.charAt(i) == guessChar) {
	                iterator.remove();
	                break;
	            }       
	            if (resultChar == '%' && word.charAt(i) == guessChar) {
	                iterator.remove();
	                break;
	            }
	            if (resultChar == '*' && word.charAt(i) != guessChar) {
	                iterator.remove();
	                break;
	            }
	        }
	    }
	}




	public String comparer(String motChercher, String motChoisiParLeJoueur) {
		StringBuffer motChercherBuffer = new StringBuffer(motChercher);
		StringBuffer motChoisiParLeJoueurBuffer = new StringBuffer(motChoisiParLeJoueur);
		Wordle wordle = new Wordle();

		if (wordle.wordExistsInTheLocalDictionnary(motChoisiParLeJoueur)
				|| wordle.wordExistsInTheParserDictionnary(motChoisiParLeJoueur, wordle.longueurMotParDefault)) {

			if (motChercher.equals(motChoisiParLeJoueur)) {
				wordle.indiceUtilise = true;
				for (int i = 0; i < motChoisiParLeJoueur.length(); i++) {
					motChoisiParLeJoueurBuffer.setCharAt(i, '*');
				}
			} else {
				for (int i = 0; i < motChercher.length(); i++) {
					if (motChoisiParLeJoueur.charAt(i) == motChercher.charAt(i)) {
						motChoisiParLeJoueurBuffer.setCharAt(i, '*');
						motChercherBuffer.setCharAt(i, '/');
					}
				}
				for (int i = 0; i < motChercher.length(); i++) {
					for (int j = 0; j < motChercher.length(); j++) {
						if (motChoisiParLeJoueur.charAt(i) == motChercher.charAt(j)
								&& motChercherBuffer.charAt(j) != '/') {
							motChoisiParLeJoueurBuffer.setCharAt(i, '%');
							motChercherBuffer.setCharAt(j, '/');
						}
					}
				}
			}
		}
		return motChoisiParLeJoueurBuffer.toString().replaceAll("[A-Z]", "/");
	}
	
	
	public void playAgainstMediumBot(int numberOfLetters, String wordToGuess)
	{
		System.out.println("Tentative initiale aléatoire") ;
		this.playFirstTry(numberOfLetters);
		//System.out.println(this.wordsPossible) ;

		int i = 0;
		int j = i + 1 ;
		while (this.wordsPossible.size() >= 1 && this.wordsPossible.contains(wordToGuess)) {
			
			if (this.wordsPossible.size()==1)
			{
				System.out.println(j +"-ème tentative");
				System.out.println("Le bot a trouver le mot par défault : " + this.wordsPossible.get(0)) ;
				break ; 
			}
			
		    System.out.println(j +"-ème tentative");
		    String wordComparison = this.comparer(wordToGuess, this.wordsProposed.get(i));
		    String lastGuess = this.wordsProposed.get(i);
		    String lastGuessResult = wordComparison;
		    this.banWords(lastGuess, lastGuessResult);
		    this.playNextTime();
		    i++;
		    j++;
		    //System.out.println(this.wordsPossible);
		}
	}
	
	public int playAgainstMediumBotAnalysis(int numberOfLetters, String wordToGuess)
	{
		System.out.println("Tentative initiale aléatoire") ;
		this.playFirstTry(numberOfLetters);
		//System.out.println(this.wordsPossible) ;

		int i = 0;
		int j = i + 1 ;
		while (this.wordsPossible.size() >= 1 && this.wordsPossible.contains(wordToGuess)) {
			
			if (this.wordsPossible.size()==1)
			{
				System.out.println(j +"-ème tentative");
				System.out.println("Le bot a trouver le mot par défault : " + this.wordsPossible.get(0)) ;
				break ; 
			}
			
		    System.out.println(j +"-ème tentative");
		    String wordComparison = this.comparer(wordToGuess, this.wordsProposed.get(i));
		    String lastGuess = this.wordsProposed.get(i);
		    String lastGuessResult = wordComparison;
		    this.banWords(lastGuess, lastGuessResult);
		    this.playNextTime();
		    i++;
		    j++;
		    //System.out.println(this.wordsPossible);
		}
		return i ;
	}
	
	public ArrayList<Integer> botAnalysis(int numberOfGames, int numberOfLetters)
	{
		ArrayList<Integer> analysis = new ArrayList<Integer>();
		
		while (numberOfGames>=0)
		{
			this.wordsPossible = Parseur.getListeMotsDeLongueur(numberOfLetters);
			Random random = new Random();
			int randomIndex = random.nextInt(this.wordsPossible.size());
			String randomWord = wordsPossible.get(randomIndex);
			
			analysis.add(playAgainstMediumBotAnalysis(numberOfLetters, randomWord)) ;		
			numberOfGames--;
		}
		
		double mean = 0 ;
		for (int k : analysis)
		{
			mean = mean + k ;
		}
		mean = mean/analysis.size();
		System.out.println("Moyenne : " + mean) ;
		return analysis ;		
	}
	
	
	

}
