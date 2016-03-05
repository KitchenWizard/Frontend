import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import java.net.*;


public class RegisterWindow extends JFrame implements ActionListener
{
	//Create components that will be added later
	protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JLabel usernameLabel;
    protected JLabel passwordLabel;
	public JButton register;
	protected JTextField fName;
	protected JTextField lName;
	protected JTextField emailField;
	protected JLabel fNameLabel;
	protected JLabel lNameLabel;
	protected JLabel emailLabel;
	
	protected JLabel logo;
	protected JButton back;
	
	//PASS IN THIS ORDER
	protected String username;
	protected String fname;
	protected String lname;
	protected String email;
	protected String password;
	protected String hash;
	
	protected static String session;
	
	//RegisterWindow 
	//Will allow the user to type in their information to be able to register for an account
	//They will have to type their first name, last name, email, and a username and password
	//Then they can press the confirm button and the information will be sent to the database
	
	public RegisterWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,450));
		
	}
	
	//Add the components to the pane
	public void addComponentsToPane(Container pane)
	{
		//Set the layout to null so we can play the components by ourselves
		pane.setLayout(null);
    	
		//Create the top bar which is common across all the screens
    	logo=new JLabel("Kitchen Wizard");
    	logo.setBounds(0, 0, 118, 22);
    	pane.add(logo);
		
    	JSeparator line=new JSeparator(JSeparator.HORIZONTAL);
    	line.setBounds(0, 25, 800, 18);
    	pane.add(line);
    	
    	//Create the label and field for the First Name
    	fNameLabel=new JLabel("First Name: ");
    	fNameLabel.setBounds(100,105,100,30);
    	pane.add(fNameLabel);
    	
    	fName=new JTextField(25);
    	fName.setBounds(170,105,150,30);
    	pane.add(fName);
    	
    	//Create the label and field for the Last Name
    	lNameLabel=new JLabel("Last Name: ");
    	lNameLabel.setBounds(100,135,100,30);
    	pane.add(lNameLabel);
    	
    	lName=new JTextField(25);
    	lName.setBounds(170,135,150,30);
    	pane.add(lName);
    	
    	//Create the label and field for Email
    	emailLabel=new JLabel("Email: ");
    	emailLabel.setBounds(100,165,100,30);
    	pane.add(emailLabel);
    	
    	emailField=new JTextField(25);
    	emailField.setBounds(170,165,150,30);
    	pane.add(emailField);
    	
    	//Create the label and field for Username
    	usernameLabel=new JLabel("Username: ");
    	usernameLabel.setBounds(100,195, 100, 30);
    	pane.add(usernameLabel);
    	
    	usernameField=new JTextField(25);
        usernameField.setBounds(170, 195, 150, 30);
        pane.add(usernameField);
        
        //Create the label and field for Password
        passwordLabel=new JLabel("Password: ");
        passwordLabel.setBounds(100, 225, 100, 30);
        pane.add(passwordLabel);
        
        passwordField=new JPasswordField(25);
        passwordField.setBounds(170, 225, 150, 30);
        pane.add(passwordField);
    	
        //Create the register button
    	register=new JButton("Register");
    	register.addActionListener(new ActionListener()
    	{
    		//Define the function for when the button is pressed
    		//Get the text from each of the fields and send all of it to the database
    		public void actionPerformed(ActionEvent e)
    		{
        		fname=fName.getText();
        		lname=lName.getText();
        		email=emailField.getText();
        		username=usernameField.getText();
        		password=new String(passwordField.getPassword());
    			try {
					sendRequest();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
    	register.setBounds(600, 200, 150, 40);
    	pane.add(register);
    	
    	//Create the back button which will lead back to the MainWindow
    	back=new JButton("Back");
		back.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		try {
					MainWindow.createAndShowGUI();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
        		dispose();
        	}
        });
		back.setBounds(5, 423, 75, 25);
		pane.add(back);
		
		//Add in the bottom horizontal line
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
    	
	}
	
	//Method required but not used
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	//createAndShowGUI which will set up the frame and add all of the components
	static void createAndShowGUI() 
	{
	        //Create and set up the window.
	        RegisterWindow frame = new RegisterWindow("Kitchen Wizard - Register");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //Set up the content pane.
	        frame.addComponentsToPane(frame.getContentPane());
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	}
	
	//main which will run createAndShowGUI
	public static void main(String args[])
	{
		createAndShowGUI();
	}
	
	//sendRequest which will attempt to connect to the database and send the first name, last name, username,
	//hashed password, and email.
	//It will receive a message if the account is created or not
	public void sendRequest() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="register";
		String param1=username;
		String param2=fname;
		String param3=lname;
		String param4=email;
		int hash=password.hashCode();
		String param5=hash+"";
		
		String query=String.format("command=%s&username=%s&fname=%s&lname=%s&email=%s&password=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(param1,charset),
				URLEncoder.encode(param2,charset),
				URLEncoder.encode(param3,charset),
				URLEncoder.encode(param4,charset),
				URLEncoder.encode(param5,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
}
