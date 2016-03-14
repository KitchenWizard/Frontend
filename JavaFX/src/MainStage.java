import javafx.stage.Stage;
import javafx.application.Application;

public class MainStage extends Application{
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	public void start(Stage stage) throws Exception 
	{
		LoginWindow.setStage(stage);
        stage.show();
	}

}
