package components.tab;

import java.util.Date;

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
import models.HomophonicPun;
import models.Pun;
import values.Constant;
import views.HomePage;

public class HomophonicTab extends WriteTab{

	private TextArea contentTxt;
	
	public HomophonicTab(String s) {
		super(s);
		h = 347;
	}

	@Override
	protected void createUniqueLayout() {
		contentTxt = new TextArea();
		contentTxt.setPrefWidth(250);
		contentTxt.setFont(font);
		contentTxt.setPromptText("Whats your pun?");
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
		Pun p = new HomophonicPun(u, titleTxt.getText(), new Date(), contentTxt.getText());
		Database.getInstance().addPun(p);
		StageHelper.getInstance().changePage(new HomePage());
	}

}
