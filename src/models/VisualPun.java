package models;

import java.util.Date;

import components.PunCard;
import helpers.LayoutHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VisualPun extends Pun{
	
	private String imageLink;
	
	public VisualPun(String author, String title, Date date, String imageLink) {
		super(author, title, date);
		this.imageLink = imageLink;
		this.type = "Visual";
	}

	@Override
	public Pane getContentLayout() {
		VBox container = new VBox();
		
		ImageView image = new ImageView();
		LayoutHelper.configureImageViewFile(image, imageLink);
		image.setFitWidth(600);
		image.setPreserveRatio(true);

		container.getChildren().add(image);
		container.setAlignment(Pos.CENTER);
		VBox.setMargin(image, new Insets(10, 0, 0, 0));
		
		return container;
	}

	@Override
	public String getUniqueStringFormat() {
		return imageLink;
	}

}
