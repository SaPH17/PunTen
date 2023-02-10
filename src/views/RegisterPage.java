package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import builder.ModalBuilder;
import components.Logo;
import database.Database;
import helpers.LayoutHelper;
import helpers.StageHelper;
import helpers.ValidationHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.User;
import values.Constant;

public class RegisterPage extends Scene{
	
	private StackPane root;
	private GridPane inputContainer, formContainer, content;
	private StackPane bannerContainer;
	private TextField usernameTxt;
	private PasswordField passwordTxt;
	private Label greetingLbl, usernameLbl, passwordLbl;
	private Button loginBtn, registerBtn;

	public RegisterPage() {
		super(new StackPane());
		root = (StackPane) this.getRoot();
		
		initializeComponent();
		initializeListener();
	}
	
	private boolean isFormValid() {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		
		if(username.length() < 5) {
			ModalBuilder.createRegisterFailedModal("Username must be at least 5 characters").showModal();
			return false;
		}
		else if(!Database.getInstance().usernameAvailable(username)) {
			ModalBuilder.createRegisterFailedModal("Username is taken").showModal();
			return false;
		}
		else if(password.length() < 5) {
			ModalBuilder.createRegisterFailedModal("Password must be at least 5 characters").showModal();
			return false;
		}
		else if(!ValidationHelper.containAlphanum(password)) {
			ModalBuilder.createRegisterFailedModal("Password must contains \n alphabet and number").showModal();
			return false;
		}
		
		return true;
	}
	
	private void initializeListener() {
		registerBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(!isFormValid()) {
					return;
				}
				
				Database.getInstance().addUser(new User(usernameTxt.getText(), passwordTxt.getText()));
				ModalBuilder.createRegisterSuccessModal().show();
				
				StageHelper helper = StageHelper.getInstance();
				helper.changePage(new LoginPage());
			}
		});
		
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				StageHelper helper = StageHelper.getInstance();
				helper.changePage(new LoginPage());
			}
		});
	}
	
	private void initializeComponent() {
		initializeBanner();
		initializeForm();
		
		StackPane container = configureContainer();
		
		GridPane background = new GridPane();
		LayoutHelper.configureGridPaneConstraint(background, 1, 1);
		LayoutHelper.configurePaneBackground(background, Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		
		ObservableList<Node> list = root.getChildren();
		list.add(background);
		list.add(container);
	}
	
	private StackPane configureContainer() {
		double w = 1280 * (Constant.SCREEN_WIDTH / 1920);
		double h = 720 * (Constant.SCREEN_HEIGHT / 1080);
		
		double ww = 1400 * (Constant.SCREEN_WIDTH / 1920);
		double hh = 700 * (Constant.SCREEN_HEIGHT / 1080);
		
		double mw = 315 * (Constant.SCREEN_WIDTH / 1920);
		double mh = 150 * (Constant.SCREEN_HEIGHT / 1080); 
		
		content = new GridPane();
		LayoutHelper.configureGridPaneConstraint(content, 1, 2);
		LayoutHelper.addBorderRadius(content, ww, hh, 16);

		content.addColumn(0, bannerContainer);
		content.addColumn(1, formContainer);
		content.setPrefWidth(w);
		content.setPrefHeight(h);
		
		StackPane container = new StackPane();
		container.getChildren().add(content);
		container.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 16, 0, 0, 0);");
		StackPane.setMargin(container, new Insets(mh, mw, mh, mw));

		return container;
	}
	
	private void initializeBanner() {
		GridPane backgroundImage = new GridPane();
		LayoutHelper.configureGridPaneConstraint(backgroundImage, 1, 1);
		LayoutHelper.configurePaneBackground(backgroundImage, Constant.ORANGE_PRIMARY, 
				CornerRadii.EMPTY, Insets.EMPTY);
		
		backgroundImage.setPadding(new Insets(100 * (Constant.SCREEN_WIDTH / 1920)));
		
		double w = 500 * (Constant.SCREEN_WIDTH / 1920);
		
		ImageView imageView = new ImageView();
		imageView.setFitWidth(w);
		imageView.setPreserveRatio(true);
        LayoutHelper.addBorderRadius(imageView, w, w, 20);

		try {
			InputStream stream = new FileInputStream("./resources/assets/banner-register.jpeg");
			Image image = new Image(stream);
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		bannerContainer = new StackPane();
		backgroundImage.addColumn(0, imageView);
		
		ObservableList<Node> list = bannerContainer.getChildren();
		list.add(backgroundImage);
	}
	
	private void initializeForm() {
		GridPane titleContainer = new GridPane();
		
		double w = 300 * (Constant.SCREEN_WIDTH / 1920);

		Logo logo = new Logo(w);
		titleContainer.addRow(0, logo);
		titleContainer.setPadding(new Insets(0, 0, 75, 0));
		
		Font font = Font.font("Verdana", FontWeight.NORMAL, 14 * (Constant.SCREEN_HEIGHT / 1080));

		usernameTxt = new TextField();
		passwordTxt = new PasswordField();
		
		usernameTxt.prefWidthProperty().bind(titleContainer.widthProperty());
		usernameTxt.setPrefHeight(40 * (Constant.SCREEN_HEIGHT / 1080));
		usernameTxt.setFont(font);
		usernameTxt.setPromptText("Username");
		usernameTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
		
		passwordTxt.prefWidthProperty().bind(titleContainer.widthProperty());
		passwordTxt.setPrefHeight(40 * (Constant.SCREEN_HEIGHT / 1080));
		passwordTxt.setFont(font);
		passwordTxt.setPromptText("Password");
		passwordTxt.setBorder(new Border(new BorderStroke(Constant.ORANGE_PRIMARY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));

		greetingLbl = new Label("Register now!");
		greetingLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20 * (Constant.SCREEN_HEIGHT / 1080)));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		
		registerBtn = new Button("Register");
		registerBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff;-fx-cursor: hand");
		registerBtn.prefWidthProperty().bind(titleContainer.widthProperty());
		registerBtn.setPrefHeight(40 * (Constant.SCREEN_HEIGHT / 1080));
		registerBtn.setFont(font);
		
		//LABEL REGISTER
		Label loginLabel = new Label("Already have an account?");
		loginLabel.setFont(font);
		loginBtn = new Button("Login Here");
		loginBtn.setStyle("-fx-cursor: hand");
		loginBtn.setBackground(null);
		loginBtn.setFont(font);
		loginBtn.setTextFill(Constant.ORANGE_PRIMARY);
		
		//LAYOUT REGISTER
		HBox loginContainer = new HBox();
		loginContainer.setAlignment(Pos.CENTER_LEFT);
		ObservableList<Node> list = loginContainer.getChildren();
		list.add(loginLabel);
		list.add(loginBtn);

		inputContainer = new GridPane();
		inputContainer.setHgap(10);
		inputContainer.setVgap(15);
		
		inputContainer.addRow(0, greetingLbl);
		inputContainer.addRow(1, usernameTxt);
		inputContainer.addRow(2, passwordTxt);
		inputContainer.addRow(3, registerBtn);
		inputContainer.addRow(4, loginContainer);

		formContainer = new GridPane();
		LayoutHelper.configurePaneBackground(formContainer, Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		double px = 125  * (Constant.SCREEN_WIDTH / 1920);
		double py = 75 * (Constant.SCREEN_HEIGHT / 1080);
		formContainer.setPadding(new Insets(py, px, py, px));
		formContainer.addRow(0, titleContainer);
		formContainer.addRow(1, inputContainer);
		formContainer.setAlignment(Pos.CENTER);
	}
	
}
