import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class ListWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JLabel notifications;
	protected JButton home;
	protected JButton alphaSort;
	protected JButton expirSort;
	protected JButton delete;
	
	protected JPanel topBar;
	protected JPanel bottomBar;
	protected JPanel sortButtons;
	protected JPanel list;
	protected JPanel item;
	protected JPanel itemDetails;
	
	public ListWindow(String name)
	{
		super(name);
		this.getContentPane().setPreferredSize(new Dimension(800,480));
		
	}
	
	public void addComponentsToPane(Container pane)
	{
		pane.setLayout(new BorderLayout());
		topBar=new JPanel(new BorderLayout());
		bottomBar=new JPanel(new BorderLayout());
		sortButtons=new JPanel(new FlowLayout());
		list=new JPanel();
		list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));
		list.setBorder(BorderFactory.createLineBorder(Color.black));
		item=new JPanel(new BorderLayout());
		itemDetails=new JPanel(new FlowLayout());
		
		logo=new JLabel("Kitchen Wizard");
		topBar.add(logo,BorderLayout.LINE_START);
		
		notifications=new JLabel("0 Notifications");
		topBar.add(notifications,BorderLayout.LINE_END);
		
		topBar.add(new JSeparator(JSeparator.HORIZONTAL),BorderLayout.PAGE_END);
		
		pane.add(topBar, BorderLayout.PAGE_START);
		
		JLabel picture=new JLabel("Picture of item");
		JLabel name=new JLabel("Item "+"Example");
		JLabel expir=new JLabel("Expiration Date: "+1+"/"+1+"/"+1);
		
		delete=new JButton("X");
		itemDetails.add(name);
		itemDetails.add(expir);
		item.add(picture,BorderLayout.LINE_START);
		item.add(itemDetails,BorderLayout.CENTER);
		item.add(delete,BorderLayout.LINE_END);
		list.add(item);
		
		picture=new JLabel("Picture of item");
		name=new JLabel("Item "+"Example");
		expir=new JLabel("Expiration Date: "+1+"/"+1+"/"+1);
		
		delete=new JButton("X");
		itemDetails.add(name);
		itemDetails.add(expir);
		item.add(picture,BorderLayout.LINE_START);
		item.add(itemDetails,BorderLayout.CENTER);
		item.add(delete,BorderLayout.LINE_END);
		list.add(item);
		/*for(int i=0;i<5;i++)
		{
			JLabel picture=new JLabel("Picture of item");
			JLabel name=new JLabel("Item "+i);
			JLabel expir=new JLabel("Expiration Date: "+i+"/"+i+"/"+i);
			delete=new JButton("X");
			itemDetails.add(name);
			itemDetails.add(expir);
			item.add(picture,BorderLayout.LINE_START);
			item.add(itemDetails,BorderLayout.CENTER);
			item.add(delete,BorderLayout.LINE_END);
			list.add(item);
		}*/
		
		pane.add(list, BorderLayout.CENTER);
		
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
		
		alphaSort=new JButton("ABC");
		sortButtons.add(alphaSort);
		expirSort=new JButton("Expiration");
		sortButtons.add(expirSort);
		bottomBar.add(sortButtons,BorderLayout.LINE_END);
		
		pane.add(bottomBar, BorderLayout.PAGE_END);
		
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

