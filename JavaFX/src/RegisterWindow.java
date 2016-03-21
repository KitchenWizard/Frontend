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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterWindow extends Application{

	//Create components that will be added later
	protected static TextField usernameField;
    protected static PasswordField passwordField;
    protected static Label usernameLabel;
    protected static Label passwordLabel;
	protected static Button register;
	protected static TextField fName;
	protected static TextField lName;
	protected static TextField emailField;
	protected static Label fNameLabel;
	protected static Label lNameLabel;
	protected static Label emailLabel;
	
	protected static Label logo;
	protected static Button back;
	protected static Label registerLabel;
	
	//PASS IN THIS ORDER
	protected static String username;
	protected static String fname;
	protected static String lname;
	protected static String email;
	protected static String password;
	protected static String hash;
	
	protected static String session;
	protected static String dbResult;
	
	
	public static void setStage(Stage stage) throws Exception 
	{
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
		
		registerLabel=new Label("Please Register for Kitchen Wizard");
		registerLabel.getStyleClass().add("title");
		grid.add(registerLabel, 200, 55,500,50);
		
		//Create the label and field for the First Name
    	fNameLabel=new Label("First Name: ");
    	grid.add(fNameLabel,100,120,100,30);
    	
    	fName=new TextField();
    	grid.add(fName,195,120,150,30);
    	
    	//Create the label and field for the Last Name
    	lNameLabel=new Label("Last Name: ");
    	grid.add(lNameLabel,100,150,100,30);
    	
    	lName=new TextField();
    	grid.add(lName,195,150,150,30);
    	
    	//Create the label and field for Email
    	emailLabel=new Label("Email: ");
    	grid.add(emailLabel,100,180,100,30);
    	
    	emailField=new TextField();
    	grid.add(emailField,195,180,150,30);
    	
    	//Create the label and field for Username
    	usernameLabel=new Label("Username: ");
    	grid.add(usernameLabel,100,210, 100, 30);
    	
    	usernameField=new TextField();
        grid.add(usernameField,195, 210, 150, 30);
        
        //Create the label and field for Password
        passwordLabel=new Label("Password: ");
        grid.add(passwordLabel,100, 240, 100, 30);
        
        passwordField=new PasswordField();
        grid.add(passwordField,195, 240, 150, 30);
    	
        //Create the register button
    	register=new Button("Register");
    	register.setOnAction(new EventHandler<ActionEvent>()
    	{
    		//Define the function for when the button is pressed
    		//Get the text from each of the fields and send all of it to the database
			public void handle(ActionEvent event) 
			{
				fname=fName.getText();
        		lname=lName.getText();
        		email=emailField.getText();
        		username=usernameField.getText();
        		password=new String(passwordField.getText());
    			try {
					sendRequest();
					if(dbResult.equals("Account Created"))
					{
						Label accountCreated=new Label("Account Created, Returning to Home Screen");
						accountCreated.getStyleClass().add("success");
						grid.add(accountCreated,285,280,250,100);
						TimeUnit.SECONDS.sleep(5);
						LoginWindow.setStage(stage);
						
					}
				} catch (Exception e1) {
					Label noConnection=new Label("Can't connect to database");
					noConnection.getStyleClass().add("warning");
					grid.add(noConnection, 310, 285,200,100);
				}
				
			}
    	});
    	register.setMinSize(225, 50);
		register.getStyleClass().add("menubutton");
    	grid.add(register,550, 200, 150, 40);
    	
    	//Create the back button which will lead back to the MainWindow
    	back=new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					LoginWindow.setStage(stage);
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
        stage.setTitle("Kitchen Wizard -Register");
		File f=new File("Style.css");
		scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/"));
        stage.setScene(scene);
        stage.show();
	}

	public void start(Stage primaryStage) throws Exception 
	{	
	}
	
	public static void sendRequest() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="register";
		String param1=username;
		String param2=fname;
		String param3=lname;
		String param4=email;
		int hash=password.hashCode();
		String param5=hash+"";
		
		String query=String.format("command=%s&username=%s&fname=%s&lname=%s&email=%s&password=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(param1,charset),
				URLEncoder.encode(param2,charset),
				URLEncoder.encode(param3,charset),
				URLEncoder.encode(param4,charset),
				URLEncoder.encode(param5,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			dbResult=inputLine;
		System.out.println(dbResult);
		in.close();
	}
}