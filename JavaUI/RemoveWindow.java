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


public class RemoveWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JLabel notifications;
	protected JLabel removeLabel;
	protected JButton home;
	protected int notificationsNum;
	
	public RemoveWindow(String name)
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
		
		removeLabel=new JLabel("Scan Item Now");
		pane.add(removeLabel,BorderLayout.CENTER);
		removeLabel.setBounds(350, 100, 175, 38);
		pane.add(removeLabel);
		
		home=new JButton("Home");
		home.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		HomeWindow.createAndShowGUI();
        		dispose();
        	}
        });
		home.setBounds(5, 453, 75, 25);
		pane.add(home);
		
		JSeparator line2=new JSeparator(JSeparator.HORIZONTAL);
    	line2.setBounds(0, 450, 800, 18);
    	pane.add(line2);
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	static void createAndShowGUI() 
	{
	        //Create and set up the window.
	        RemoveWindow frame = new RemoveWindow("Kitchen Wizard - Remove Items Now");
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

