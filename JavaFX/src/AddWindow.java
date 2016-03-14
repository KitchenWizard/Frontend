import javafx.scene.text.*;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class AddWindow extends Application{
	
	protected static Label logo;
	protected static int notificationsNum;
	protected static Button notificationsButton;
	
	protected static Label addLabel;
	protected static Button home;
	
	protected static Label picture;
	protected static Label name;
	protected static Label expir;
	protected static TextField expirField;
	
	protected static ComboBox group;
	protected static Button sendAdditionalInfo;
	
	protected static String session;
	protected static String barcode;
	protected static String dbResponse;
	
	protected static TextField barcodeField;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	public static void setStage(Stage stage) throws Exception 
	{
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

		//BarcodeField which will capture the barcode input and when it is done and the scanner presses enter, the text will be sent to sendItem
		barcodeField=new TextField();
		barcodeField.setOpacity(0);
		barcodeField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
			public void handle(KeyEvent keyEvent)
        	{
				if(keyEvent.getCode()==KeyCode.ENTER)
				{
				//Get the text from the barcodeField and assign it to barcode
					barcode=barcodeField.getText();
					System.out.println(barcode);
				}
        	}
        });
		grid.add(barcodeField,400,200,1,1);
		
		
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
		
		//Create and add the add label which will prompt the user to scan an item
		addLabel=new Label("Scan Item Now");
		addLabel.getStyleClass().add("title");
		grid.add(addLabel,300, 100, 175, 38);
		
		//Create the bottom bar of the program
		home=new Button("Home");
		home.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					HomeWindow.setStage(stage);
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

		stage.setTitle("Kitchen Wizard -Home");
        stage.setScene(scene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void sendItem() throws IOException
	{
		
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="additem";
		String barcode1=barcode;
		String sessionkey=session;
		
		String query=String.format("command=%s&barcode=%s&sessionkey=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(barcode1,charset),
				URLEncoder.encode(sessionkey,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			System.out.println(inputLine);
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
		in.close();
	}

}