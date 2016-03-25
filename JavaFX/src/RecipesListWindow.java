import javafx.stage.Stage;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RecipesListWindow extends Application{
	
	//((1, 'Food', 'Eat', '', 0, 0, 'nik', datetime.datetime(2016, 3, 20, 22, 17, 36)), (2, 'Food', 'Eat', '', 100, 1, 'nik', datetime.datetime(2016, 3, 20, 22, 19, 6)), (3, 'food', 'eat', '', 1, 2, 'nik', datetime.datetime(2016, 3, 20, 22, 23, 58)), (4, 'Food`', 'Eat', '', 1, 2, 'nik', datetime.datetime(2016, 3, 20, 22, 25, 11)), (5, 'Food`', 'Eat', '', 1, 2, 'nik', datetime.datetime(2016, 3, 20, 22, 25, 29)), (6, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 34, 6)), (7, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 35, 52)), (8, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 37, 14)), (9, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 39, 24)), (10, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 40, 35)), (11, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 43, 19)), (12, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 46, 14)), (13, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 47)), (14, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 49, 20)), (15, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 52, 1)), (16, 'food', 'eat', '', 2, 3, 'nik', datetime.datetime(2016, 3, 20, 22, 53, 24)), (17, 'food', 'eat', '', 1, 2, 'nik', datetime.datetime(2016, 3, 20, 22, 57, 3)))
	//items structured like this
	protected  Label logo;
	protected  int notificationsNum;
	protected  Button notificationsButton;
	
	protected  Button home;
	
	protected  ListView listView;
	protected  Button alphaSort;
	protected  Button expirSort;
	protected  Button delete;
	
	 String session;
	 String items;
	 String id;
	 Stage stage1;
	 String[] parsed;
	 ArrayList addItems;
	 String ingredients;
	
	public  void main(String[] args) 
	{
        launch(args);
    }
	
	public  void setStage(Stage stage,String s, String it) throws Exception 
	{
		stage1=stage;
		session=s;
		items=it;
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
		
		listView=new ListView();
		listView.setMinSize(775, 395);
		listView.setEditable(false);
		
		addItems=new ArrayList();
		String newitems=items.replace("[", "");
		newitems=newitems.replace("]", "");
		parsed=newitems.split(";");
		List parsedList=new LinkedList<String>(Arrays.asList(parsed));
		populate(parsedList);
		
		ObservableList data=FXCollections.observableArrayList(addItems);
		listView.setItems(data);
		grid.add(listView, 0, 26,795,423);
		
		alphaSort=new Button("ABC");
		alphaSort.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					//HomeWindow.setStage(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		alphaSort.setMinWidth(75);
		alphaSort.getStyleClass().add("notificationsbutton");
		grid.add(alphaSort,600, 455, 75, 20);
		
		expirSort=new Button("Expir");
		expirSort.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					//HomeWindow.setStage(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		expirSort.setMinWidth(75);
		expirSort.getStyleClass().add("notificationsbutton");
		grid.add(expirSort,700, 455, 75, 20);
		
		//Create the bottom bar of the program
		home=new Button("Back");
		home.setOnAction(new EventHandler<ActionEvent>()
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
		home.getStyleClass().add("notificationsbutton");
		grid.add(home,5, 455, 75, 20);
    	
    	//Add in the bottom horizontal line
		Separator line2=new Separator();
		line2.setValignment(VPos.CENTER);
    	grid.add(line2, 0, 450, 800, 2);

		stage.setTitle("Kitchen Wizard -Your List of Recipes");
        stage.setScene(scene);
	}

	public  void populate(List item) throws IOException
	{
		for(int i=0;i<item.size()-1;i+=8)
		{
			int index=i;
			HBox hbox=new HBox();
			hbox.setPadding(new Insets(10,10,10,10));
			HBox imageBox=new HBox();
			HBox infoBox=new HBox();
			HBox deleteBox=new HBox();
			
			String idd=(String)item.get(0+index);
			String nameText=(String)item.get(1+index);
			Label name=new Label(nameText);
			name.setPadding(new Insets(1,10,1,10));
			infoBox.getChildren().add(name);
			String cookText=(String)item.get(5+index);
			Label cook=new Label(cookText+" minutes");
			cook.setPadding(new Insets(1,10,1,10));
			infoBox.getChildren().add(cook);
			String description=(String) item.get(2+index);
			String prep=(String) item.get(4+index);
			Button moreInfo=new Button("More");
			moreInfo.setPadding(new Insets(1,10,1,10));
			moreInfo.getStyleClass().add("notificationsbutton");
			moreInfo.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					try {
						id=idd;
						sendIngredients();
						ExpandedRecipeWindow erw=new ExpandedRecipeWindow();
						erw.setStage(stage1, session,items,nameText,description,ingredients,prep,cookText,idd);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			infoBox.getChildren().add(moreInfo);
			
			Button delete=new Button("X");
			delete.getStyleClass().add("delete");
			delete.setMinSize(30, 30);
			delete.setOnAction(new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent event) 
						{
							try {
								id=idd;
								System.out.println("Trying to remove "+id);
								sendRemove();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								sendList();
								addItems=new ArrayList();
								String newitems=items.replace("[", "");
								newitems=newitems.replace("]", "");
								parsed=newitems.split(";");
								List parsedList=new LinkedList<String>(Arrays.asList(parsed));
								populate(parsedList);
								ObservableList data=FXCollections.observableArrayList(addItems);
								listView.setItems(data);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					});
			deleteBox.getChildren().add(delete);
			
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(imageBox, Priority.ALWAYS);
			HBox.setHgrow(infoBox, Priority.ALWAYS);
			HBox.setHgrow(deleteBox, Priority.ALWAYS);
			
			imageBox.setAlignment(Pos.CENTER_LEFT);
			infoBox.setAlignment(Pos.CENTER);
			deleteBox.setAlignment(Pos.CENTER_RIGHT);
			
			hbox.getChildren().addAll(imageBox,infoBox,deleteBox);
			addItems.add(hbox);
		}
	}
	public  void sendList() throws IOException
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
	public  void sendRemove() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="removerecipe";
		String id1=id;
		String sessionkey=session;
		
		String query=String.format("command=%s&id=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(id1,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
		in.close();
	}
	public void sendIngredients() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getingredients";
		String session1=session;
		String id1=id;
		String idtosend=id1.trim();
		System.out.println(idtosend);
		
		String query=String.format("command=%s&sessionkey=%s&recipeid=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(session1,charset),
				URLEncoder.encode(idtosend,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			ingredients=inputLine;
		System.out.println(ingredients);
		in.close();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//sendNotifications which will get the current list of notifications from the database
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