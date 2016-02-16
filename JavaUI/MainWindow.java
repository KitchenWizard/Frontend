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
	public JButton confirm;
	public JLabel wrongPass;
	public JPanel LogIn;
	
	protected JLabel logo;
	public JPanel topBar;
	
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
		pane.setLayout(new BorderLayout());
    	LogIn=new JPanel(new FlowLayout());
    	topBar=new JPanel(new BorderLayout());
    	
    	logo=new JLabel("Kitchen Wizard");
    	topBar.add(logo,BorderLayout.LINE_START);
    	topBar.add(new JSeparator(JSeparator.HORIZONTAL),BorderLayout.PAGE_END);
		
    	usernameLabel=new JLabel("Username");
    	LogIn.add(usernameLabel);
    	
    	usernameField=new JTextField(25);
        usernameField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		username=usernameField.getText();
        	}
        });
        LogIn.add(usernameField);
        
        passwordLabel=new JLabel("Password");
        LogIn.add(passwordLabel);
        
        passwordField=new JPasswordField(25);
        passwordField.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		password=passwordField.getPassword();
        	}
        });
        LogIn.add(passwordField);
        
    	confirm=new JButton("Go");
    	confirm.addActionListener(this);
    	LogIn.add(confirm);
    	
    	wrongPass=new JLabel("Incorrect username or password");
    	wrongPass.setVisible(false);
    	LogIn.add(wrongPass);
    	
    	pane.add(topBar, BorderLayout.PAGE_START);
    	pane.add(LogIn,BorderLayout.CENTER);
    	
    	
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
