package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Logo extends ImageView{
	
	private final String FILE_PATH = "./resources/logo.png";
	
	public Logo(int size) {		
		setX(10);
		setY(10);
		setFitWidth(size);
		setPreserveRatio(true);
		
		try {
			InputStream stream = new FileInputStream(FILE_PATH);
			Image image = new Image(stream);
			setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
