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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ExpandedItemWindow extends Application{
	
	protected static Label logo;
	protected static int notificationsNum;
	protected static Button notificationsButton;
	protected static Button back;
	
	protected static ImageView picture;
	protected static String name;
	protected static String expir;
	protected static Label qunatity;
	protected static Label group;
	protected static ComboBox groupBox;
	protected static Label quantity;
	
	protected static TextField expirField;
	protected static TextField quantityField;
	protected static String groups;
	
	protected static String session;
	protected static String newitems;
	protected static String items;
	protected static String dbResponse;
	protected static String id;
	protected static String barcode;
	protected static String URL;
	protected static String quantityString;
	
	protected static Button confirm;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	public static void setStage(Stage stage, String s,String it,String u,String n,String e,String b,String idd,String q) throws Exception 
	{
		id=idd;
		items=it;
		session=s;
		URL=u;
		System.out.println(URL);
		barcode=b;
		quantityString=q;
		System.out.println(id);
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
		
		final ImageView selectedImage = new ImageView();
		URL=u;
		URL=URL.replace("'", "");
		URL=URL.trim();
		System.out.println(URL);
        Image image1=new Image(URL);
        selectedImage.setImage(image1);
        selectedImage.setFitWidth(200);;
        selectedImage.setFitHeight(200);
        selectedImage.setPreserveRatio(true);
        selectedImage.setSmooth(true);
        grid.add(selectedImage, 100, 200);
		
		name=n;
		Label nameLabel=new Label(name);
		grid.add(nameLabel, 350, 100,350,20);
		
		Label expirLabel=new Label("Expiration Date:");
		grid.add(expirLabel, 350, 150,200,20);
		expirField=new TextField();
		expirField.setText(e.trim());
		grid.add(expirField, 500,150,100,30);
		
		quantity=new Label("Quantity:");
		grid.add(quantity, 350,200,100,20);
		quantityField=new TextField();
		quantityField.setText(quantityString.trim());
		grid.add(quantityField, 500,200,100,30);
		
		getGroups();
		ObservableList<String> groupList=FXCollections.observableArrayList(groups.split(";"));
		group=new Label("Group ID");
		grid.add(group, 350,250,150,30);
		groupBox=new ComboBox(groupList);
		grid.add(groupBox, 500,250,200,30);
		
		confirm=new Button("Confirm");
		confirm.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			sendExtra();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		try{
        			sendGroup();
        		}
        		catch (Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        });
		confirm.getStyleClass().add("menubutton");
		grid.add(confirm, 350,300,200,100);
		
		//Create the bottom bar of the program
		back=new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			//sendList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					HomeWindow.setStage(stage, session);
					//ListWindow.setStage(stage,session,newitems);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		back.getStyleClass().add("notificationsbutton");
		grid.add(back,5, 455, 75, 20);
    	
    	//Add in the bottom horizontal line
		Separator line2=new Separator();
		line2.setValignment(VPos.CENTER);
    	grid.add(line2, 0, 450, 800, 2);

		stage.setTitle("Kitchen Wizard -Item");
        stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	public static void sendList() throws IOException
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
			newitems=inputLine;
		System.out.println(newitems);
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
	
	public static void sendExtra() throws IOException
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
	
	public static void sendGroup() throws IOException
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
			
		in.close();
	}
}