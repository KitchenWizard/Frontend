import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;


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
	protected char[] password;
	protected char[] correctPass={'f','f','f'};
	
	public MainWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,480));
		
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
        		password=passwordField.getPassword();
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
    	wrongPass.setBounds(250,150,200,50);
    	pane.add(wrongPass);
    	
    	register=new JButton("Register");
    	register.setBounds(325, 240, 150, 40);
    	pane.add(register);
    	
    	
    	
	}
	public void actionPerformed(ActionEvent e) 
	{
		username=usernameField.getText();
		password=passwordField.getPassword();
		if(username.equals("asdf")&&Arrays.equals(password, correctPass))
		{
			System.out.println("Good");
			HomeWindow.createAndShowGUI();
			dispose();
		}
		else
		{
			wrongPass.setVisible(true);
			System.out.println("No");
		}
		
	}
	
	private static void createAndShowGUI() 
	{
	        //Create and set up the window.
	        MainWindow frame = new MainWindow("Kitchen Wizard - Please Log In");
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
}
