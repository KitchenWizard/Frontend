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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.net.*;

public class MainWindow extends JFrame implements ActionListener
{
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
	protected char[] correctPass={'f','f','f'};
	
	public MainWindow(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		super(name);
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
    	
    	login=new JLabel("Please Log In");
    	login.setBounds(360, 50, 140, 30);
    	pane.add(login);
    	
    	usernameLabel=new JLabel("Username");
    	usernameLabel.setBounds(100,125, 100, 25);
    	pane.add(usernameLabel);
    	
    	usernameField=new JTextField(25);
        usernameField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		username=usernameField.getText();
        	}
        });
        usernameField.setBounds(165, 125, 150, 25);
        pane.add(usernameField);
        
        passwordLabel=new JLabel("Password");
        passwordLabel.setBounds(350, 125, 100, 25);
        pane.add(passwordLabel);
        
        passwordField=new JPasswordField(25);
        passwordField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		password=new String(passwordField.getPassword());
        	}
        });
        passwordField.setBounds(415, 125, 150, 25);
        pane.add(passwordField);
        
    	confirm=new JButton("Go");
    	confirm.addActionListener(this);
    	confirm.setBounds(600, 125, 75, 25);
    	pane.add(confirm);
    	
    	wrongPass=new JLabel("Incorrect username or password");
    	wrongPass.setVisible(false);
    	wrongPass.setBounds(300,160,300,50);
    	pane.add(wrongPass);
    	
    	register=new JButton("Register");
    	register.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			RegisterWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	register.setBounds(325, 240, 150, 40);
    	pane.add(register);
		
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
    	
    	
    	
	}
	public void actionPerformed(ActionEvent e) 
	{
		username=usernameField.getText();
		password=new String(passwordField.getPassword());
		try {
			sendRequest();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(true)
		{
			HomeWindow.createAndShowGUI();
			dispose();
		}
		else
		{
			wrongPass.setVisible(true);
		}
		
	}
	
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

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		createAndShowGUI();
	}
	
	public void sendRequest() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="login";
		String param1=username;
		String param2=password;
		
		String query=String.format("command=%s&username=%s&password=%s",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(param1,charset),
				URLEncoder.encode(param2,charset),
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
}

