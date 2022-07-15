package helpers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageHelper {
	private Stage stage;
	private static StageHelper helper;
	
	private StageHelper() { }
	
	public static StageHelper getInstance() {
		if(helper == null) {
			helper = new StageHelper();
		}
		
		return helper;
	}
	
	public void changePage(Scene scene) {
		stage.setScene(scene);
		stage.setMaximized(true);
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
