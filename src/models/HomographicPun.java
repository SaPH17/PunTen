package models;

import java.util.Date;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomographicPun extends Pun{
	
	private String content, keyword;
	
	public HomographicPun(String author, String title, Date date, String content, String keyword) {
		super(author, title, date);
		this.content = content;
		this.keyword = keyword;
		this.type = "Homographic";
	}

	@Override
	public Pane getContentLayout() {
		VBox container = new VBox();
		container.setSpacing(15);
		
		Label contentLbl = new Label(content);
		contentLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		
		Label keywordLbl = new Label("Keywords: " + keyword);
		keywordLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		keywordLbl.setTextFill(Color.web("#c1c1c1"));

		container.getChildren().add(contentLbl);
		container.getChildren().add(keywordLbl);
		
		return container;
	}

	@Override
	public String getUniqueStringFormat() {
		return content + "," + keyword;
	}

}
