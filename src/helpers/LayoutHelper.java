package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class LayoutHelper {
	public static void configureGridPaneConstraint(GridPane pane, int row, int col) {
		ColumnConstraints cc = new ColumnConstraints();
		RowConstraints rc = new RowConstraints();
		cc.setPercentWidth(100 / col);
		rc.setPercentHeight(100 / row);
		
		for(int i = 0; i < col; i++) {
			pane.getColumnConstraints().add(cc);
		}
		
		for(int i = 0; i < row; i++) {
			pane.getRowConstraints().add(rc);
		}
	}
	
	public static void configurePaneBackground(Pane pane, Paint paint, CornerRadii corner, Insets insets) {
		BackgroundFill backgroundFill = new BackgroundFill(paint, corner, insets);
		Background background = new Background(backgroundFill);
		pane.setBackground(background);
	}
	
	public static void addBorderRadius(Node node, double x, double y, int radius) {
		Rectangle clip = new Rectangle(x, y);
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        node.setClip(clip);
	}
	
	public static void configureImageViewFile(ImageView view, String path) {
		try {
			InputStream stream = new FileInputStream(path);
			Image image = new Image(stream);
			view.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			InputStream stream;
			try {
				stream = new FileInputStream("./resources/assets/placeholder.png");
				Image image = new Image(stream);
				view.setImage(image);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

}
