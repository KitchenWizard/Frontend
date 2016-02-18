import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	protected JLabel notifications;
	protected int notificationsNum;
	protected JButton home;
	protected JButton alphaSort;
	protected JButton expirSort;
	protected JButton delete;
	
	protected JPanel listPane;
	
	public ListWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,480));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(null);
		listPane=new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		
		JScrollPane scroller=new JScrollPane(listPane);
		scroller.setBounds(0, 26, 800, 420);
		pane.add(scroller);
		
		logo=new JLabel("Kitchen Wizard");
    	logo.setBounds(0, 0, 118, 22);
    	pane.add(logo);
		
    	JSeparator line=new JSeparator(JSeparator.HORIZONTAL);
    	line.setBounds(0, 25, 800, 18);
    	pane.add(line);
		
		notifications=new JLabel(notificationsNum+" Notifications");
		notifications.setBounds(700, 3, 90, 15);
		pane.add(notifications);
		
		for(int i=0;i<20;i++)
		{
			JPanel item=new JPanel();
			item.setLayout(new BorderLayout());
			
			JLabel picture=new JLabel("Picture of item");
			JLabel name=new JLabel("Item "+i);
			JLabel expir=new JLabel("Expiration Date: "+i+"/"+i+"/"+i);
			JPanel itemDetails=new JPanel();
			itemDetails.add(name);
			itemDetails.add(expir);
			delete=new JButton("X");
			
			item.add(picture,BorderLayout.LINE_START);
			item.add(itemDetails,BorderLayout.CENTER);
			item.add(delete,BorderLayout.LINE_END);
			item.setBorder(BorderFactory.createLineBorder(Color.black));
			JButton itemButton=new JButton();
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
        		HomeWindow.createAndShowGUI();
        		dispose();
        	}
        });
		home.setBounds(5, 453, 75, 25);
		pane.add(home);
		
		alphaSort=new JButton("ABC");
		alphaSort.setBounds(600, 453, 75, 25);
		pane.add(alphaSort);
		
		expirSort=new JButton("Expir");
		expirSort.setBounds(695, 453, 75, 25);
		pane.add(expirSort);
		
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
	        ListWindow frame = new ListWindow("Kitchen Wizard - Your List");
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

