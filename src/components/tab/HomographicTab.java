package components.tab;

import java.util.Date;

import builder.ModalBuilder;
import database.Database;
import helpers.Session;
import helpers.StageHelper;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import models.HomographicPun;
import models.Pun;
import values.Constant;
import views.HomePage;

public class HomographicTab extends WriteTab{

	private TextArea contentTxt;
	private TextField keywordTxt;
	
	public HomographicTab(String s) {
		super(s);
		h = 412;
	}

	@Override
	protected void createUniqueLayout() {
		contentTxt = new TextArea();
		contentTxt.setPrefWidth(250);
		contentTxt.setFont(font);
		contentTxt.setPromptText("Whats your pun?");
		contentTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
	
		keywordTxt = new TextField();
		keywordTxt.setPrefWidth(250);
		keywordTxt.setPrefHeight(40);
		keywordTxt.setFont(font);
		keywordTxt.setPromptText("Whats the keyword?");
		keywordTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
	
		container.getChildren().add(contentTxt);
		container.getChildren().add(keywordTxt);
	}

	@Override
	protected void createPun() {
		if(contentTxt.getText().isBlank()) {
			ModalBuilder.createRegisterFailedModal("Content is required!").showModal();
			return;
		}
		else if(keywordTxt.getText().isBlank()) {
			ModalBuilder.createRegisterFailedModal("Keyword is required!").showModal();
			return;
		}
		
		String u = Session.getSession().getUser().getUsername();
		Pun p = new HomographicPun(u, titleTxt.getText(), new Date(), contentTxt.getText(), keywordTxt.getText());
		Database.getInstance().addPun(p);
		StageHelper.getInstance().changePage(new HomePage());
	}
	
}
