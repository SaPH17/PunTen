package views;

import components.Logo;
import helpers.LayoutHelper;
import helpers.Session;
import helpers.StageHelper;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import values.Constant;
import values.ProfilePicture;

public abstract class Page extends Scene{
	
	protected VBox dropdownContent;
	protected StackPane root;
	private ImageView caretIcon;
	protected HBox navbar;
	
	public Page() {
		super(new StackPane());
		root = (StackPane) this.getRoot();
		
		initializeNavbar();
		initializeContent();
		configureFloatingButton();
		navbar.toFront();
	}
	
	protected abstract void initializeContent();
	
	private void initializeNavbar() {
		Logo logo = new Logo(100);
		logo.setStyle("-fx-cursor: hand");
		logo.setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event arg0) {
				StageHelper.getInstance().changePage(new HomePage());
			}
		});
		
		Pane placeholder = new Pane();
		HBox.setHgrow(placeholder, Priority.ALWAYS);
		
		StackPane dropdownContainer = configureDropdown();
		
		navbar = new HBox();
		ObservableList<Node> list = navbar.getChildren();
		list.add(logo);
		list.add(placeholder);
		list.add(dropdownContainer);
		
		navbar.setPadding(new Insets(15, 15, 15, 50));
		navbar.setAlignment(Pos.CENTER_LEFT);
		navbar.setSpacing(15);
		navbar.setMaxHeight(20);
		StackPane.setAlignment(navbar, Pos.TOP_CENTER);
		LayoutHelper.configurePaneBackground(navbar, Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		navbar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 24, 0, 0, 0);");	
		HBox.setMargin(logo, new Insets(0, 30, 0, 15));
		HBox.setMargin(dropdownContainer, new Insets(0, 30, 0, 0));
		
		root.getChildren().add(navbar);
	}

	private StackPane configureDropdown() {
		ImageView profilePicture = new ImageView();
		LayoutHelper.configureImageViewFile(profilePicture, "./resources/profile-picture/" + ProfilePicture.pics[Session.getSession().getUser().getProfilePicture()]);
		LayoutHelper.addBorderRadius(profilePicture, 40, 40, 40);
		profilePicture.setFitWidth(40);
		profilePicture.setPreserveRatio(true);
		
		caretIcon = new ImageView();
		LayoutHelper.configureImageViewFile(caretIcon, "./resources/assets/icon-caret.png");
		caretIcon.setFitWidth(15);
		caretIcon.setPreserveRatio(true);
		
		HBox dropdownBtn = new HBox();
		dropdownBtn.setSpacing(20);
		dropdownBtn.setAlignment(Pos.CENTER);
		dropdownBtn.getChildren().add(profilePicture);
		dropdownBtn.getChildren().add(caretIcon);
		dropdownBtn.setStyle("-fx-cursor: hand");
		dropdownBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent arg0) {
				RotateTransition rt = new RotateTransition(Duration.millis(200), caretIcon);
				rt.setFromAngle(dropdownContent.isVisible() ? 180 : 0);
				rt.setToAngle(dropdownContent.isVisible() ? 0 : 180);
				rt.setCycleCount(1);
				rt.setInterpolator(Interpolator.LINEAR);
				rt.play();
				
				dropdownContent.setVisible(!dropdownContent.isVisible());
			}
		});
		
		configureDropdownContent();
		
		Pane p = new Pane();
		p.setTranslateY(75);
		p.setTranslateX(-100);
		p.getChildren().add(dropdownContent);
		p.setManaged(false);
				
		StackPane dropdownContainer = new StackPane();
		dropdownContainer.getChildren().add(dropdownBtn);
		dropdownContainer.getChildren().add(p);
		
		return dropdownContainer;
	}

	private void configureDropdownContent() {
		String username = Session.getSession().getUser().getUsername();
		
		Label usernameLbl = new Label("Welcome, " + username);
		usernameLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		usernameLbl.setWrapText(true);
		usernameLbl.setPadding(new Insets(5, 10, 5, 10));
		VBox.setMargin(usernameLbl, new Insets(0, 0, 10, 0));
		
		Button profileBtn = new Button("My Profile");
		configureDropdownButton(profileBtn);
		
		profileBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				StageHelper.getInstance().changePage(new ProfilePage());
			}
		});
		
		Button logoutBtn = new Button("Logout");
		configureDropdownButton(logoutBtn);
		
		logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Session.getSession().logout();
				StageHelper.getInstance().changePage(new LoginPage());
			}
		});

		dropdownContent = new VBox();
		LayoutHelper.configurePaneBackground(dropdownContent, Color.WHITE, new CornerRadii(8), Insets.EMPTY);
		dropdownContent.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 16, 0, 0, 0);");	
		dropdownContent.setPadding(new Insets(10));
		dropdownContent.setSpacing(5);
		dropdownContent.getChildren().add(usernameLbl);
		dropdownContent.getChildren().add(profileBtn);
		dropdownContent.getChildren().add(logoutBtn);
		dropdownContent.setPrefWidth(200);
		dropdownContent.setVisible(false);
	}
	
	private void configureDropdownButton(Button button) {
		button.setMaxWidth(Double.MAX_VALUE);
		button.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		button.setBackground(null);
		button.setTextAlignment(TextAlignment.LEFT);
		button.setAlignment(Pos.CENTER_LEFT);
		button.setPadding(new Insets(10));
		
		button.setOnMouseEntered(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((Button)arg0.getTarget()).setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 8; -fx-cursor: hand");
			}
		});
		
		button.setOnMouseExited(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((Button)arg0.getTarget()).setStyle("-fx-background-color: #fff; -fx-background-radius: 8; -fx-cursor: hand");
			}
		});
		
	}

	private void configureFloatingButton() {
		int SIZE = 70;
		
		VBox btnContainer = new VBox();
		btnContainer.setStyle("-fx-cursor: hand");
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.setMaxHeight(SIZE);
		btnContainer.setMaxWidth(SIZE);
		btnContainer.setTranslateX(-50);
		btnContainer.setTranslateY(-25);
		LayoutHelper.configurePaneBackground(btnContainer, Constant.ORANGE_PRIMARY, new CornerRadii(SIZE), Insets.EMPTY);
		StackPane.setAlignment(btnContainer, Pos.BOTTOM_RIGHT);
		
		btnContainer.setOnMouseEntered(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((VBox)arg0.getTarget()).setStyle("-fx-background-color: #fc8c04; -fx-background-radius: 70; -fx-cursor: hand");
			}
		});
		
		btnContainer.setOnMouseExited(new EventHandler<Event>() {

			public void handle(Event arg0) {
				((VBox)arg0.getTarget()).setStyle("-fx-background-color: #ff7750; -fx-background-radius: 70; -fx-cursor: hand");
			}
		});
		
		btnContainer.setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event arg0) {
				StageHelper.getInstance().changePage(new WritePage());
			}
		});
		
		ImageView icon = new ImageView();
		LayoutHelper.configureImageViewFile(icon, "./resources/assets/icon-write.png");
		icon.setFitWidth(30);
		icon.setPreserveRatio(true);
		
		btnContainer.getChildren().add(icon);
		
		root.getChildren().add(btnContainer);
	}
}
