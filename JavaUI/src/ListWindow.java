import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;


public class ListWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected int notificationsNum;
	protected JButton notificationsButton;
	protected JButton home;
	protected JButton alphaSort;
	protected JButton expirSort;
	protected static JButton delete;
	
	protected JPanel listPane;
	protected ArrayList itemNames=new ArrayList();
	static String session;
	
	public ListWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,450));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(null);
		listPane=new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		
		JScrollPane scroller=new JScrollPane(listPane);
		scroller.setBounds(0, 26, 800, 395);
		pane.add(scroller);
		
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
		
		itemNames.add("b");
		itemNames.add("e");
		itemNames.add("f");
		itemNames.add("a");
		itemNames.add("x");
		itemNames.add("z");
		itemNames.add("t");
		itemNames.add("p");
		itemNames.add("q");
		itemNames.add("m");
		itemNames.add("l");
		itemNames.add("k");
		itemNames.add("g");
		itemNames.add("w");
		itemNames.add("v");
		itemNames.add("o");
		
		for(int i=0;i<itemNames.size();i++)
		{
			final int index=i;
			final JPanel item=new JPanel();
			item.setLayout(new BorderLayout());
			final JLabel picture=new JLabel("Picture of item "+i);
			final JLabel name=new JLabel("Item "+itemNames.get(i));
			final JLabel expir=new JLabel("Expiration Date: "+i+"/"+i+"/"+i);
			JPanel itemDetails=new JPanel();
			itemDetails.add(name);
			itemDetails.add(expir);
			JButton expand=new JButton("	More");
			expand.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					ExpandedItemWindow.createAndShowGUI(picture,name,expir);
					dispose();
				}
			});
			itemDetails.add(expand);
			delete=new JButton("X");
			delete.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					itemNames.remove(index);
					listPane.remove(item);
					listPane.revalidate();
					listPane.repaint();
				}
			});
			
			item.add(picture,BorderLayout.LINE_START);
			item.add(itemDetails,BorderLayout.CENTER);
			item.add(delete,BorderLayout.LINE_END);
			item.setBorder(BorderFactory.createLineBorder(Color.black));
			listPane.add(item);
		}
		
		home=new JButton("Home");
		home.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		HomeWindow.createAndShowGUI();
        		dispose();
        	}
        });
		
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
		
		alphaSort=new JButton("ABC");
		alphaSort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Collections.sort(itemNames);
				listPane.removeAll();
				for(int i=0;i<itemNames.size();i++)
				{
					final int index=i;
					final JPanel item=new JPanel();
					item.setLayout(new BorderLayout());
					
					final JLabel picture=new JLabel("Picture of item");
					final JLabel name=new JLabel("Item "+itemNames.get(i));
					final JLabel expir=new JLabel("Expiration Date: "+i+"/"+i+"/"+i);
					JPanel itemDetails=new JPanel();
					itemDetails.add(name);
					itemDetails.add(expir);
					JButton expand=new JButton("More");
					expand.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							ExpandedItemWindow.createAndShowGUI(picture,name,expir);
							dispose();
						}
					});
					itemDetails.add(expand);
					delete=new JButton("X");
					delete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							itemNames.remove(index);
							listPane.remove(item);
							listPane.revalidate();
							listPane.repaint();
						}
					});
					
					item.add(picture,BorderLayout.LINE_START);
					item.add(itemDetails,BorderLayout.CENTER);
					item.add(delete,BorderLayout.LINE_END);
					item.setBorder(BorderFactory.createLineBorder(Color.black));
					listPane.add(item);
				}
				listPane.revalidate();
				listPane.repaint();
				
			}
		});
		alphaSort.setBounds(600, 423, 75, 25);
		pane.add(alphaSort);
		
		expirSort=new JButton("Expir");
		expirSort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		expirSort.setBounds(695, 423, 75, 25);
		pane.add(expirSort);
		
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
	        ListWindow frame = new ListWindow("Kitchen Wizard - Your List");
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

