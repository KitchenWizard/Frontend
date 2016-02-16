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
	public JPanel buttons;
	
	protected JLabel logo;
	protected JLabel notifications;
	public JPanel topBar;
	
	
	public HomeWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,480));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(new BorderLayout());
		topBar=new JPanel(new BorderLayout());
		buttons=new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.PAGE_AXIS));
	
		logo=new JLabel("Kitchen Wizard");
		topBar.add(logo,BorderLayout.LINE_START);
		
		notifications=new JLabel("0 Notifications");
		topBar.add(notifications,BorderLayout.LINE_END);
		
		topBar.add(new JSeparator(JSeparator.HORIZONTAL),BorderLayout.PAGE_END);
		
    	addButton=new JButton("Add Items");
    	addButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			AddWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	buttons.add(addButton);
    	
		buttons.add(new JLabel(" "));
		
    	removeButton=new JButton("Remove Items");
    	removeButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			RemoveWindow.createAndShowGUI();
    			dispose();
    		}
    		
    	});
    	buttons.add(removeButton);
    	
		buttons.add(new JLabel(" "));
		
    	listButton=new JButton("View List of Items");
    	listButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e)
    		{
    			ListWindow.createAndShowGUI();
    			dispose();
    		}
    	});
    	buttons.add(listButton);
    	
    	pane.add(topBar,BorderLayout.PAGE_START);
    	pane.add(buttons, BorderLayout.LINE_START);
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
