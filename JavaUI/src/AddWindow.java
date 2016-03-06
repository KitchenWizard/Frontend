import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JTextField;

//AddWindow which will prompt the user to scan items and send the barcode received to the database
public class AddWindow extends JFrame implements ActionListener
{
	//Create the components that will be used later
	protected JLabel logo;
	protected JLabel addLabel;
	protected JButton home;
	protected JButton notificationsButton;
	protected int notificationsNum;
	
	protected JLabel picture;
	protected JLabel name;
	protected JLabel expir;
	protected JTextField expirField;
	
	protected Container pane;
	protected static String session;
	protected String barcode;
	
	protected static JTextField barcodeField;
	protected JButton addButton;
	
	//Constructor
	//KeyListener added to listen for the barcode scanner since it functions as a keyboard
	public AddWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,450));
        setFocusable(true);
		
	}
	
	//Add the components to the pane
	public void addComponentsToPane(Container pane)
	{
		//Set the layout to null so we can place the components ourselves
		pane.setLayout(null);
		
		//Create the top bar of the program
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
		
		//Create and add the add label which will prompt the user to scan an item
		addLabel=new JLabel("Scan Item Now");
		addLabel.setBounds(350, 100, 175, 38);
		pane.add(addLabel);
		
		barcodeField=new JTextField();
		barcodeField.setBounds(350,150,300,50);
		barcodeField.setVisible(false);
		barcodeField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				barcode=barcodeField.getText();
			}
		});
		pane.add(barcodeField);
		
		//Create the bottom bar of the program
		home=new JButton("Home");
		home.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		HomeWindow.createAndShowGUI(session);
        		dispose();
        	}
        });
		home.setBounds(5, 423, 75, 25);
		pane.add(home);
		
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
    	pane.add(line2);
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		addLabel.setVisible(false);
		
		picture.setBounds(150,50,350,350);
		pane.add(picture);
		
		name.setBounds(400,50,100,50);
		pane.add(name);
		
		expir.setBounds(400,100,250,50);
		pane.add(expir);
		
		expirField.setBounds(650,100,145,50);
		pane.add(expirField);
		
	}
	
	//createAndShowGUI which will create the frame and add the pane
	//Passed the session key
	static void createAndShowGUI(String s) 
	{
	        //Create and set up the window.
	        AddWindow frame = new AddWindow("Kitchen Wizard - Add Items Now");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //Set up the content pane.
	        frame.addComponentsToPane(frame.getContentPane());
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        session=s;
	        barcodeField.grabFocus();
	}
	
	//Default and required createAndShowGUI
	static void createAndShowGUI()
	{
	}

	//main
	public static void main(String args[])
	{
		createAndShowGUI();
	}
	
	//sendItem which will send the barcode to the database
	public void sendItem() throws IOException
	{
		
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="additem";
		String barcode1=barcode;
		String sessionkey=session;
		
		String query=String.format("command=%s&barcode=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(barcode1,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
	
	//sendNotifications which will get the current list of notifications from the database
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

