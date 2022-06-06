import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("testing");
		arg0.setScene(new Scene(new Label("testing")));
		arg0.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
