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
	
	public RegisterWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,450));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(null);
    	
    	logo=new JLabel("Kitchen Wizard");
    	logo.setBounds(0, 0, 118, 22);
    	pane.add(logo);
		
    	JSeparator line=new JSeparator(JSeparator.HORIZONTAL);
    	line.setBounds(0, 25, 800, 18);
    	pane.add(line);
    	
    	//JSeparator line2=new JSeparator(JSeparator.VERTICAL);
    	//line2.setBounds(399, 0, 3, 480);
    	//pane.add(line2);
    	
    	fNameLabel=new JLabel("First Name: ");
    	fNameLabel.setBounds(100,100,100,25);
    	pane.add(fNameLabel);
    	
    	fName=new JTextField(25);
    	fName.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			fname=fName.getText();
    		}
    	});
    	fName.setBounds(170,100,150,25);
    	pane.add(fName);
    	
    	lNameLabel=new JLabel("Last Name: ");
    	lNameLabel.setBounds(100,130,100,25);
    	pane.add(lNameLabel);
    	
    	lName=new JTextField(25);
    	lName.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			lname=lName.getText();
    		}
    	});
    	lName.setBounds(170,130,150,25);
    	pane.add(lName);
    	
    	emailLabel=new JLabel("Email: ");
    	emailLabel.setBounds(100,160,100,25);
    	pane.add(emailLabel);
    	
    	emailField=new JTextField(25);
    	emailField.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			email=emailField.getText();
    		}
    	});
    	emailField.setBounds(170,160,150,25);
    	pane.add(emailField);
    	
    	usernameLabel=new JLabel("Username: ");
    	usernameLabel.setBounds(100,190, 100, 25);
    	pane.add(usernameLabel);
    	
    	usernameField=new JTextField(25);
        usernameField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        	}
        });
        usernameField.setBounds(170, 190, 150, 25);
        pane.add(usernameField);
        
        passwordLabel=new JLabel("Password: ");
        passwordLabel.setBounds(100, 220, 100, 25);
        pane.add(passwordLabel);
        
        passwordField=new JPasswordField(25);
        passwordField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        	}
        });
        passwordField.setBounds(170, 220, 150, 25);
        pane.add(passwordField);
    	
    	register=new JButton("Register");
    	register.addActionListener(new ActionListener()
    	{
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
    	register.setBounds(600, 200, 150, 40);
    	pane.add(register);
    	
    	back=new JButton("Back");
		back.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		
        		try {
					MainWindow.createAndShowGUI();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		dispose();
        	}
        });
		back.setBounds(5, 423, 75, 25);
		pane.add(back);
		
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
    	
	}
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
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

	public static void main(String args[])
	{
		createAndShowGUI();
	}
	
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
