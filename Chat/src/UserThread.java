import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 * @author www.codejava.net
 */
public class UserThread extends Thread {
	private Socket socket;
	private ChatServer server;
	private PrintWriter writer;
	private User user;
	private Socket socket1;
	public UserThread(Socket socket, ChatServer server, User user) {
		this.socket = socket;
		this.server = server;
		this.user = user;
	}

	@SuppressWarnings("null")
	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			printUsers();

			String userName = user.getUsername();
			server.addUserName(userName);

			String serverMessage = "New user connected: " + userName;
			server.broadcast(serverMessage, this);

			String clientMessage;
			ServerSocket serverSocket = new ServerSocket(27501);
			do {
				try {

					while(true) {
						try {
							socket1 = serverSocket.accept();
							InputStream inputStream = socket1.getInputStream();
							// create a DataInputStream so we can read data from it.
							ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
							
							clientMessage = (String) objectInputStream.readObject();
							userName = (String) objectInputStream.readObject();
							serverMessage = "[" + userName + "]: " + clientMessage;
							OutputStream outputStream;
							ObjectOutputStream objectOutputStream;

							outputStream = socket1.getOutputStream();
							// create an object output stream from the output stream so we can send an object through it
							objectOutputStream = new ObjectOutputStream(outputStream);
							objectOutputStream.writeObject(serverMessage);
							objectOutputStream.writeObject(this.user.getUsername());
						} catch (IOException | ClassNotFoundException e) {
							e.printStackTrace();
						} 
					}

				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

				//				server.broadcast(serverMessage, this);

			} while (serverMessage != null || !serverMessage.equals("bye"));

			server.removeUser(userName, this);
			socket.close();

			serverMessage = userName + " has quitted.";
			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Sends a list of online users to the newly connected user.
	 */
	void printUsers() {
		if (server.hasUsers()) {
			writer.println("Connected users: " + server.getUserNames());
		} else {
			writer.println("No other users connected");
		}
	}

	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		//		writer.println(message);
		try(ServerSocket serverSocket = new ServerSocket(27501)) {
			socket = new Socket("localhost", 27501);
			System.out.println("Connected to the chat server");
			Socket socket1 = serverSocket.accept();

			OutputStream outputStream;
			ObjectOutputStream objectOutputStream;

			outputStream = socket1.getOutputStream();
			// create an object output stream from the output stream so we can send an object through it
			objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(message);
			objectOutputStream.writeObject(this.user.getUsername());

		} catch (NumberFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}