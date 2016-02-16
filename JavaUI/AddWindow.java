import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class AddWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JLabel notifications;
	protected JLabel addLabel;
	protected JButton home;
	
	protected JPanel topBar;
	protected JPanel bottomBar;
	
	public AddWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,480));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(new BorderLayout());
		topBar=new JPanel(new BorderLayout());
		bottomBar=new JPanel(new BorderLayout());
		
		logo=new JLabel("Kitchen Wizard");
		topBar.add(logo,BorderLayout.LINE_START);
		
		notifications=new JLabel("0 Notifications");
		topBar.add(notifications,BorderLayout.LINE_END);
		
		topBar.add(new JSeparator(JSeparator.HORIZONTAL),BorderLayout.PAGE_END);
		
		pane.add(topBar, BorderLayout.PAGE_START);
		
		addLabel=new JLabel("Scan Item Now");
		pane.add(addLabel,BorderLayout.CENTER);
		
		home=new JButton("Home");
		home.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		HomeWindow.createAndShowGUI();
        		dispose();
        	}
        });
		bottomBar.add(home,BorderLayout.LINE_START);
		pane.add(bottomBar, BorderLayout.PAGE_END);
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	static void createAndShowGUI() 
	{
	        //Create and set up the window.
	        AddWindow frame = new AddWindow("Kitchen Wizard - Add Items Now");
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

