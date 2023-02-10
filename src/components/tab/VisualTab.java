package components.tab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import builder.ModalBuilder;
import database.Database;
import helpers.LayoutHelper;
import helpers.Session;
import helpers.StageHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Pun;
import models.VisualPun;
import values.Constant;
import views.HomePage;

public class VisualTab extends WriteTab{
	
	private ImageView preview;
	private TextField contentTxt;
	private boolean flag = false;

	public VisualTab(String s) {
		super(s);
		h = 340;
	}

	@Override
	protected void createUniqueLayout() {
		contentTxt = new TextField();
		contentTxt.setPrefHeight(40);
		contentTxt.setFont(font);
		contentTxt.setPromptText("Whats your file name?");
		contentTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
	
		HBox h = new HBox();
		h.setSpacing(20);
		HBox.setHgrow(contentTxt, Priority.ALWAYS);
		
		preview = new ImageView();
		preview.setFitHeight(150);
		preview.setPreserveRatio(true);
		LayoutHelper.configureImageViewFile(preview, "./resources/assets/placeholder.png");

		Button previewBtn = new Button("Search");
		previewBtn.setStyle("-fx-background-color: #fff; -fx-text-fill: #ff7750; -fx-cursor: hand; -fx-border-color: #ff7750; -fx-border-width: 1; -fx-background-radius: 8px;");
		previewBtn.setPrefHeight(35);
		previewBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		
		previewBtn.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent arg0) {
				String c = contentTxt.getText().isBlank() ? "placeholder.png" : contentTxt.getText();
				
				try {
					InputStream stream = new FileInputStream("./resources/puns/" + c);
					Image image = new Image(stream);
					preview.setImage(image);
					flag = true;
				} catch (FileNotFoundException e) {
					InputStream stream;
					flag = false;
					try {
						stream = new FileInputStream("./resources/assets/placeholder.png");
						Image image = new Image(stream);
						preview.setImage(image);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		h.getChildren().addAll(contentTxt, previewBtn);
		
		container.getChildren().add(h);
		container.getChildren().add(preview);
	}

	@Override
	protected void createPun() {
		if(!flag) {
			ModalBuilder.createRegisterFailedModal("Image is required!").showModal();
			return;
		}
		boolean flag2 = false;
		String[] ext = {".jpg", ".jpeg", ".png"};
		for (String s : ext) {
			if(contentTxt.getText().endsWith(s)) {
				flag2 = true;
				break;
			}
		}
		
		if(!flag2) {
			ModalBuilder.createRegisterFailedModal("File must be an image!").showModal();
			return;
		}
		
		String u = Session.getSession().getUser().getUsername();
		Pun p = new VisualPun(u, titleTxt.getText(), new Date(), contentTxt.getText());
		Database.getInstance().addPun(p);
		StageHelper.getInstance().changePage(new HomePage());
	}

}
