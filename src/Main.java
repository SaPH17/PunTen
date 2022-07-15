import helpers.StageHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import values.Constant;
import views.HomePage;
import views.LoginPage;
import views.ProfilePage;
import views.WritePage;

public class Main extends Application{

	@Override
	public void start(Stage arg0) throws Exception {
		StageHelper.getInstance().setStage(arg0);
		
		arg0.setTitle("PunTen");
		
		arg0.setWidth(Constant.SCREEN_WIDTH);
		arg0.setHeight(Constant.SCREEN_HEIGHT);
		arg0.setMinWidth(Constant.SCREEN_WIDTH);
		arg0.setMinHeight(Constant.SCREEN_HEIGHT);
		
		arg0.setMaximized(true);
		arg0.setScene(new LoginPage());
//		arg0.setScene(new HomePage());
//		arg0.setScene(new WritePage());
//		arg0.setScene(new ProfilePage());
		arg0.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
 