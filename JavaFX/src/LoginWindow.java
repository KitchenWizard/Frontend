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

public class LoginWindow extends Application{

	protected TextField usernameField;
    protected PasswordField passwordField;
    protected Label usernameLabel;
    protected Label passwordLabel;
    protected Label login;
	protected Button confirm;
	protected Button register;
	protected Label wrongPass;
	protected Label noConnection;
	
	protected Label logo;
	
	protected String username;
	protected String password;
	protected String session="";
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	public void setStage(Stage stage) throws Exception
	{
		GridPane grid=new GridPane();
		grid.setPadding(new Insets(5,5,5,5));
		grid.setVgap(5);
		grid.setHgap(5);
		Scene scene = new Scene(grid, 800, 450);

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
		
		login=new Label("Please Log In");
		login.getStyleClass().add("title");
		grid.add(login, 320, 55,200,50);
		
		usernameLabel=new Label("Username:");
		grid.add(usernameLabel, 100, 150,200,50);
		
		usernameField=new TextField();
		grid.add(usernameField, 185, 150,150,50);
		
		passwordLabel=new Label("Password:");
		grid.add(passwordLabel, 375, 150,200,50);
		
		passwordField=new PasswordField();
		grid.add(passwordField, 455, 150,150,50);
		
		HomeWindow login=new HomeWindow();
		confirm=new Button("Confirm");
		confirm.setMinSize(125, 28);
		confirm.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				username=usernameField.getText();
				password=new String(passwordField.getText());
				
				System.out.println(username);
				System.out.println(password);
				try {
					sendRequest();
				} catch (IOException e1) {
					noConnection.setVisible(true);
				}
				if(session.equals(""))
				{
					
				} else if(session.equals("INVAILD_LOGIN"))
				{
					wrongPass.setVisible(true);
				}else
				{
					try 
					{
						login.setStage(stage,session);
					} catch (Exception e) {
						noConnection.setVisible(true);
					}
				}
			}
		});
		confirm.getStyleClass().add("menubutton");
		grid.add(confirm, 635, 148,100,50);
		
		register=new Button("Register");
		register.setMinSize(150, 35);
		register.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent event) 
					{
						try {
							RegisterWindow reg=new RegisterWindow();
							reg.setStage(stage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		register.getStyleClass().add("menubutton");
		grid.add(register, 318, 250,150,150);
		
		wrongPass=new Label("Incorrect Username or Password");
		wrongPass.setVisible(false);
		wrongPass.getStyleClass().add("warning");
		grid.add(wrongPass, 305, 215,250,50);
		
		noConnection=new Label("Cannot connect to database");
		noConnection.setVisible(false);
		noConnection.getStyleClass().add("warning");
		grid.add(noConnection, 305, 235,200,50);
		
		stage.setTitle("Kitchen Wizard -Please Login");
        stage.setScene(scene);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void sendRequest() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="login";
		String param1=username;
		int hash=password.hashCode();
		String param2=hash+"";
		
		String query=String.format("command=%s&username=%s&password=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(param1,charset),
				URLEncoder.encode(param2,charset));
		
		URLConnection connection=new URL(url+"?"+query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine=in.readLine())!=null)
			session=inputLine;
		System.out.println(session);
		in.close();
	}
}