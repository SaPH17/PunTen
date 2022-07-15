package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import helpers.LayoutHelper;
import helpers.Session;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import listener.PictureButtonListener;
import values.ProfilePicture;

public class ChangePictureModal extends Stage{
	private Stage parent;
	private VBox content;
	private StackPane root;
	private Scene scene;
	private Button confirmBtn, selected;

	public ChangePictureModal(Stage parent) {
		this.parent = parent;
		
		root = new StackPane();
		content = new VBox();
		scene = new Scene(root);
		
		root.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.45);"
        );
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		int w = (int) (800 * (1920 / screenBounds.getWidth()));
		int h = (int) (300 * (1080 / screenBounds.getHeight()));
		root.setPadding(new Insets(h, w, h, w));
		
		ObservableList<Node> list = root.getChildren();
		list.add(content);
		
		content.setSpacing(20);
		
		scene.setFill(Color.TRANSPARENT);
				
		initializeButton();
		
		configurePane();
		configureModal();
	}
	
	public void showModal() {
		ScaleTransition st = new ScaleTransition(Duration.millis(200), content);
		st.setFromX(0.5);
		st.setFromY(0.5);
		st.setToX(1);
		st.setToY(1);
		st.setCycleCount(1);
		st.setInterpolator(Interpolator.LINEAR);
		st.play();
		
		showAndWait();
	}
	
	private int i = 0;

	private void configurePane() {
		GridPane pictureGrid = new GridPane();
		pictureGrid.setHgap(20);
		pictureGrid.setVgap(20);
		LayoutHelper.configureGridPaneConstraint(pictureGrid, 3, 3);
		
		for(i = 0; i < ProfilePicture.pics.length; i++) {
			ImageView img = new ImageView();
			LayoutHelper.configureImageViewFile(img, "./resources/profile-picture/" + ProfilePicture.pics[i]);
			img.setFitWidth(125);
			img.setPreserveRatio(true);
			LayoutHelper.addBorderRadius(img, 125, 125, 16);
			
			Button btn = new Button();
			btn.setStyle("-fx-cursor: hand");
			btn.setPadding(new Insets(0));
			btn.setBackground(null);
			
			if(Session.getSession().getUser().getProfilePicture() == i) {
				btn.setStyle("-fx-cursor: hand; -fx-border-width: 4; -fx-border-color: #fc8c04; -fx-border-radius: 16px; -fx-background-insets: 6px");
				selected = btn;
			}
			
			btn.setGraphic(img);
			PictureButtonListener l = new PictureButtonListener(this, btn, i);
			btn.setOnAction(l);
			
			pictureGrid.add(btn, i % 3 , i / 3);
		}
		
		content.getChildren().add(pictureGrid);
		content.getChildren().add(confirmBtn);
		content.setStyle("-fx-background-radius: 8; -fx-background-color: #FFF");
		content.setPadding(new Insets(25));
	};

	private void initializeButton() {
		confirmBtn = new Button("Confirm");

		confirmBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff; -fx-cursor: hand");
		confirmBtn.setPrefWidth(475);
		confirmBtn.setPrefHeight(30);
		confirmBtn.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				close();
			}
		});
		
	}
	
	private void configureModal() {
		setScene(scene);
		initStyle(StageStyle.TRANSPARENT);
		setMaximized(true);
		
		initOwner(parent);
		initModality(Modality.APPLICATION_MODAL);
	}

	public Button getSelected() {
		return selected;
	}

	public void setSelected(Button selected) {
		this.selected = selected;
	}
}
