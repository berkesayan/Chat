import java.io.*;
import java.net.*;
import java.util.Scanner;
 
/**
 * This thread is responsible for reading user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
    private String username;
    
    public WriteThread(Socket socket, ChatClient client, String username) {
        this.socket = socket;
        this.client = client;
        this.username = username;
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run(Client user) {
 
//        Console console = System.console();
//    	System.out.println("\n Enter your name");
//    	Scanner keyboard = new Scanner(System.in);
//        String userName = console.readLine("\nEnter your name: ");
//    	String userName = keyboard.nextLine();
//        client.setUserName(userName);
        writer.println(username);
        
        String text;
        
//        do {
//          text = console.readLine("[" + userName + "]: ");
//          writer.println(text);
        	
//        	System.out.println("[" + username + "]: ");
//        	text = keyboard.nextLine();
        	
//        	writer.println(text);
//        } while (!text.equals("bye"));
 
//        try {
//            socket.close();
//        } catch (IOException ex) {
// 
//            System.out.println("Error writing to server: " + ex.getMessage());
//        }
    }
}