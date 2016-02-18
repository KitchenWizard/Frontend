import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class HomeWindow extends JFrame implements ActionListener
{
	protected JButton addButton;
	protected JButton removeButton;
	protected JButton listButton;
	protected int notificationsNum;
	
	protected JLabel logo;
	protected JLabel notifications;
	
	
	public HomeWindow(String name)
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
		
		notifications=new JLabel(notificationsNum+" Notifications");
		notifications.setBounds(700, 3, 90, 15);
		pane.add(notifications);
		
    	addButton=new JButton("Add Items");
    	addButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			AddWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	addButton.setBounds(275, 100, 250, 50);
    	pane.add(addButton);
		
    	removeButton=new JButton("Remove Items");
    	removeButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			RemoveWindow.createAndShowGUI();
    			dispose();
    		}
    		
    	});
    	removeButton.setBounds(275, 180, 250, 50);
    	pane.add(removeButton);
		
    	listButton=new JButton("View List of Items");
    	listButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			ListWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	listButton.setBounds(275, 260, 250, 50);
    	pane.add(listButton);
    	
    	JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 450, 800, 18);
    	pane.add(line2);
    	
    	//JSeparator line3=new JSeparator(JSeparator.VERTICAL);
    	//line3.setBounds(399, 0, 3, 480);
    	//pane.add(line3);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
	static void createAndShowGUI() 
	{
	        //Create and set up the window.
	        HomeWindow frame = new HomeWindow("Kitchen Wizard - Home Screen");
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
