package models;

import java.util.Date;

import components.PunCard;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomophonicPun extends Pun{
	
	private String content;

	public HomophonicPun(String author, String title, Date date, String content) {
		super(author, title, date);
		this.content = content;
		this.type = "Homophonic";
	}

	@Override
	public Pane getContentLayout() {
		VBox container = new VBox();
		
		Label contentLbl = new Label(content);
		contentLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

		container.getChildren().add(contentLbl);
		
		return container;
	}

	@Override
	public String getUniqueStringFormat() {
		return content;
	}

	

}
