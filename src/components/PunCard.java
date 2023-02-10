package components;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import helpers.DateHelper;
import helpers.LayoutHelper;
import helpers.StageHelper;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Pun;
import views.DetailPage;

public class PunCard extends VBox{
	private Pun pun;

	public PunCard(Pun pun) {
		this.pun = pun;
		
		configureLayout();
	}

	private void configureLayout() {
		LayoutHelper.configurePaneBackground(this, Color.WHITE, new CornerRadii(8), Insets.EMPTY);
		setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 12, 0, 0, 0); -fx-cursor: hand");	
		setPadding(new Insets(20));
		setSpacing(15);
		ObservableList<Node> list = getChildren();
		
		Label titleLbl = new Label(pun.getTitle());
		titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		
		String author = "Posted by " + pun.getAuthor() + " - " + DateHelper.convertDateToRelativeTime(pun.getDate());

		Label authorLbl = new Label(author);
		authorLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		authorLbl.setTextFill(Color.web("#c1c1c1"));
		
		ImageView commentIcon = new ImageView();
		LayoutHelper.configureImageViewFile(commentIcon, "./resources/assets/icon-comment.png");
		commentIcon.setFitWidth(25);
		commentIcon.setPreserveRatio(true);
		
		Button commentContainer = new Button(pun.getComments().size() + " comment(s)");
		commentContainer.setGraphic(commentIcon);
		commentContainer.setAlignment(Pos.CENTER_LEFT);
		commentContainer.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		commentContainer.setTextFill(Color.web("#c1c1c1"));
		commentContainer.setPadding(new Insets(7.5));
		commentContainer.setBackground(null);
		commentContainer.setGraphicTextGap(10);
		
		commentContainer.setOnMouseEntered(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((Button)arg0.getTarget()).setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 8; -fx-cursor: hand");
			}
		});
		
		commentContainer.setOnMouseExited(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((Button)arg0.getTarget()).setStyle("-fx-background-color: #fff; -fx-background-radius: 8; -fx-cursor: hand");
			}
		});
		
		list.add(authorLbl);
		list.add(titleLbl);
		list.add(pun.getContentLayout());
		list.add(commentContainer);
		
		setOnMouseEntered(new EventHandler<Event>() {

			public void handle(Event arg0) {
				setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.125), 36, 0, 0, 0); -fx-cursor: hand");	
			}
		});
		
		setOnMouseExited(new EventHandler<Event>() {

			public void handle(Event arg0) {
				setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 12, 0, 0, 0); -fx-cursor: hand");	
			}
		});
		setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event arg0) {
				StageHelper.getInstance().changePage(new DetailPage(pun));
			}
		});
	}
	
}
