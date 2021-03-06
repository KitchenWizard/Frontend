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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ExpandedRecipeWindow extends Application{
	
	protected  Label logo;
	protected  int notificationsNum;
	protected  Button notificationsButton;
	protected  Button back;
	
	protected  ImageView picture;
	protected  String name;
	protected  Label descriptionLabel;
	protected  Label ingredientsLabel;
	protected  Label cookLabel;
	protected  Label prepLabel;
	
	protected  TextField nameField;
	protected  TextField descriptionField;
	protected TextField prepField;
	protected TextField cookField;
	
	protected  String groups;
	protected static ComboBox itemsBox;
	protected static TextField amount;
	protected Button sendIngredients;
	
	protected  String session;
	protected  String newitems;
	protected  String items;
	protected  String dbResponse;
	protected  String id;
	protected  String description;
	protected  String ingredients;
	protected  String cook;
	protected  String prep;
	
	protected  Button confirm;
	
	public  void main(String[] args) 
	{
        launch(args);
    }
	
	public  void setStage(Stage stage, String s,String it,String n,String d,String in,String p,String c,String idd) throws Exception 
	{
		id=idd;
		items=it;
		session=s;
		description=d;
		ingredients=in;
		prep=p;
		cook=c;
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
		
		/*final ImageView selectedImage = new ImageView();
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
        grid.add(selectedImage, 100, 200);*/
		
		name=n;
		Label nameLabel=new Label("Name: ");
		grid.add(nameLabel, 250, 100,100,20);
		nameField=new TextField(name);
		grid.add(nameField, 350, 100,150,30);
		
		descriptionLabel=new Label("Description: ");
		grid.add(descriptionLabel, 250, 150,100,20);
		descriptionField=new TextField(description);
		grid.add(descriptionField, 350, 150,150,30);
		
		prepLabel=new Label("Prep Time: ");
		grid.add(prepLabel, 250,200,100,20);
		prepField=new TextField(prep);
		grid.add(prepField, 350, 200,100,30);
		
		cookLabel=new Label("Cook Time: ");
		grid.add(cookLabel, 250, 250,100,20);
		cookField=new TextField(cook);
		grid.add(cookField,350,250,100,30);
		
		ingredientsLabel=new Label("Ingredients");
		grid.add(ingredientsLabel, 250, 300,100,20);
		
		getGroups();
		ObservableList<String> groupList=FXCollections.observableArrayList(groups.split(";"));
		System.out.println(groupList.get(0));
		itemsBox=new ComboBox(groupList);
		grid.add(itemsBox,350, 300, 100,30);
		amount=new TextField();
		grid.add(amount, 450, 300,100,30);
		
		sendIngredients=new Button("Send");
		sendIngredients.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
        			sendCurrentIngredient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		grid.add(sendIngredients, 700, 300,75,30);
		
		confirm=new Button("Update");
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
        	}
        });
		confirm.getStyleClass().add("menubutton");
		grid.add(confirm, 350,400,200,100);
		
		//Create the bottom bar of the program
		back=new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>()
        {
			public void handle(ActionEvent event)
        	{
        		try {
					RecipesListWindow home=new RecipesListWindow();
					home.setStage(stage, session,items);
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
			newitems=inputLine;
		System.out.println(newitems);
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
	
	public  void sendExtra() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String sessionkey=session;
		String command="updaterecipe";
		String nameupdate=nameField.getText();
		String descriptionupdate=descriptionField.getText();
		String prepupdate=prepField.getText();
		String cookupdate=cookField.getText();
		String pid=id;
		String query=String.format("command=%s&sessionkey=%s&name=%s&description=%s&preptime=%s&cooktime=%s&recipeid=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(sessionkey,charset),
				URLEncoder.encode(nameupdate,charset),
				URLEncoder.encode(descriptionupdate,charset),
				URLEncoder.encode(prepupdate,charset),
				URLEncoder.encode(cookupdate,charset),
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
	
	public void sendCurrentIngredient() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="updaterecipe";
		String session1=session;
		String sessionkey=session;
		String groupID=itemsBox.getValue().toString();
		String[] groupIDArray=groupID.split(",");
		String grouptosend=groupIDArray[0];
		grouptosend=grouptosend.trim();
		grouptosend=grouptosend.replace("(", "");
		String amount1=amount.getText();
		String toSend=grouptosend+'`'+amount1;
		String action="ADD";
		String id1=id.trim();
		
		
		String query=String.format("command=%s&sessionkey=%s&groupid=%s&quantity=%s&itemaction=%s&recipeid=%s&",
				URLEncoder.encode(command,charset),
				URLEncoder.encode(session1,charset),
				URLEncoder.encode(grouptosend,charset),
				URLEncoder.encode(amount1,charset),
				URLEncoder.encode(action,charset),
				URLEncoder.encode(id1,charset));
		
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
			
		in.close();
	}
	
	
}