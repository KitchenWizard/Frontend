import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;


public class HomeWindow extends JFrame implements ActionListener
{
	protected JButton addButton;
	protected JButton removeButton;
	protected JButton listButton;
	protected JButton recipesButton;
	protected int notificationsNum;
	protected JButton notificationsButton;
	
	protected JLabel logo;
	
	
	public HomeWindow(String name)
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
				NotificationsWindow.createAndShowGUI();
				dispose();
			}
		});
		pane.add(notificationsButton);
		
    	addButton=new JButton("Add Items");
    	addButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			AddWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	addButton.setBounds(275, 75, 250, 50);
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
    	removeButton.setBounds(275, 155, 250, 50);
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
    	listButton.setBounds(275, 235, 250, 50);
    	pane.add(listButton);
    	
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
    	
    	JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 420, 800, 18);
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
