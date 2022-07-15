package components.tab;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import builder.ModalBuilder;
import database.Database;
import helpers.Session;
import helpers.StageHelper;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import models.CompoundPun;
import models.Pun;
import values.Constant;
import views.HomePage;

public class CompoundTab extends WriteTab{
	
	private TextArea contentTxt;

	public CompoundTab(String s) {
		super(s);
		h = 347;
	}

	@Override
	protected void createUniqueLayout() {
		contentTxt = new TextArea();
		contentTxt.setPrefWidth(250);
		contentTxt.setFont(font);
		contentTxt.setPromptText("Whats your pun? (Separate each pun with an enter)");
		contentTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
	
		container.getChildren().add(contentTxt);
	}

	@Override
	protected void createPun() {
		if(contentTxt.getText().isBlank()) {
			ModalBuilder.createRegisterFailedModal("Content is required!").showModal();
			return;
		}
		
		String u = Session.getSession().getUser().getUsername();
		List<String> a = Arrays.asList(contentTxt.getText().split("\n"));
		
		Pun p = new CompoundPun(u, titleTxt.getText(), new Date(), a);
		Database.getInstance().addPun(p);
		StageHelper.getInstance().changePage(new HomePage());
	}

}
