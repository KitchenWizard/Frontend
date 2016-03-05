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


public class ExpandedItemWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JButton back;
	protected JButton notificationsButton;
	protected int notificationsNum;
	
	protected static JLabel picture;
	protected static JLabel name;
	protected static JLabel expir;
	protected JLabel additional;
	
	public ExpandedItemWindow(String name)
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
		
		picture.setBounds(150,50,350,350);
		pane.add(picture);
		
		name.setBounds(400,50,100,50);
		pane.add(name);
		
		expir.setBounds(400,100,250,50);
		pane.add(expir);
		
		additional=new JLabel("Additional information");
		additional.setBounds(400,150,250,250);
		pane.add(additional);
		
		back=new JButton("Back");
		back.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		ListWindow.createAndShowGUI();
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
	
	static void createAndShowGUI(JLabel p,JLabel n,JLabel e) 
	{
	        //Create and set up the window.

			picture=p;
			name=n;
			expir=e;
	        ExpandedItemWindow frame = new ExpandedItemWindow("Kitchen Wizard - Item");
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

	private static void createAndShowGUI() {
		
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

