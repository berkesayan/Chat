import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Client extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField usernametext;
	private ChatClient client;
	private static Client user;
	private JTextField hostnametext;
	private JTextField porttxt;
	private PrintWriter writer ;
	private Socket socket;
	private BufferedReader reader;
	private JTextArea textArea;
	private User user1;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private Socket socket1 ;
	public String getUserName() {
		return usernametext.getText();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
					user = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void send(Socket socket) {
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void ReadThread() throws IOException  {

		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
		while (true) {
			try {
				if(reader.ready()) {
					String response = reader.readLine();
					//				System.out.println("\n" + response);
					textArea.append("\n" + response);
					// prints the username after displaying the server's message
					if (usernametext.getText() != "" || usernametext.getText() != null) {
						//					System.out.print("[" + client.getUserName() + "]: ");
						textArea.append("[" + usernametext.getText() + "]: " + "\n");
					}
				}
			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} finally {
				socket.close();
			}
		}

	}
	/**
	 * Create the frame.
	 */
	public Client() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(40, 53, 208, 212);
		contentPane.add(textArea);

		textField = new JTextField();
		textField.setBounds(40, 275, 208, 47);
		contentPane.add(textField);
		textField.setColumns(10);



		usernametext = new JTextField();
		usernametext.setBounds(400, 69, 96, 19);
		contentPane.add(usernametext);
		usernametext.setColumns(10);

		JLabel usernamelbl = new JLabel("Username");
		usernamelbl.setBounds(322, 72, 68, 13);
		contentPane.add(usernamelbl);

		JLabel lblNewLabel = new JLabel("hostname");
		lblNewLabel.setBounds(323, 42, 45, 13);
		contentPane.add(lblNewLabel);

		hostnametext = new JTextField();
		hostnametext.setBounds(400, 39, 96, 19);
		contentPane.add(hostnametext);
		hostnametext.setColumns(10);
		hostnametext.setText("localhost");

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.append("[" + usernametext.getText() + "]: " + textField.getText() + "\n" );
				OutputStream outputStream;
				ObjectOutputStream objectOutputStream;

				try {
					outputStream = socket1.getOutputStream();
					// create an object output stream from the output stream so we can send an object through it
					objectOutputStream = new ObjectOutputStream(outputStream);
					objectOutputStream.writeObject(textField.getText());
					objectOutputStream.writeObject(usernametext.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(textField.getText() == "bye") {
					try {
						socket.close();
					} catch (IOException ex) {
						System.out.println("Error writing to server: " + ex.getMessage());
					}
				}
			}
		});
		btnNewButton.setBounds(40, 342, 85, 21);
		contentPane.add(btnNewButton);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//				client = new ChatClient(hostnametext.getText(),Integer.parseInt(porttxt.getText()),usernametext.getText());
				//				client.execute(user);
				try(ServerSocket serverSocket = new ServerSocket(27500)) {
					socket = new Socket(hostnametext.getText(), Integer.parseInt(porttxt.getText()));
					System.out.println("Connected to the chat server");
					Socket socket1 = serverSocket.accept();

					OutputStream outputStream;
					ObjectOutputStream objectOutputStream;

					outputStream = socket1.getOutputStream();
					// create an object output stream from the output stream so we can send an object through it
					objectOutputStream = new ObjectOutputStream(outputStream);
					objectOutputStream.writeObject(usernametext.getText());

				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				try {
					OutputStream output = socket.getOutputStream();
					writer = new PrintWriter(output, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				btnEnter.disable();
				btnEnter.enable(false);
				new Thread().start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							ReadThread();
						} catch (IOException e) {
							System.out.println("kod 173'da hata var");
							e.printStackTrace();
						}
					}
				}).start();
				
				new Thread().start();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						while (true)
						try {
							socket1 = new Socket("localhost",27501);
							InputStream inputStream = socket1.getInputStream();
							// create a DataInputStream so we can read data from it.
							ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
							//user = (User) objectInputStream.readObject();
							String message = (String) objectInputStream.readObject();
							String userName = (String) objectInputStream.readObject();
							
							textArea.append(userName + " " + message + "\n");
						} catch (IOException | ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

					}
				}).start();
			}
		});

		
		btnEnter.setBounds(400, 155, 85, 21);
		contentPane.add(btnEnter);

		porttxt = new JTextField();
		porttxt.setBounds(400, 100, 96, 19);
		contentPane.add(porttxt);
		porttxt.setColumns(10);
		porttxt.setText("8989");

		JLabel portlbl = new JLabel("port");
		portlbl.setBounds(332, 103, 45, 13);
		contentPane.add(portlbl);

	}
}
