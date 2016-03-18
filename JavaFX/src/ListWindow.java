import javafx.stage.Stage;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

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

public class ListWindow extends Application{
	
	//[[40; '076677100145'; 'FAMOUS AMOS FAMOUS AMOS, BITE SIZE COOKIES, CHOCOLATE CHIP'; 'FAMOUS AMOS, BITE SIZE COOKIES, CHOCOLATE CHIP'; 'FAMOUS AMOS'; '2 oz'; 'NONE'; 'http://2.bp.blogspot.com/-Gbn3dT1R9Yo/VPXSJ8lih_I/AAAAAAAALDQ/24wFWdfFvu4/s1600/sorry-image-not-available.png'; 'NONE'; '0%']; [41; '076677100145'; 'FAMOUS AMOS FAMOUS AMOS, BITE SIZE COOKIES, CHOCOLATE CHIP'; 'FAMOUS AMOS, BITE SIZE COOKIES, CHOCOLATE CHIP'; 'FAMOUS AMOS'; '2 oz'; 'NONE'; 'http://2.bp.blogspot.com/-Gbn3dT1R9Yo/VPXSJ8lih_I/AAAAAAAALDQ/24wFWdfFvu4/s1600/sorry-image-not-available.png'; 'NONE'; '0%']]
	//items structured like this
	protected static Label logo;
	protected static int notificationsNum;
	protected static Button notificationsButton;
	
	protected static Button home;
	
	protected static ListView listView;
	protected static Button alphaSort;
	protected static Button expirSort;
	protected static Button delete;
	
	static String session;
	static String items;
	static String id;
	static Stage stage;
	static String[] parsed;
	static ArrayList addItems;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	public static void setStage(Stage stage,String s, String it) throws Exception 
	{
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
		List parsedList=Arrays.asList(parsed);
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

		stage.setTitle("Kitchen Wizard -Your List");
        stage.setScene(scene);
	}

	public static void populate(List item) throws IOException
	{
		for(int i=0;i<item.size()-1;i+=10)
		{
			int index=i;
			HBox hbox=new HBox();
			hbox.setPadding(new Insets(10,10,10,10));
			HBox imageBox=new HBox();
			HBox infoBox=new HBox();
			HBox deleteBox=new HBox();
			
			Label picture=new Label((String)item.get(7));
			picture.setPadding(new Insets(1,10,1,10));
			imageBox.getChildren().add(picture);
			
			Label name=new Label((String)item.get(2));
			name.setPadding(new Insets(1,10,1,10));
			infoBox.getChildren().add(name);
			Label expir=new Label((String)item.get(6));
			expir.setPadding(new Insets(1,10,1,10));
			infoBox.getChildren().add(expir);
			Button moreInfo=new Button("More");
			moreInfo.setPadding(new Insets(1,10,1,10));
			moreInfo.getStyleClass().add("notificationsbutton");
			moreInfo.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event) 
				{
					try {
						ExpandedItemWindow.setStage(stage, session,items,picture,name,expir);;
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
							id=(String) item.get(index);
							try {
								sendRemove();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//item.remove(index);
							listView.getItems().remove(index);
							item.remove(index);
							try {
								populate(item);
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
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException 
	{
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);
	
	    byte[] b = new byte[2048];
	    int length;
	
	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }
	
	    is.close();
	    os.close();
	}
	
	public static void sendRemove() throws IOException
	{
		URL url=new URL("http://52.36.126.156:8080/");
		String charset="UTF-8";
		String command="removeitem";
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