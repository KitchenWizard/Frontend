import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddRecipeWindow extends Application{

	//Create components that will be added later
	
	protected static Label logo;
	protected static Button back;
	protected static Label registerLabel;
	
	protected static String session;
	
	protected static Label name;
	protected static Label description;
	protected static Label items;
	protected static Label prep;
	protected static Label cook;
	
	protected static TextField nameField;
	protected static TextField descriptionField;
	protected static ComboBox itemsBox;
	protected static TextField prepField;
	protected static TextField cookField;
	
	protected static Button confirm;
	
	protected static String groups;
	
	
	public static void setStage(Stage stage,String s) throws Exception 
	{
		session=s;
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(5);
		grid.setHgap(5);
		
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
		
		name=new Label("Name:");
		grid.add(name, 100, 50,100,20);
		nameField=new TextField();
		grid.add(nameField, 200, 50,100,30);
		
		description=new Label("Description:");
		grid.add(description, 100, 90,100,20);
		descriptionField=new TextField();
		grid.add(descriptionField, 200, 90,100,30);
		
		items=new Label("Ingredients:");
		grid.add(items, 100, 130,100,30);
		getGroups();
		ObservableList<String> groupList=FXCollections.observableArrayList(groups.split(";"));
		System.out.println(groupList.get(0));
		itemsBox=new ComboBox(groupList);
		grid.add(itemsBox,200, 130, 100,30);
		
		
		prep=new Label("Prep Time:");
		grid.add(prep, 100, 170,100,30);
		prepField=new TextField();
		grid.add(prepField, 200,170,100,30);
		
		cook=new Label("Cook Time:");
		grid.add(cook, 100, 210,100,30);
		cookField=new TextField();
		grid.add(cookField, 200, 210,100,30);
		
		confirm=new Button("Send");
		confirm.getStyleClass().add("menubutton");
		grid.add(confirm, 200, 250,200,50);
		
		back=new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					RecipesWindow.setStage(stage,session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		back.setMinSize(100, 28);
		back.getStyleClass().add("notificationsbutton");
		grid.add(back,5, 454, 75, 25);
		
		//Add in the bottom horizontal line
		Separator line2=new Separator();
		line2.setValignment(VPos.CENTER);
    	grid.add(line2, 0, 450, 800, 2);
    	
		
		Scene scene = new Scene(grid, 800, 480);
        stage.setTitle("Kitchen Wizard -Add Recipe");
		File f=new File("Style.css");
		scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
        stage.setScene(scene);
        stage.show();
	}

	public void start(Stage primaryStage) throws Exception 
	{	
	}
	public static void getGroups() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="getgrouplist";
		
		String query=String.format("command=%s&",
				URLEncoder.encode(command,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			groups=inputLine;
		System.out.println(groups);
		in.close();
	}
}