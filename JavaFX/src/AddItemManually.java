import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class AddItemManually extends Application{
	
	protected static Label logo;
	protected static int notificationsNum;
	protected static Button notificationsButton;
	
	protected static Button home;
	protected static Button confirm;
	
	protected static Label name;
	protected static TextField nameField;
	protected static Label description;
	protected static TextField descriptionField;
	protected static Label manufacturer;
	protected static TextField manufacturerField;
	protected static Label expir;
	protected static TextField expirField;
	protected static Label quantity;
	protected static TextField quantityField;
	protected static Label group;
	protected static ComboBox groupBox;
	protected static String groups;
	
	protected static String session;
	protected static String dbResponse;
	
	protected static String barcode;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	public static void setStage(Stage stage, String s,String b) throws Exception 
	{
		barcode=b;
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
		
		name=new Label("Product Name:");
		grid.add(name, 100, 120,150,30);
		nameField=new TextField();
		grid.add(nameField, 250, 120,150,30);
		
		description=new Label("Description:");
		grid.add(description, 100, 150,150,30);
		descriptionField=new TextField();
		grid.add(descriptionField, 250, 150,150,30);
		
		manufacturer=new Label("Manufacturer");
		grid.add(manufacturer, 100, 180,150,30);
		manufacturerField=new TextField();
		grid.add(manufacturerField, 250, 180,150,30);

		quantity=new Label("Quantity used:");
		grid.add(quantity, 100, 210,150,30);
		quantityField=new TextField();
		grid.add(quantityField, 250, 210,100,30);
		
		expir=new Label("Expiration Date:");
		grid.add(expir, 100, 240,150,30);
		expirField=new TextField();
		grid.add(expirField, 250, 240,100,30);

		getGroups();
		ObservableList<String> groupList=FXCollections.observableArrayList(groups.split(";"));
		group=new Label("Group ID");
		grid.add(group, 100, 270,500,30);
		groupBox=new ComboBox(groupList);
		
		grid.add(groupBox, 250, 270,100,30);
		
		confirm=new Button("Send");
		confirm.getStyleClass().add("menubutton");
		confirm.setOnAction(new EventHandler<ActionEvent>()
    	{
			public void handle(ActionEvent event) 
			{
				try {
					sendItem();
					AddWindow.setStage(stage, session);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	});
		confirm.setMinSize(225, 50);
		grid.add(confirm, 550,200,150,50 );
		
		//Create the bottom bar of the program
		home=new Button("Home");
		home.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					HomeWindow.setStage(stage,session);
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

		stage.setTitle("Kitchen Wizard -Manually Add Item");
        stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public static void sendItem() throws IOException
	{
		
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="manualadd";
		String sessionkey=session;
		String barcode1=barcode;
		String name=nameField.getText();
		String description=descriptionField.getText();
		String manufacturer=manufacturerField.getText();
		String quantity=quantityField.getText();
		String expir=expirField.getText();
		if(expir.equals(null))
		{
			expir="na";
		}
		String group=groupBox.getValue().toString();
		if(group.equals(null))
		{
			group="na";
		}
		System.out.println(sessionkey);
		System.out.println(description);
		String query=String.format("command=%s&barcode=%s&name=%s&description=%s&manufacturer=%s&amount=%s&expiration=%s&group=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(barcode1,charset),
				URLEncoder.encode(name,charset),
				URLEncoder.encode(description,charset),
				URLEncoder.encode(manufacturer,charset),
				URLEncoder.encode(quantity,charset),
				URLEncoder.encode(expir,charset),
				URLEncoder.encode(group,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			dbResponse=inputLine;
		System.out.println(dbResponse);
		in.close();
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
		in.close();
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
			dbResponse=inputLine;
		System.out.println(dbResponse);
		in.close();
	}
}