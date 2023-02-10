package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import values.Constant;

public class Modal extends Stage{
	private Stage parent;
	private String title, path;
	private VBox content;
	private StackPane root;
	private Scene scene;
	private Button closeBtn;

	public Modal(Stage parent, String title, String path) {
		this.parent = parent;
		this.title = title;
		this.path = path;
		
		root = new StackPane();
		content = new VBox();
		scene = new Scene(root);
		
		root.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.45);"
        );
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
		ObservableList list = root.getChildren();
		list.add(content);
		
		scene.setFill(Color.TRANSPARENT);
				
		initializeButton();
		
		configurePane();
		configureModal();
		content.maxWidthProperty().bind(root.widthProperty().divide(4));
		content.maxHeightProperty().bind(root.heightProperty().divide(3));
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

	private void configurePane() {
		Label titleLbl = new Label(title);
		titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		titleLbl.setWrapText(true);
		titleLbl.setTextAlignment(TextAlignment.CENTER);
		titleLbl.setMinHeight(40);

		ImageView imageView = new ImageView();
		
		imageView.setFitWidth(400 * (Constant.SCREEN_WIDTH / 1920));
		imageView.setFitHeight(400 * (Constant.SCREEN_HEIGHT / 1080));
		imageView.setStyle("-fx-background-size: cover");
		
		InputStream stream;
		try {
			stream = new FileInputStream(path);
			Image image = new Image(stream);
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		content.setAlignment(Pos.CENTER);
		content.setSpacing(25);
		
		ObservableList list = content.getChildren();
		list.add(titleLbl);
		list.add(imageView);
		list.add(closeBtn);
		
		content.setStyle("-fx-background-radius: 10; -fx-background-color: #FFF");
		content.setPadding(new Insets(25, 40, 25, 40));
	};
	
	private void initializeButton() {
		closeBtn = new Button("Close");

		closeBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff; -fx-cursor: hand");
		closeBtn.prefWidthProperty().bind(content.widthProperty());
		closeBtn.setPrefHeight(30);
		closeBtn.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
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
}
