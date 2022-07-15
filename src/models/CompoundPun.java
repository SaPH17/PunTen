package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CompoundPun extends Pun{

	private List<String> contents;
	
	public CompoundPun(String author, String title, Date date, List<String> contents) {
		super(author, title, date);
		this.contents = contents;
		this.type = "Compound";
	}

	@Override
	public Pane getContentLayout() {
		VBox container = new VBox();
		container.setSpacing(15);
		
		for (String string : contents) {
			string = " > " + string;
			Label contentLbl = new Label(string);
			contentLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
			
			container.getChildren().add(contentLbl);
		}
		
		return container;
	}

	@Override
	public String getUniqueStringFormat() {
		String res = "";
		for(int i = 0; i < contents.size(); i++) {
			if(i == 0) {
				res += contents.get(i);
			}
			else {
				res += "," + contents.get(i);
			}
		}
		return res;
	}
	
}
