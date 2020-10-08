import java.net.*;
import java.io.*;
 
/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
 
    public ChatClient(String hostname, int port, String username) {
        this.hostname = hostname;
        this.port = port;
        this.userName = username;
    }
    
    public void execute(Client user) {
        try {
            Socket socket = new Socket(hostname, port);
 
            System.out.println("Connected to the chat server");
            
//            ReadThread thread1 = new ReadThread(socket, this);
//            WriteThread thread = new WriteThread(socket, this, userName);
//            thread1.start();
//            thread.start();
            user.send(socket);
            
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    void setUserName(String userName) {
        this.userName = userName;
    }
 
    String getUserName() {
        return this.userName;
    }
 
 
    public static void main(String[] args) {
//        if (args.length < 2) return;
// 
//        String hostname = args[0];
//        int port = Integer.parseInt(args[1]);
    	String hostname = "localhost";
    	int port = 8989;
        ChatClient client = new ChatClient(hostname, port, "Berke");
//        client.execute();
    }
}