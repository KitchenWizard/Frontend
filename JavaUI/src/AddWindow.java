import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;


public class AddWindow extends JFrame implements ActionListener
{
	protected JLabel logo;
	protected JLabel addLabel;
	protected JButton home;
	protected JButton notificationsButton;
	protected int notificationsNum;
	
	protected JLabel picture;
	protected JLabel name;
	protected JLabel expir;
	protected JTextField expirField;
	
	protected Container pane;
	
	public AddWindow(String name)
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
		
		addLabel=new JLabel("Scan Item Now");
		addLabel.setBounds(350, 100, 175, 38);
		pane.add(addLabel);
		
		home=new JButton("Home");
		home.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		HomeWindow.createAndShowGUI();
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
		addLabel.setVisible(false);
		
		picture.setBounds(150,50,350,350);
		pane.add(picture);
		
		name.setBounds(400,50,100,50);
		pane.add(name);
		
		expir.setBounds(400,100,250,50);
		pane.add(expir);
		
		expirField.setBounds(650,100,145,50);
		pane.add(expirField);
		
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

