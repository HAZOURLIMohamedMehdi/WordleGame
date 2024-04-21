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

	public Client(String name) {
		this.name = name;

	}

	public void startConnection() {

		try {
			socket = new Socket("192.168.62.143", 6671);

			messageLife = new DataOutputStream(socket.getOutputStream());

			status = true;
			lifeThread = new Thread(() -> {
				while (status) {
					try {
						Thread.sleep(1000);
						messageLife.writeUTF("hi" + name);
						messageLife.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
			lifeThread.start();
			Thread Thread2 = new Thread(() -> {
				DataInputStream dis;
				while (!socket.isClosed()) {
					try {
						dis = new DataInputStream(socket.getInputStream());
						String text=dis.readUTF();
						System.out.println("le mot : "+text);
						verify(text);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
			Thread2.start();
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

Platform.runLater(() -> {

			MainPage mainGame = new MainPage(5, new Wordle(), null);
			try {
				Scene scene = new Scene(mainGame, 750, 700);
				mainGame.jeu.longueurMotParDefault = 5;

				mainGame.jeu.resetScoreWhenLoose();
				mainGame.jeu.lancerTimer();
				mainGame.jeu.tempsEcoule = 0;
				mainGame.clearTimerCount();
				mainGame.InitialtimerCount();
				mainGame.timerCount(0);
				mainGame.restore(5);

				mainGame.jeu.motChercher = message;
				mainGame.desactive();
				Main.stage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
