package application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MultiplayerConfig {
	private static MultiplayerConfig INSTANCE = null;
	private static ArrayList<String> players = new ArrayList<String>();
	private String admin;
	private Server server;
	private MainPageMultiplayer mainpage;
	public static String[] WordsTable;
    public static int partiesnumber=0;
	private MultiplayerConfig() {

	}

	public static MultiplayerConfig getINSTANCE() {
		return INSTANCE;
	}

	public static ArrayList<String> getPlayers() {
		return players;
	}

	public static boolean addPlayers(String player) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(player))
				return false;
		}
		players.add(player);
		return true;
	}

	public Server getServer() {
		return server;
	}

	public static MultiplayerConfig create() {

		if (INSTANCE == null) {
			INSTANCE = new MultiplayerConfig();
		}
		return INSTANCE;
	}

	public void createServer(String admin) {
		this.admin = admin;
		players.add(admin);
		server = new Server();
	}

	public static Scene startGame(MainPageMultiplayer mainpage, int taille, int parties) {

		/*
		 * Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
		 * jeu.tempsEcoule += 1000; Date now = new Date(jeu.tempsEcoule);
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("mm:ss"); String formattedTime =
		 * sdf.format(now); this.labelsOfHead[3].setText(formattedTime);
		 * 
		 * }));
		 */
		partiesnumber=parties;
		
		String words = "";
		WordsTable = new String[parties];
		System.out.println("eeeeeeeeeeeee");

		for (int i = 0; i < parties; i++) {
			String a = mainpage.jeu.getRandomWord(taille);
			words = words + "+" + a;
			WordsTable[i] = a;
		}
		System.out.println("words "+words);

		Scene scene = new Scene(mainpage, 750, 700);
		mainpage.jeu.longueurMotParDefault = taille;
		mainpage.jeu.resetScoreWhenLoose();
		mainpage.jeu.lancerTimer();
		mainpage.jeu.tempsEcoule = 0;
		mainpage.clearTimerCount();
		mainpage.InitialtimerCount();
		mainpage.timerCount(0);
		mainpage.multiplayer = parties;
		mainpage.restore(taille);
		Server.sendMessageAll("-"+taille+"-"+parties+"-" + words);
		mainpage.desactive();
		return scene;

	}

	public String getAdmin() {
		return admin;
	}

}
