package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Server {
	public static ServerSocket serverSocket = null;

	public static HashMap<String, Socket> sockets = new HashMap<String, Socket>();
	public static Thread lifeThread = null;
	public Server() {
		startServer();
	}

	public void startServer() {

		try {
			serverSocket = new ServerSocket(6630);

			Thread acceptThread = new Thread(() -> {
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						DataInputStream dis = new DataInputStream(socket.getInputStream());

						lifeThread = new Thread(() -> {
							while (!socket.isClosed()) {
								try {
									String str = (String) dis.readUTF();
									System.out.println(str);
									verify(str, socket);
								} catch (IOException e) {
								}

							}
						});
						lifeThread.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			acceptThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verify(String message, Socket socket) {
		boolean create = true;
		String name;
		if (message.charAt(0) == ':') {
			System.out.println("connection : " + message);
		} else {
			name = "";
			for (int i = 14; i < message.length(); i++) {
				name += message.charAt(i);
			}

			if (MultiplayerConfig.addPlayers(name)) {
				System.out.println("name " + name);
				System.out.println("size 3: "+sockets.size());
				sockets.put(name, socket);
				System.out.println("size 4: "+sockets.size());
				sendMessage("ok!", socket);
				Room.clientsThread();
			} else if (sockets.get(name) != null) {
                  System.out.println("i am here");
			} else {
			
			try {
				sendMessage("exit", socket);
				socket.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	}

	public void close() {
		try {
			serverSocket.close();
			// socket.close();
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMessage(String message, Socket socket) {
		try {
			DataOutputStream a = new DataOutputStream(socket.getOutputStream());
			a.writeUTF(message);
			a.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMessageAll(String message) {
		System.out.println("size : "+sockets.size());
		for (int i = 0; i < sockets.size(); i++) {
			System.out.println("pizza : "+MultiplayerConfig.getPlayers().get(i+1));
			sendMessage(message, sockets.get(MultiplayerConfig.getPlayers().get(i+1)));
		}
	}
	public static void closePlayer(int i) {
		try {
			sendMessage("bara nayek !!",sockets.get(MultiplayerConfig.getPlayers().get(i-1)));
			sockets.get(MultiplayerConfig.getPlayers().get(i-1)).close();
			sockets.remove(MultiplayerConfig.getPlayers().get(i-1));
			MultiplayerConfig.getPlayers().remove(i-1);
		} catch (IOException e) {
		}
	}
}
