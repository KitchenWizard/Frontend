import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.net.*;

//Log In Window
//Will be displayed when the program first starts up and gives the user the option to either log in
//with their username and password or to hit the register button 

public class MainWindow extends JFrame implements ActionListener
{
	//Create all the elements that will be added to the pane
	protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JLabel usernameLabel;
    protected JLabel passwordLabel;
    protected JLabel login;
	public JButton confirm;
	public JButton register;
	public JLabel wrongPass;
	
	protected JLabel logo;
	
	protected String username;
	protected String password;
	protected String session;
	
	//Constructor
	public MainWindow(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		super(name);
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		this.getContentPane().setPreferredSize(new Dimension(800,450));
		
	}
	
	//Define and add all of the elements to the pane
	public void addComponentsToPane(Container pane)
	{
		//Set pane layout to null so we can define all of the locations ourselves
		pane.setLayout(null);
    	
		//Top bar of the program which will be constant throughout all of the screens with the KitchenWizard Logo and a horizontal line
    	logo=new JLabel("Kitchen Wizard");
    	logo.setBounds(0, 0, 118, 22);
    	pane.add(logo);
		
    	JSeparator line=new JSeparator(JSeparator.HORIZONTAL);
    	line.setBounds(0, 25, 800, 18);
    	pane.add(line);
    	
    	//Define login label
    	login=new JLabel("Please Log In");
    	login.setBounds(360, 50, 140, 30);
    	pane.add(login);
    	
    	//Define username label and field
    	usernameLabel=new JLabel("Username");
    	usernameLabel.setBounds(100,125, 100, 25);
    	pane.add(usernameLabel);
    	
    	usernameField=new JTextField(25);
        usernameField.setBounds(165, 125, 150, 25);
        pane.add(usernameField);
        
        //Define password label and field
        passwordLabel=new JLabel("Password");
        passwordLabel.setBounds(350, 125, 100, 25);
        pane.add(passwordLabel);
        
        passwordField=new JPasswordField(25);
        passwordField.setBounds(415, 125, 150, 25);
        pane.add(passwordField);
        
        //Define confirmation button to send the data from username and password fields
    	confirm=new JButton("Go");
    	confirm.addActionListener(this);
    	confirm.setBounds(600, 125, 75, 25);
    	pane.add(confirm);
    	
    	//Define the Wrong Username or Password label that will show up if the username or password is incorrect
    	//Originally set to invisible
    	wrongPass=new JLabel("Incorrect username or password");
    	wrongPass.setVisible(false);
    	wrongPass.setBounds(300,160,300,50);
    	pane.add(wrongPass);
    	
    	//Define register button
    	register=new JButton("Register");
    	register.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			//Open the RegisterWindow on click
    			RegisterWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	register.setBounds(325, 240, 150, 40);
    	pane.add(register);
		
    	//Define the bottom bar of the program which is constant throughout.
    	//On this screen, it is just a horizontal line
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
	}
	
	//Define actionPerformed which is called when the confirm button is clicked
	//Get the username and password from their fields and try to send them to the database
	//If successful, a session key will be returned and if the session key is not INVALID_LOGIN, the HomeWindow is created
	//and the session key passed to it
	//Else the Wrong Username or Password label will be set to visible
	public void actionPerformed(ActionEvent e) 
	{
		username=usernameField.getText();
		password=new String(passwordField.getPassword());
		try {
			sendRequest();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String invalid="INVALID_LOGIN";
		System.out.println(session.equalsIgnoreCase(invalid));
		if(session.equalsIgnoreCase(invalid))
		{
			wrongPass.setVisible(true);
			System.out.println("NO");
		}
		else
		{
			HomeWindow.createAndShowGUI(session);
			dispose();
		}
	}
	
	//createAndShowGUI method which will create the frame and add the pane
	static void createAndShowGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
	        //Create and set up the window.
	        MainWindow frame = new MainWindow("Kitchen Wizard - Please Log In");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocation(0,0);
	        //Set up the content pane.
	        frame.addComponentsToPane(frame.getContentPane());
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	}

	//main which will call createAndShowGUI
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		createAndShowGUI();
	}
	
	//sendRequest which will attempt to connect to the database and send a GET request with the login command, username, and hashed password as headers.
	//It will receive back a session key if successful and INVALID_LOGIN if failed
	public void sendRequest() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="login";
		String param1=username;
		int hash=password.hashCode();
		String param2=hash+"";
		
		String query=String.format("command=%s&username=%s&password=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(param1,charset),
				URLEncoder.encode(param2,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			session=inputLine;
			System.out.println(session);
		in.close();
	}
}