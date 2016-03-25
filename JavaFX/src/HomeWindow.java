import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomeWindow extends Application{
	
	protected Label logo;
	protected Button addButton;
	protected Button listButton;
	protected Button recipesButton;
	protected int notificationsNum;
	protected Button notificationsButton;
	protected Button shoppingButton;
	protected Button updateButton;
	
	protected String items;
	protected String list;
	protected String info;
	protected String session;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	public void setStage(Stage stage,String s) throws Exception
	{
		System.gc();
		session=s;
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(5);
		grid.setHgap(5);
		Scene scene = new Scene(grid, 800, 480);

		File f=new File("Style.css");
		scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
		
		int numCol=800;
		int numRows=480;
		
		for(int i=0;i<numCol;i++)
		{
			ColumnConstraints colConst=new ColumnConstraints();
			colConst.setPercentWidth(100.0/numCol);
			grid.getColumnConstraints().add(colConst);
		}
		
		for(int i=0;i<numRows;i++)
		{
			RowConstraints rowConst=new RowConstraints();
			rowConst.setPercentHeight(100.0/numRows);
			grid.getRowConstraints().add(rowConst);
		}
		
		logo=new Label("Kitchen Wizard");
		logo.getStyleClass().add("kw");
		grid.add(logo, 0, 0,150,20);
		
		Separator line=new Separator();
		line.setValignment(VPos.CENTER);
		grid.add(line, 0, 25,800,2);
		
		notificationsButton=new Button(notificationsNum+" Notifications");
		notificationsButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					//NotificationsWindow.setStage(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		notificationsButton.setMinSize(50,23);
		notificationsButton.getStyleClass().add("notificationsbutton");
		grid.add(notificationsButton,675, 1, 125, 20);
		
		//Create and add the add button which will take the user to the screen prompting them to scan items
    	addButton=new Button("Add Items");
    	addButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			AddWindow add=new AddWindow();
					add.setStage(stage,session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	addButton.setMinSize(250, 75);
		addButton.getStyleClass().add("menubutton");
    	grid.add(addButton,275, 85, 250, 50);
		
    	//Create and add the list button which will take the user to the screen displaying their current list of items
    	listButton=new Button("View List of Items");
    	listButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
				try {
					sendList();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		try {
        			ListWindow list=new ListWindow();
					list.setStage(stage,session,items);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	listButton.setMinSize(250, 75);		
    	listButton.getStyleClass().add("menubutton");
    	grid.add(listButton,275, 195, 250, 50);
    	
    	//Create and add the recipes button which will take the user to the screen displaying their available recipes
    	recipesButton=new Button("Recipes");
    	recipesButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			RecipesWindow recipes=new RecipesWindow();
					recipes.setStage(stage,session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	recipesButton.setMinSize(250, 75);		
    	recipesButton.getStyleClass().add("menubutton");
    	grid.add(recipesButton,275, 295, 250, 50);
    	
    	listButton=new Button("Shopping List");
    	listButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			sendShoppingList();
        			ShoppingListWindow shop=new ShoppingListWindow();
					shop.setStage(stage,session,list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	listButton.setMinSize(250,75);
    	listButton.getStyleClass().add("menubutton");
    	grid.add(listButton, 275, 370,250,50);
    	
    	Button close=new Button("X");
    	close.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			stage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	grid.add(close, 750, 460);
    	
    	updateButton=new Button("Update User Information");
    	updateButton.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			sendUpdate();
        			UpdateUserWindow update=new UpdateUserWindow();
					update.setStage(stage,session,info);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	updateButton.setMinSize(275,50);
    	updateButton.getStyleClass().add("menubutton");
    	grid.add(updateButton, 0, 100,250,50);
    	
    	//Add in the bottom horizontal line
		Separator line2=new Separator();
		line2.setValignment(VPos.CENTER);
    	grid.add(line2, 0, 450, 800, 2);

		stage.setTitle("Kitchen Wizard -Home");
        stage.setScene(scene);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	//sendList which will send a request to the database to get the current list of items for the user
		//returns all of the items and all of the information about them
	public void sendList() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getitems";
		String sessionkey=session;
		
		String query=String.format("command=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			items=inputLine;
		System.out.println(items);
		in.close();
	}
	public void sendShoppingList() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getitems";
		String sessionkey=session;
		
		String query=String.format("command=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			list=inputLine;
		System.out.println(list);
		in.close();
	}
	public void sendUpdate() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getinfo";
		String sessionkey=session;
		
		String query=String.format("command=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			info=inputLine;
		System.out.println(info);
		in.close();
	}
}