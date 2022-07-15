package models;

import java.util.Date;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RecursivePun extends Pun{
	
	private String content, reference;

	public RecursivePun(String author, String title, Date date, String content, String reference) {
		super(author, title, date);
		this.content = content;
		this.reference = reference;
		this.type = "Recursive";
	}

	@Override
	public Pane getContentLayout() {
		VBox container = new VBox();
		container.setSpacing(15);
		
		Label contentLbl = new Label(content);
		contentLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		
		Label keywordLbl = new Label("Reference: " + reference);
		keywordLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		keywordLbl.setTextFill(Color.web("#c1c1c1"));

		container.getChildren().add(contentLbl);
		container.getChildren().add(keywordLbl);
		
		return container;
	}

	@Override
	public String getUniqueStringFormat() {
		return content + "," + reference;
	}

}
