package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Server {
	public static ServerSocket serverSocket=null;

	public static Socket socket=null;
	public static Thread lifeThread=null;

	public Server() {
		startServer();
	}

	public void startServer() {
		try {
			serverSocket = new ServerSocket(6671);

			Thread acceptThread = new Thread(() -> {
				while(true) {
				try {
					socket = serverSocket.accept();
				
			DataInputStream dis = new DataInputStream(socket.getInputStream());

			lifeThread = new Thread(() -> {
				while (!socket.isClosed()) {
					try {
						String str = (String) dis.readUTF();
						System.out.println(str);
						verify(str);
					} catch (IOException e) {
						e.printStackTrace();
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

	public void verify(String message) {
		boolean create=true;
		String name;
		if (message.charAt(0) == ':') {
       System.out.println("connection : "+message);
		}else {
			 name="";
			for(int i=2;i<message.length();i++) {
				name+=message.charAt(i);
			}
			for(int i=0;i<Room.clients.size();i++) {
				if(Room.clients.get(i).name.equals(name)) {
					create=false;
				}
			}
			
			String nameplayer=name;
			if(create) {
				Room.clients.add(new Client(name));
				Room.clientsThread();
			}
			
		}
	}

	public void close() {
		try {
			serverSocket.close();
			socket.close();
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendMessage(String message) {
		try {
			DataOutputStream a=new DataOutputStream(socket.getOutputStream());
			a.writeUTF(message);
            a.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
