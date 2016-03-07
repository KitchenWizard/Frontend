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
import javax.swing.JTextField;


public class RemoveWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JLabel removeLabel;
	protected JButton home;
	protected JButton notificationsButton;
	protected int notificationsNum;
	
	protected static String session;
	protected String id;
	protected JTextField removeField;
	protected JButton removeButton;
	
	public RemoveWindow(String name)
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		pane.add(notificationsButton);
		
		removeLabel=new JLabel("Scan Item Now");
		pane.add(removeLabel);
		removeLabel.setBounds(350, 100, 175, 38);
		pane.add(removeLabel);
		
		removeField=new JTextField();
		removeField.setBounds(350,150,100,50);
		pane.add(removeField);
		
		removeButton=new JButton("Remove");
		removeButton.setBounds(350,210,100,50);
		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				id=removeField.getText();
				try {
					sendRemove();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		pane.add(removeButton);
		
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
		
	}
	
	static void createAndShowGUI(String s) 
	{
	        //Create and set up the window.
	        RemoveWindow frame = new RemoveWindow("Kitchen Wizard - Remove Items Now");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //Set up the content pane.
	        frame.addComponentsToPane(frame.getContentPane());
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        session=s;
	}
	
	static void createAndShowGUI()
	{
	}

	public static void main(String args[])
	{
		createAndShowGUI();
	}
	

	public void sendRemove() throws IOException
	{
		
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="removeitem";
		String id1=id;
		String sessionkey=session;
		
		String query=String.format("command=%s&id=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(id1,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
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

