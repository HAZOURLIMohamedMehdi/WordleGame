package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client {
	public String name;
	public String ip;
	public boolean status = false; // false -> down // true -> up
	public Socket socket;
	public Thread lifeThread;
	public DataOutputStream messageLife;
	public static String[] words;
	public static MainPageMultiplayer mainGame;
	public Client(String name) {
		this.name = name;

	}

	public void startConnection() {

		try {
			socket = new Socket("10.120.10.215", 6670);

			messageLife = new DataOutputStream(socket.getOutputStream());

			status = true;
			lifeThread = new Thread(() -> {
				while (status && socket != null && !socket.isClosed()) {
					try {

						Thread.sleep(1000);
						messageLife.writeUTF("utilisateur : " + name);
						messageLife.flush();

					} catch (Exception e) {

					}
				}

			});
			lifeThread.start();

			DataInputStream dis;
			try {
				dis = new DataInputStream(socket.getInputStream());
				String text = dis.readUTF();
				System.out.println("le mot : " + text);
				verify(text);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Thread azze = new Thread(() -> {
				if(!socket.isClosed()) {
				DataInputStream diss;
				while (socket != null && !socket.isClosed()) {
					try {
						diss = new DataInputStream(socket.getInputStream());
						String text = diss.readUTF();
						System.out.println("le mot : " + text);
						verify(text);
					} catch (Exception e) {
						Main.sc.setRoot(Main.acceuilpage);
						Main.stage.setScene(Main.sc);
					}
				}
			}});
			azze.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			Thread.currentThread().interrupt();
			status = false;
			messageLife.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			DataOutputStream sendMessage = new DataOutputStream(socket.getOutputStream());

			sendMessage.writeUTF(":" + message);
		} catch (Exception e) {

		}

	}

	public void verify(String message) {
		System.out.println("ffffff");
		if (message.equals("exit")) {
			close();
		} else if (message.equals("ok!")) {

		} else if(message.charAt(0)=='-'){
			
			int i=1;
			String test="";
			for(;message.charAt(i)!='-';i++) {
				test+=message.charAt(i);
			}
			int taille=Integer.parseInt(test);
			 test="";
			for(i=i+1;message.charAt(i)!='-';i++) {
				test+=message.charAt(i);
			}
			int parties=Integer.parseInt(test);
			 words=new String[parties];
			 i++;
            for(int j=0;j<parties;j++) {
            	words[j]="";
            	  for(i=i+1;i<message.length() && message.charAt(i)!='+';i++) {
                  	words[j]+=message.charAt(i);
                  }
                	System.out.println("words : "+words[j]);

            }
			
			Platform.runLater(() -> {
 
				LoseMultiplayer c= new LoseMultiplayer();
				 mainGame = new MainPageMultiplayer(5, new Wordle(),c);
				 c.ShowWord(mainGame,taille);
				try {
					Scene scene = new Scene(mainGame, 750, 700);
					mainGame.jeu.longueurMotParDefault = taille;

					mainGame.jeu.resetScoreWhenLoose();
					mainGame.jeu.lancerTimer();
					mainGame.jeu.tempsEcoule = 0;
					mainGame.clearTimerCount();
					mainGame.InitialtimerCount();
					mainGame.timerCount(0);
					mainGame.multiplayer=parties;
					mainGame.multiplayerstart();
					mainGame.restore(taille);
					mainGame.jeu.motChercher = words[0];
					mainGame.desactive();
					Main.stage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}else {
			
		}
	}
}
