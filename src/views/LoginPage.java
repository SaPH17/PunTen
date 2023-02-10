package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import builder.ModalBuilder;
import components.Logo;
import database.Database;
import helpers.LayoutHelper;
import helpers.Session;
import helpers.StageHelper;
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

public class LoginPage extends Scene{
	
	private StackPane root;
	private GridPane inputContainer, formContainer, content;
	private StackPane bannerContainer;
	private TextField usernameTxt;
	private PasswordField passwordTxt;
	private Label greetingLbl, usernameLbl, passwordLbl;
	private Button loginBtn, registerBtn;

	public LoginPage() {
		super(new StackPane());
		root = (StackPane) this.getRoot();
		
		initializeComponent();
		initializeListener();
	}
	
	private void initializeListener() {
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				String username = usernameTxt.getText();
				String password = passwordTxt.getText();
				
				User u = Database.getInstance().authUser(username, password);
				
				if(u == null) {
					ModalBuilder.createLoginFailedModal().showModal();
					return;
				}
				
				ModalBuilder.createLoginSuccessModal().showModal();
				
				StageHelper helper = StageHelper.getInstance();
				Session.getSession().setUser(u);
				helper.changePage(new HomePage());
			}
		});
		
		registerBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				StageHelper helper = StageHelper.getInstance();
				helper.changePage(new RegisterPage());
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
			InputStream stream = new FileInputStream("./resources/assets/banner-login.jpg");
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

		greetingLbl = new Label("Welcome back!");
		greetingLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20 * (Constant.SCREEN_HEIGHT / 1080)));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		
		loginBtn = new Button("Login");
		loginBtn.setStyle("-fx-background-color: #ff7750; -fx-text-fill: #fff; -fx-cursor: hand");
		loginBtn.prefWidthProperty().bind(titleContainer.widthProperty());
		loginBtn.setPrefHeight(40 * (Constant.SCREEN_HEIGHT / 1080));
		loginBtn.setFont(font);

		//LABEL REGISTER
		Label registerLabel = new Label("Not registered yet?");
		registerLabel.setFont(font);
		registerBtn = new Button("Register Here");
		registerBtn.setStyle("-fx-cursor: hand");
		registerBtn.setBackground(null);
		registerBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 14 * (Constant.SCREEN_HEIGHT / 1080)));
		registerBtn.setUnderline(true);
		registerBtn.setTextFill(Constant.ORANGE_PRIMARY);
		
		//LAYOUT REGISTER
		HBox registerContainer = new HBox();
		registerContainer.setAlignment(Pos.CENTER_LEFT);
		ObservableList<Node> list = registerContainer.getChildren();
		list.add(registerLabel);
		list.add(registerBtn);

		inputContainer = new GridPane();
		inputContainer.setHgap(10);
		inputContainer.setVgap(15);
		
		inputContainer.addRow(0, greetingLbl);
		inputContainer.addRow(1, usernameTxt);
		inputContainer.addRow(2, passwordTxt);
		inputContainer.addRow(3, loginBtn);
		inputContainer.addRow(4, registerContainer);

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
