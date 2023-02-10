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
import models.Pun;
import models.RecursivePun;
import values.Constant;
import views.HomePage;

public class RecursiveTab extends WriteTab{
	
	private TextArea contentTxt;
	private TextField referenceTxt;

	public RecursiveTab(String s) {
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
	
		referenceTxt = new TextField();
		referenceTxt.setPrefWidth(250);
		referenceTxt.setPrefHeight(40);
		referenceTxt.setFont(font);
		referenceTxt.setPromptText("Whats the reference?");
		referenceTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
	
		container.getChildren().add(contentTxt);
		container.getChildren().add(referenceTxt);
	}

	@Override
	protected void createPun() {
		if(contentTxt.getText().isBlank()) {
			ModalBuilder.createRegisterFailedModal("Content is required!").showModal();
			return;
		}
		else if(referenceTxt.getText().isBlank()) {
			ModalBuilder.createRegisterFailedModal("Reference is required!").showModal();
			return;
		}
		
		String u = Session.getSession().getUser().getUsername();
		Pun p = new RecursivePun(u, titleTxt.getText(), new Date(), contentTxt.getText(), referenceTxt.getText());
		Database.getInstance().addPun(p);
		StageHelper.getInstance().changePage(new HomePage());
	}

}
