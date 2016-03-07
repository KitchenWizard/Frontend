import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;


//HomeWindow
//The window that shows up after the user has logged in and present them with their options for using KitchenWizard
public class HomeWindow extends JFrame implements ActionListener
{
	//Create the components that will be added to the pane
	protected JButton addButton;
	protected JButton removeButton;
	protected JButton listButton;
	protected JButton recipesButton;
	protected int notificationsNum;
	protected JButton notificationsButton;
	
	protected JLabel logo;
	
	protected static String session;
	protected static String items;
	
	//Constructor
	public HomeWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,450));
	}
	
	//Create and add the components to the pane
	public void addComponentsToPane(Container pane)
	{
		//Set the layout to null so we can place the components ourselves
		pane.setLayout(null);
	
		//Create the top bar of the program with the logo, notifications button, and horizontal bar
		logo=new JLabel("Kitchen Wizard");
    	logo.setBounds(0, 0, 118, 22);
    	pane.add(logo);
		
    	JSeparator line=new JSeparator(JSeparator.HORIZONTAL);
    	line.setBounds(0, 25, 800, 18);
    	pane.add(line);
		
		notificationsButton=new JButton(notificationsNum+" Notifications");
		notificationsButton.setBounds(645, 3, 150, 20);
		notificationsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					sendNotifications();
					NotificationsWindow.createAndShowGUI();
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		pane.add(notificationsButton);
		
		//Create and add the add button which will take the user to the screen prompting them to scan items
    	addButton=new JButton("Add Items");
    	addButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
	    			AddWindow.createAndShowGUI(session);
	    			dispose();
    		}
    	});
    	addButton.setBounds(275, 75, 250, 50);
    	pane.add(addButton);
		
    	//Create and add the remove button which will take the user to the screen prompting them to scan items
    	removeButton=new JButton("Remove Items");
    	removeButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
	    			RemoveWindow.createAndShowGUI(session);
	    			dispose();
    		}
    		
    	});
    	removeButton.setBounds(275, 155, 250, 50);
    	pane.add(removeButton);
		
    	//Create and add the list button which will take the user to the screen displaying their current list of items
    	listButton=new JButton("View List of Items");
    	listButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			try {
					sendList();
	    			ListWindow.createAndShowGUI(session,items);
	    			dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
    	listButton.setBounds(275, 235, 250, 50);
    	pane.add(listButton);
    	
    	//Create and add the recipes button which will take the user to the screen displaying their available recipes
    	recipesButton=new JButton("Recipes");
    	recipesButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			RecipesWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	recipesButton.setBounds(275, 315, 250, 50);
    	pane.add(recipesButton);
    	
    	//Create and add the bottom horizontal bar
    	JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
	}
	
	//Required function but not used
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
	//createAndShowGUI to create the frame and add the pane.
	//Passed in a string that is the session key
	static void createAndShowGUI(String s) 
	{
	        //Create and set up the window.
	        HomeWindow frame = new HomeWindow("Kitchen Wizard - Home Screen");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //Set up the content pane.
	        frame.addComponentsToPane(frame.getContentPane());
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        session=s;
	        System.out.println(session);
	}
	
	//Default createAndShowGUI required 
	static void createAndShowGUI()
	{
		
	}
	
	//main
	public static void main(String args[])
	{
		createAndShowGUI();
	}
	
	//sendList which will send a request to the database to get the current list of items for the user
	//returns all of the items and all of the information about them
	public void sendList() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getitems";
		String sessionkey=session;
		
		String query=String.format("command=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			items=inputLine;
		in.close();
	}
	
	//sendNotifications which will send a request to the database to get the current list of notifications for the user
	public void sendNotifications() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="notifications";
		
		String query=String.format("command=%s&",
				URLEncoder.encode(command,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
}
