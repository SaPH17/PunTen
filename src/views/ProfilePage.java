package views;

import java.util.ArrayList;
import java.util.function.Predicate;

import components.ChangePictureModal;
import components.PunCard;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Pun;
import values.ProfilePicture;

public class ProfilePage extends Page{

	private ScrollPane scroll;
	private VBox container;
	private ArrayList<Pun> punList;
	private ImageView profilePicture;

	public ProfilePage() {
		super();
	}

	@Override
	protected void initializeContent() {
		punList = loadPun();

		container = new VBox();
		container.setSpacing(20);
		container.setPadding(new Insets(25));
		container.setAlignment(Pos.CENTER);
		
		configureProfileSection();
		
		ObservableList<Node> list = container.getChildren();
		for (Pun pun : punList) {
			PunCard card = new PunCard(pun);
			card.maxWidthProperty().bind(container.widthProperty().divide(3));
			
			list.add(card);
		}
		
		scroll = new ScrollPane();
		scroll.setBackground(null);
		scroll.setContent(container);
		scroll.translateYProperty().bind(navbar.heightProperty());

		container.prefWidthProperty().bind(scroll.widthProperty());
		
		this.root.getChildren().add(scroll);
	}
	
	private void configureProfileSection() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(15));
		LayoutHelper.configureGridPaneConstraint(grid, 1, 2);
		LayoutHelper.configurePaneBackground(grid, Color.WHITE, new CornerRadii(8), Insets.EMPTY);
		grid.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 12, 0, 0, 0);");	
		grid.maxWidthProperty().bind(container.widthProperty().divide(3));
		
		VBox v = new VBox();
		v.setPadding(new Insets(15));
		v.setSpacing(15);
		v.setAlignment(Pos.CENTER);
		VBox v2 = new VBox();
		v2.setPadding(new Insets(15));
		v2.setSpacing(15);
		v2.setAlignment(Pos.CENTER_LEFT);
		
		profilePicture = new ImageView();
		int size = 125;
		int pp =  Session.getSession().getUser().getProfilePicture();
		LayoutHelper.configureImageViewFile(profilePicture, "./resources/profile-picture/" + ProfilePicture.pics[pp]);
		LayoutHelper.addBorderRadius(profilePicture, size, size, size);
		profilePicture.setFitWidth(size);
		profilePicture.setPreserveRatio(true);
		
		Button changeBtn = new Button("Change Picture");
		changeBtn.setStyle("-fx-background-color: #fff; -fx-text-fill: #ff7750; -fx-cursor: hand; -fx-border-color: #ff7750; -fx-border-width: 1; -fx-background-radius: 8px;");
		changeBtn.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		changeBtn.setPadding(new Insets(7));
		
		changeBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				new ChangePictureModal(StageHelper.getInstance().getStage()).showModal();
//				LayoutHelper.configureImageViewFile(profilePicture, "./resources/profile-picture/" + ProfilePicture.pics[Session.getSession().getUser().getProfilePicture()]);
				Database.getInstance().writeUserCSV();
				StageHelper.getInstance().changePage(new ProfilePage());
			}
		});

		Label usernameLbl = new Label(Session.getSession().getUser().getUsername());
		usernameLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		
		Label totalPostLbl = new Label("Pun posted: " + punList.size());
		totalPostLbl.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		
		v.getChildren().addAll(profilePicture, changeBtn);
		v2.getChildren().addAll(usernameLbl, totalPostLbl);
		
		grid.addColumn(0, v);
		grid.addColumn(1, v2);
		
		container.getChildren().add(grid);
	}

	private ArrayList<Pun> loadPun() {
		ArrayList<Pun> punList = (ArrayList<Pun>) Database.getInstance().getAllPun().clone();
		punList.removeIf(new Predicate<Pun>() {
			public boolean test(Pun p) {
				return !p.getAuthor().equals(Session.getSession().getUser().getUsername());
			}
		});
		
		return punList;
	}

}
