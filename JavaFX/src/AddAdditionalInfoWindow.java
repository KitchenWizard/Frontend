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
import javafx.scene.layout.*;

public class AddAdditionalInfoWindow extends Application{
	
	protected  Label logo;
	protected  int notificationsNum;
	protected  Button notificationsButton;
	
	protected  Button home;
	protected  Button confirm;
	
	protected  Label picture;
	protected  Label name;
	protected  Label expir;
	protected  TextField expirField;
	protected  Label quantity;
	protected  TextField quantityField;
	protected  Label group;
	protected  ComboBox groupBox;
	protected  Button sendAdditionalInfo;
	
	protected  String session;
	protected  String dbResponse;
	protected  String groups;
	protected  String id;
	protected  String barcode;
	
	public  void main(String[] args) 
	{
        launch(args);
    }
	
	public  void setStage(Stage stage, String s,String idd,String b) throws Exception 
	{
		barcode=b;
		id=idd;
		id=id.replace("[[", "");
		session=s;
		System.out.println(session);
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
		
		expir=new Label("Expiration Date:");
		grid.add(expir, 100, 120,150,30);
		expirField=new TextField();
		grid.add(expirField, 250, 120,150,30);
		
		quantity=new Label("Quantity used:");
		grid.add(quantity, 100, 150,150,30);
		quantityField=new TextField();
		grid.add(quantityField, 250, 150,150,30);
		
		getGroups();
		ObservableList<String> groupList=FXCollections.observableArrayList(groups.split(";"));
		group=new Label("Group ID");
		grid.add(group, 100, 180,150,30);
		groupBox=new ComboBox(groupList);
		grid.add(groupBox, 250, 180,300,30);
		
		confirm=new Button("Send");
		confirm.getStyleClass().add("menubutton");
		confirm.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
				try {
					sendExtra();
					sendGroup();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		try {
        			AddWindow add=new AddWindow();
					add.setStage(stage,session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		grid.add(confirm, 100,250,150,50 );
		
		//Create the bottom bar of the program
		home=new Button("Home");
		home.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			HomeWindow home=new HomeWindow();
					home.setStage(stage,session);
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

		stage.setTitle("Kitchen Wizard -Add Additional Item Info");
        stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public  void sendItem() throws IOException
	{
		
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="additem";
		String sessionkey=session;
		
		String query=String.format("command=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
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
	
	public  void sendExtra() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String sessionkey=session;
		String command="updateitem";
		String expirdate=expirField.getText();
		String quantityused=quantityField.getText()+"%";
		String pid=id;
		String query=String.format("command=%s&sessionkey=%s&percentused=%s&expiration=%s&id=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset),
				URLEncoder.encode(quantityused,charset),
				URLEncoder.encode(expirdate,charset),
				URLEncoder.encode(pid,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			dbResponse=inputLine;
		System.out.println(dbResponse);
		in.close();
	}
	
	public  void sendGroup() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String sessionkey=session;
		String command="updategroup";
		String groupID=groupBox.getValue().toString();
		groupID=groupID.replace("(", "");
		String[] groupIDArray=groupID.split(",");
		System.out.println(groupIDArray[0]);
		String grouptosend=groupIDArray[0].trim();
		String barcodesend=barcode;
		
		String query=String.format("command=%s&sessionkey=%s&groupid=%s&barcode=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset),
				URLEncoder.encode(grouptosend,charset),
				URLEncoder.encode(barcodesend,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			dbResponse=inputLine;
		System.out.println(dbResponse);
		in.close();
	}
	public  void getGroups() throws IOException
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