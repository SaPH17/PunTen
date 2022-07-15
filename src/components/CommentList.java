package components;

import database.Database;
import helpers.DateHelper;
import helpers.LayoutHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Comment;
import values.ProfilePicture;

public class CommentList extends VBox{
	private Comment comment;

	public CommentList(Comment comment) {
		this.comment = comment;
		configureLayout();
	}
	
	public void configureLayout() {
		setSpacing(5);
		setPadding(new Insets(20));
		setStyle("-fx-border-color: #f1f1f1");
		setAlignment(Pos.TOP_LEFT);
		
		ImageView profilePicture = new ImageView();
		int idx = Database.getInstance().getProfilePicture(comment.getAuthor());
		String path = idx == -1 ? "placeholder.png" : ProfilePicture.pics[idx];
		LayoutHelper.configureImageViewFile(profilePicture, "./resources/profile-picture/" + path);
		LayoutHelper.addBorderRadius(profilePicture, 35, 35, 35);
		profilePicture.setFitWidth(35);
		profilePicture.setPreserveRatio(true);
		
		String s = comment.getAuthor() + "  ·  " + DateHelper.convertDateToRelativeTime(comment.getDate());
		
		Label authorLbl = new Label(s); 
		authorLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		authorLbl.setTextFill(Color.web("#c1c1c1"));
		
		Label contentLbl = new Label(comment.getContent());
		contentLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		VBox.setMargin(contentLbl, new Insets(0, 0, 0, 50));
		
		HBox h = new HBox();
		h.setSpacing(15);
		h.setAlignment(Pos.CENTER_LEFT);
		h.getChildren().add(profilePicture);
		h.getChildren().add(authorLbl);
		
		getChildren().add(h);
		getChildren().add(contentLbl);
	}

	Comment getComment() {
		return comment;
	}

	void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
