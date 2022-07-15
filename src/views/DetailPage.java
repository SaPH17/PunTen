package views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import builder.ModalBuilder;
import components.CommentList;
import database.Database;
import helpers.DateHelper;
import helpers.LayoutHelper;
import helpers.Session;
import helpers.StageHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Comment;
import models.Pun;

public class DetailPage extends Page{
	
	private Pun pun;
	private ScrollPane scroll;
	private VBox container, detail;
	private TextArea commentTxt;
	
	public DetailPage(Pun pun) {
		super();
		this.pun = pun;
		
		initializePunDetail();
		initializeCommentBox();
		initializeAllComment();
	}

	@Override
	protected void initializeContent() {
		container = new VBox();
		container.setSpacing(20);
		container.setPadding(new Insets(25, 25, 100, 25));
		container.setAlignment(Pos.CENTER);
		
		scroll = new ScrollPane();
		scroll.setBackground(null);
		scroll.setContent(container);
		scroll.translateYProperty().bind(navbar.heightProperty());
		scroll.setStyle("-fx-unit-increment: 10 ; -fx-block-increment: 50 ;");

		container.prefWidthProperty().bind(scroll.widthProperty());
		
		this.root.getChildren().add(scroll);
	}
	
	private void initializePunDetail() {
		detail = new VBox();
		
		LayoutHelper.configurePaneBackground(detail, Color.WHITE, new CornerRadii(8), Insets.EMPTY);
		detail.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 12, 0, 0, 0);");	
		detail.setPadding(new Insets(20));
		detail.setPrefWidth(500);
		detail.setSpacing(15);
		detail.maxWidthProperty().bind(container.widthProperty().divide(3));
		ObservableList<Node> list = detail.getChildren();
		
		Label titleLbl = new Label(pun.getTitle());
		titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		
		String author = "Posted by " + pun.getAuthor() + " - " + DateHelper.convertDateToRelativeTime(pun.getDate());

		Label authorLbl = new Label(author);
		authorLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		authorLbl.setTextFill(Color.web("#c1c1c1"));
		
		ImageView commentIcon = new ImageView();
		LayoutHelper.configureImageViewFile(commentIcon, "./resources/icon-comment.png");
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
		
		container.getChildren().add(detail);
	}

	private void initializeCommentBox() {
		StackPane commentSection = new StackPane();
		commentSection.setAlignment(Pos.BOTTOM_RIGHT);
		commentSection.maxWidthProperty().bind(container.widthProperty().divide(3));
		VBox.setMargin(commentSection, new Insets(15, 0, 0, 0));

		commentTxt = new TextArea();
		commentTxt.setPrefRowCount(6);
		commentTxt.setStyle("-fx-text-box-border: transparent; -fx-border-color: #c1c1c1");
		commentTxt.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		commentTxt.setWrapText(true);
		
		Button commentBtn = new Button("Comment");
		commentBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff; -fx-cursor: hand");
		commentBtn.setPrefWidth(110);
		commentBtn.setPrefHeight(35);
		commentBtn.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		StackPane.setMargin(commentBtn, new Insets(10));
		
		commentBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				String c = commentTxt.getText();
				String a = Session.getSession().getUser().getUsername();
				
				if(c.isBlank()) {
					ModalBuilder.createRegisterFailedModal("Please fill comment!");
					return;
				}
				
				pun.addComment(new Comment(a, c, new Date()));
				Database.getInstance().writePunCSV();
				StageHelper.getInstance().changePage(new DetailPage(pun));
			}
		});
		
		commentSection.getChildren().add(commentTxt);
		commentSection.getChildren().add(commentBtn);

		detail.getChildren().add(commentSection);
	}
	
	private void initializeAllComment() {
		List<Comment> comments = pun.getComments();
		
		VBox commentContainer = new VBox();
		commentContainer.setAlignment(Pos.CENTER);
		VBox.setMargin(commentContainer, new Insets(15, 0, 0, 0));
		commentContainer.maxWidthProperty().bind(container.widthProperty().divide(3));
		LayoutHelper.configurePaneBackground(commentContainer, Color.WHITE, new CornerRadii(8), Insets.EMPTY);
		
		for (Comment comment : comments) {
			CommentList c = new CommentList(comment);
			commentContainer.getChildren().add(c);
		}
		
		detail.getChildren().add(commentContainer);
	}
}
