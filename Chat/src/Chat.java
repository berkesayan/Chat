import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField portTxt;
	private JTextField ipAdresstxt;
	private int port = 0;
	private String ipAdress = "";
	private ChatServer Server;
	private Set<String> username = new HashSet<>();;
	private static Chat chat;
	private JTextArea textArea;
	private User user ;
	private Socket socket;
	public Set<String> getUsername() {
		return username;
	}

	public void setUsername(Set<String> username) {
		this.username = username;
	}
	public void addUsername(String username) {
		this.username.add(username);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
					frame.setVisible(true);
					chat = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void SetUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	/**
	 * Create the frame.
	 */
	public void add() {
		textArea.setText("");
		for (String string : username) {
			textArea.append(string + "\n");
		}
	}
	public void remove(String user) {
		if(username.remove(user)) {
			textArea.setText( textArea.getText().replaceAll( user, "" ) );
		}
	}
	public Chat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setRows(10);
		textArea.setBounds(10, 27, 197, 200);
		contentPane.add(textArea);
		
		

		portTxt = new JTextField();
		portTxt.setBounds(111, 237, 96, 19);
		contentPane.add(portTxt);
		portTxt.setColumns(10);
		portTxt.setText("8989");
		
		JLabel lblNewLabel = new JLabel("Port");
		lblNewLabel.setBounds(20, 237, 81, 19);
		contentPane.add(lblNewLabel);

		ipAdresstxt = new JTextField();
		ipAdresstxt.setBounds(111, 258, 96, 19);
		contentPane.add(ipAdresstxt);
		ipAdresstxt.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Ip Adress");
		lblNewLabel_1.setBounds(10, 266, 91, 13);
		contentPane.add(lblNewLabel_1);

		JButton btnConnection = new JButton("Connect");
		btnConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				port = Integer.parseInt(portTxt.getText());
				ipAdress = ipAdresstxt.getText();
				
				user = new User();
				new Thread().start();
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						Server = new ChatServer(port,chat);
						Server.execute();
					}
				});

				thread.start();
				

			}
		});

		btnConnection.setBounds(111, 287, 96, 21);
		contentPane.add(btnConnection);



	}

}
